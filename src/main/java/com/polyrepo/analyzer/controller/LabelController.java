package com.polyrepo.analyzer.controller;

import com.polyrepo.analyzer.constant.StringConstants;
import com.polyrepo.analyzer.model.RepoNamesList;
import com.polyrepo.analyzer.service.LabelService;
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
@RequestMapping("/org")
public class LabelController {

    @Autowired
    private LabelService labelService;

    private final Logger logger = LoggerFactory.getLogger(LabelController.class);


    @PostMapping("/{orgUserName}/repo/labels")
    public ResponseEntity<Object> getLabels(@PathVariable String orgUserName, @RequestBody RepoNamesList repoNamesList) {
        try {
            return new ResponseEntity<>(labelService.getLabels(orgUserName, repoNamesList), HttpStatus.OK);
        } catch (FeignException.Unauthorized e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING, StringConstants.JSON_UNAUTHORIZED_VALUE), HttpStatus.UNAUTHORIZED);
        } catch (FeignException.BadRequest | JSONException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING, StringConstants.JSON_BAD_REQUEST_VALUE), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/{orgUserName}/repo/{label}/openIssues")
    public ResponseEntity<Map<String,Object>> getOpenIssueNamesByLabel(@PathVariable String orgUserName,@PathVariable String label , @RequestBody RepoNamesList repoNamesList) {
        try{
            return new ResponseEntity<>(labelService.getOpenIssueNamesByLabel(orgUserName,label,repoNamesList), HttpStatus.OK);
        } catch (FeignException.Unauthorized e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING, StringConstants.JSON_UNAUTHORIZED_VALUE), HttpStatus.UNAUTHORIZED);
        } catch (FeignException.BadRequest | JSONException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING, StringConstants.JSON_BAD_REQUEST_VALUE), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/{orgUserName}/repo/{label}/openIssues/more")
    public ResponseEntity<Map<String,Object>> getOpenIssueNamesByLabel(@PathVariable String orgUserName,@PathVariable String label, @RequestHeader("EndCursor") String endCursor, @RequestBody RepoNamesList repoNamesList) {
        try{
            return new ResponseEntity<>(labelService.getOpenIssueNamesByLabelWithEndCursor(orgUserName,label,endCursor,repoNamesList), HttpStatus.OK);
        } catch (FeignException.Unauthorized e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING, StringConstants.JSON_UNAUTHORIZED_VALUE), HttpStatus.UNAUTHORIZED);
        } catch (FeignException.BadRequest | JSONException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING, StringConstants.JSON_BAD_REQUEST_VALUE), HttpStatus.BAD_REQUEST);
        }
    }
}
