package com.polyrepo.analyzer.controller;

import com.polyrepo.analyzer.constant.StringConstants;
import com.polyrepo.analyzer.model.RepoNamesList;
import com.polyrepo.analyzer.service.BranchService;
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
public class BranchController {

    @Autowired
    private BranchService branchService;

    private final Logger logger = LoggerFactory.getLogger(BranchController.class);

    @PostMapping("/{orgUserName}/defaultbranch")
    public ResponseEntity<Map<String,Object>> getDefaultBranch(@PathVariable String orgUserName,@RequestBody RepoNamesList repoNamesList){
        try {
            return new ResponseEntity<>(branchService.getDefaultBranch(orgUserName, repoNamesList), HttpStatus.OK);
        } catch (FeignException.Unauthorized e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING, StringConstants.JSON_UNAUTHORIZED_VALUE), HttpStatus.UNAUTHORIZED);
        } catch (FeignException.BadRequest | JSONException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING, StringConstants.JSON_BAD_REQUEST_VALUE), HttpStatus.BAD_REQUEST);
        }

    }
}
