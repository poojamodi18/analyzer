package com.polyrepo.analyzer.controller;

import com.polyrepo.analyzer.config.TokenStore;
import com.polyrepo.analyzer.model.User;
import com.polyrepo.analyzer.service.UserHistoryService;
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

    @Autowired
    private UserHistoryService userHistoryService;

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

        return new ResponseEntity<User>(userHomeService.getUserDetails(username,name,login,avatarUrl,url), HttpStatus.OK);
    }

    @GetMapping("/recentHistory")
    public ResponseEntity<Map<String,Object>> getRecentHistory(@RequestHeader("id") String userId){
        return new ResponseEntity<>(userHistoryService.getUserHistory(Integer.parseInt(userId)), HttpStatus.OK);
    }
}
