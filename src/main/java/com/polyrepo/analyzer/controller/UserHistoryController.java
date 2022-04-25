package com.polyrepo.analyzer.controller;

import com.polyrepo.analyzer.constant.StringConstants;
import com.polyrepo.analyzer.service.UserHistoryService;
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
@RequestMapping("/history")
public class UserHistoryController {

    @Autowired
    private UserHistoryService userHistoryService;

    private final Logger logger = LoggerFactory.getLogger(UserHistoryController.class);


    @PostMapping("/{type}")
    public ResponseEntity<Map<String,Object>> getResultOfRecent(@PathVariable String type, @RequestBody String query){
        try {
            return new ResponseEntity<>(userHistoryService.getResultOfRecent(type,query), HttpStatus.OK);
        } catch (FeignException.Unauthorized e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING, StringConstants.JSON_UNAUTHORIZED_VALUE), HttpStatus.UNAUTHORIZED);
        } catch (FeignException.BadRequest | JSONException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING, StringConstants.JSON_BAD_REQUEST_VALUE), HttpStatus.BAD_REQUEST);
        }
    }
}
