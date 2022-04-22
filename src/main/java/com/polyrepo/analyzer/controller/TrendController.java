package com.polyrepo.analyzer.controller;

import com.polyrepo.analyzer.constant.StringConstants;
import com.polyrepo.analyzer.model.RepoNamesList;
import com.polyrepo.analyzer.model.Trend;
import com.polyrepo.analyzer.service.TrendService;
import feign.FeignException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/trend")
public class TrendController {

    @Autowired
    private TrendService trendService;

    private final Logger logger = LoggerFactory.getLogger(TrendController.class);

    @GetMapping("/details/{title}")
    public ResponseEntity<Map<String, Object>> getiIdlePrTrend(@RequestHeader("id") String userId, @PathVariable String title) {
        try {
            return new ResponseEntity<>(trendService.getTrendData(Integer.parseInt(userId),title), HttpStatus.OK);
        } catch (FeignException.Unauthorized e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING, StringConstants.JSON_UNAUTHORIZED_VALUE), HttpStatus.UNAUTHORIZED);
        } catch (FeignException.BadRequest | JSONException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING, StringConstants.JSON_BAD_REQUEST_VALUE), HttpStatus.BAD_REQUEST);
        }
    }

    /*@PostMapping("/{orgUserName}/repo/idlePR/{startDate}")
    public ResponseEntity<Map<String, Object>> getPullRequestNotUpdatedByDays(@PathVariable String orgUserName, @PathVariable String startDate, @RequestBody RepoNamesList repoNamesList, @RequestHeader("id") String userId) {
        try {
            return new ResponseEntity<>(trendService.getTrendData(orgUserName, repoNamesList, startDate, Integer.parseInt(userId)), HttpStatus.OK);
        } catch (FeignException.Unauthorized e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING, StringConstants.JSON_UNAUTHORIZED_VALUE), HttpStatus.UNAUTHORIZED);
        } catch (FeignException.BadRequest | JSONException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING, StringConstants.JSON_BAD_REQUEST_VALUE), HttpStatus.BAD_REQUEST);
        }
    }*/
}
