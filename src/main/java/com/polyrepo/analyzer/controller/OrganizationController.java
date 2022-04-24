package com.polyrepo.analyzer.controller;

import com.polyrepo.analyzer.constant.StringConstants;
import com.polyrepo.analyzer.service.OrganizationService;
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
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    private final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @GetMapping("/{orgName}")
    public ResponseEntity<Map<String,Object>> getOrganizationsList(@PathVariable String orgName) {
        try{
            return new ResponseEntity<>(organizationService.getOrganizationList(orgName),HttpStatus.OK);
        }catch (FeignException.Unauthorized e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING,StringConstants.JSON_UNAUTHORIZED_VALUE), HttpStatus.UNAUTHORIZED);
        }catch (FeignException.BadRequest | JSONException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING,StringConstants.JSON_BAD_REQUEST_VALUE),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{orgName}/orgProfile")
    public ResponseEntity<Map<String,Object>> getOrganizationProfile(@PathVariable String orgName){
        try{
            return new ResponseEntity<>(organizationService.getOrganizationProfile(orgName),HttpStatus.OK);
        }catch (FeignException.Unauthorized e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING,StringConstants.JSON_UNAUTHORIZED_VALUE),HttpStatus.UNAUTHORIZED);
        }catch (FeignException.BadRequest | JSONException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING,StringConstants.JSON_BAD_REQUEST_VALUE),HttpStatus.BAD_REQUEST);
        }
    }
}