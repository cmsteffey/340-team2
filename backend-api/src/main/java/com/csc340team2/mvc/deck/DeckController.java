package com.csc340team2.mvc.deck;

import com.csc340team2.mvc.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DeckController {

    @Autowired
    private DeckService deckService;

    @PostMapping("/view/decks/import/text")
    public String importDeckFromPlainText(Session session,
                                          @RequestParam String plainText,
                                          @RequestParam String nickname,
                                          Model model) {
        try {
            System.out.println("Attempting to import deck: " + nickname);
            System.out.println("Card count: " + plainText.split("\n").length);

            deckService.importDeckFromPlainText(session.getAccount(), plainText, nickname);

            System.out.println("Deck imported successfully!");
            return "redirect:/view/decks";
        } catch (Exception e) {
            System.err.println("Failed to import deck: " + e.getMessage());
            e.printStackTrace();

            model.addAttribute("error", "Failed to import deck: " + e.getMessage());
            model.addAttribute("decks", deckService.getAllOwnedBy(session.getAccount()));
            return "decks";
        }
    }
}