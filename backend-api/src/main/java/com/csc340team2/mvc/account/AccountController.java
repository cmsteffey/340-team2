package com.csc340team2.mvc.account;

import com.csc340team2.mvc.coachData.CoachData;
import com.csc340team2.mvc.coachData.CoachDataService;
import com.csc340team2.mvc.session.Session;
import com.csc340team2.mvc.session.SessionService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private CoachDataService coachDataService;

    @PostMapping("/registration")
    public ResponseEntity registerAccount(@ModelAttribute Account account){
        if(account.getRole() == null){
            return ResponseEntity.badRequest().build();
        }
        Account saved = accountService.addAccount(account);
        if(account.getRole() == AccountRole.COACH){
            CoachData data = new CoachData();
            data.setAccount(account);
            coachDataService.saveCoachData(data);
        }
        Session createdSession = sessionService.createSessionForAccount(saved);

        return ResponseEntity.status(303).header("Set-Cookie", createdSession.getSetCookieHeader()).header("Location", "/").build();
    }
    @GetMapping("/account/myAccount")
    public ResponseEntity getMyAccount(Session session){
        return ResponseEntity.ok(session.getAccount());
    }
    @GetMapping("/accounts/{id}")
    public ResponseEntity getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }
    @GetMapping("/accounts")
    public ResponseEntity getAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
    @PostMapping("/update-coach-data")
    public String setCoachData(Model model, Session session, @RequestParam Map<String, String> stringMap){
        if(session.getAccount().getRole() != AccountRole.COACH){
            return "redirect:/view/dashboard";
        }
        LoggerFactory.getLogger(AccountController.class).warn("Str map size: {}", stringMap.size());
        CoachData data = session.getAccount().getCoachData();
        if(stringMap.containsKey("description"))
            data.setDescription(stringMap.get("description"));
        coachDataService.saveCoachData(data);
        return "redirect:/view/profile";
    }
    @PostMapping("/update-coach-pfp")
    public String setCoachPfp(Model model, Session session, @RequestParam("picture") MultipartFile pfp){
        try (InputStream stream = pfp.getInputStream()){
            UUID imageName = UUID.randomUUID();
            if(pfp.getSize() != java.nio.file.Files.copy(stream, Paths.get("backend-api/src/main/resources/static/profile_pictures/" + imageName.toString() + ".png"), StandardCopyOption.REPLACE_EXISTING)){
                model.addAttribute("error", "File upload read incompletely.");
                return "500";
            }
            CoachData coachData = session.getAccount().getCoachData();
            coachData.setImageName(imageName);
            coachDataService.saveCoachData(coachData);
            return "redirect:/view/profile";
        } catch (IOException ioException){
            model.addAttribute("error", "File upload failed.");
            return "500";
        }
    }
}
