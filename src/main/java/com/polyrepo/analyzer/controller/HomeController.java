package com.polyrepo.analyzer.controller;

import com.polyrepo.analyzer.config.TokenStore;
import com.polyrepo.analyzer.model.User;
import com.polyrepo.analyzer.service.UserHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private UserHomeService userHomeService;

    public HomeController( TokenStore tokenStore ) {
        this.tokenStore = tokenStore;
    }

    @GetMapping
    public ResponseEntity<User> getUserName(@AuthenticationPrincipal( expression = "attributes['name']" ) String username
            , @AuthenticationPrincipal( expression = "name" ) String name
            , @AuthenticationPrincipal( expression = "attributes['login']") String login
            , @AuthenticationPrincipal( expression = "attributes['avatar_url']") String avatarUrl
            , @AuthenticationPrincipal( expression = "attributes['html_url']") String url
                                           ) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println("\n\n principal"+principal);

        User userObj = userHomeService.getUser(login);
        if(userObj==null){
            userObj = new User(username,login,avatarUrl,url);
            userHomeService.save(userObj);
        }
        else{
            userObj.setName(username);
            userObj.setLogin(login);
            userObj.setAvatarUrl(avatarUrl);
            userObj.setUrl(url);
            userHomeService.save(userObj);
        }
        return new ResponseEntity<User>(userObj, HttpStatus.OK);
    }
}
