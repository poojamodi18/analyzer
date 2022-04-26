package com.polyrepo.analyzer.controller;

import com.polyrepo.analyzer.config.TokenStore;
import com.polyrepo.analyzer.constant.StringConstants;
import com.polyrepo.analyzer.model.User;
import com.polyrepo.analyzer.model.UserHistory;
import com.polyrepo.analyzer.service.UserHistoryService;
import com.polyrepo.analyzer.service.UserHomeService;
import feign.FeignException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping( "/v1/home" )
public class HomeController {

    private final TokenStore tokenStore;

    @Autowired
    private UserHomeService userHomeService;

    @Autowired
    private UserHistoryService userHistoryService;

    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

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
    public ResponseEntity<Map<String, UserHistory>> getRecentHistory(@RequestHeader("id") String userId){
        return new ResponseEntity<>(userHistoryService.getUserHistory(Integer.parseInt(userId)), HttpStatus.OK);
    }

    @GetMapping("/commonTrend")
    public ResponseEntity<Map<String,Object>> getCommonTrend(){
        try {
            return new ResponseEntity<>(userHomeService.getTrendData(), HttpStatus.OK);
        } catch (FeignException.Unauthorized e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING, StringConstants.JSON_UNAUTHORIZED_VALUE), HttpStatus.UNAUTHORIZED);
        } catch (FeignException.BadRequest | JSONException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING, StringConstants.JSON_BAD_REQUEST_VALUE), HttpStatus.BAD_REQUEST);
        }
    }
}
