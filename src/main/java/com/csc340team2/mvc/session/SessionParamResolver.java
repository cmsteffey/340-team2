package com.csc340team2.mvc.session;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SessionParamResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private SessionService sessionService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Session.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        LoggerFactory.getLogger(SessionParamResolver.class).debug("Resolver called on {}", parameter.getMethod().getName());

        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(parameter.getMethod(), RequestMapping.class);
        if (requestMapping == null){
            return null;
        }
        LoggerFactory.getLogger(SessionParamResolver.class).debug("Resolver found non-null RM annotation {} on {}", Arrays.stream(requestMapping.method()).map(x->x.asHttpMethod().toString()).collect(Collectors.joining(", ")), parameter.getMethod().getName());


        boolean isViewRequest = Arrays.stream(requestMapping.path()).anyMatch(x->x.startsWith("/view/"));
        String cookie = webRequest.getHeader("Cookie");
        if(cookie == null){
            throw new InvalidSessionCookieException("Missing cookie header", isViewRequest ? "/view/login": null);
        }
        String key = Arrays.stream(cookie.split(";\\s*")).map(x->x.split("=")).filter(x->x.length == 2 && x[0].equals("session_guid")).map(x->x[1]).findFirst().orElse(null);
        if(key == null){
            throw new InvalidSessionCookieException("Missing session_guid value, or value is malformed", isViewRequest ? "/view/login": null);
        }
        Optional<Session> session = sessionService.getSessionByKey(key);

        if(session.isEmpty()){
            throw new InvalidSessionCookieException("Session invalid or expired", isViewRequest ? "/view/login": null);
        }
        mavContainer.addAttribute("currentAccount", session.orElseThrow().getAccount());
        return session.orElseThrow();
    }
}
