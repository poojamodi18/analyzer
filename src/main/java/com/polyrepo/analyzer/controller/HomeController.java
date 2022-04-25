package com.polyrepo.analyzer.controller;

import com.polyrepo.analyzer.config.TokenStore;
import com.polyrepo.analyzer.model.User;
import com.polyrepo.analyzer.model.UserHistory;
import com.polyrepo.analyzer.service.UserHistoryService;
import com.polyrepo.analyzer.service.UserHomeService;
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

    @Value("${getCommonTrendQuery}")
    private String getCommonTrendQuery;

    @Value("${getCommonTrend}")
    private String getCommonTrend;

    @Value("${getTodayCommonTrend}")
    private String getTodayCommonTrend;

    @Value("${graphQLAccessPrefix}")
    private String graphQLAccessPrefix;

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
    public ResponseEntity<Map<String, UserHistory>> getRecentHistory(@RequestHeader("id") String userId){
        return new ResponseEntity<>(userHistoryService.getUserHistory(Integer.parseInt(userId)), HttpStatus.OK);
    }

    @GetMapping("/commonTrend")
    public ResponseEntity<Map<String,Object>> getCommonTrend(){
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();

        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMM");
        DateTimeFormatter numberMonthYearFormatter = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");

        YearMonth thisMonth    = YearMonth.now();

        List<String> queryday = new ArrayList<>();

        System.out.println("\n\n\nLOOP");

        for (int i = 0; i <month; i++) {
            YearMonth lastMonth    = thisMonth.minusMonths(i);
            queryday.add(lastMonth.format(monthYearFormatter));
            queryday.add(lastMonth.format(yearFormatter)+"-"+ lastMonth.format(numberMonthYearFormatter)+"-01");
        }

        System.out.println(queryday);


        int index = 0;

        String query = String.format(getCommonTrend, queryday.get(0), queryday.get(1), queryday.get(0), queryday.get(1),
                queryday.get(0), queryday.get(1), queryday.get(0), queryday.get(1), queryday.get(0), queryday.get(1));

        String finalQuery = "";
        for (int i = 0; i <month; i++) {
                // 0 -> 0 1
                // 1 -> 2 3
                // 2 -> 4 5
                // 3 -> 6 7
        }


        System.out.println(query);

        return null;
    }
}
