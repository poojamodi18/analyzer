package com.polyrepo.analyzer.controller;

import com.polyrepo.analyzer.config.TokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping( "/v1/home" )
public class HomeController {

    private final TokenStore tokenStore;

    public HomeController( TokenStore tokenStore ) {
        this.tokenStore = tokenStore;
    }

    @GetMapping
    public Map<String, String> getUserName(@AuthenticationPrincipal( expression = "attributes['name']" ) String username
            ,@AuthenticationPrincipal( expression = "name" ) String name
            ,@AuthenticationPrincipal( expression = "attributes['login']") String login
            ,@AuthenticationPrincipal( expression = "attributes['avatar_url']") String avatarUrl
            ,@AuthenticationPrincipal( expression = "attributes['url']") String url
                                           ) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println("\n\n principal"+principal);

        Map<String, String> user = new HashMap<>();
        user.put("name",username);
        user.put("id",name);
        user.put("avatarUrl",avatarUrl);
        user.put("url",url);
        user.put("login",login);
        return user;
    }
}
