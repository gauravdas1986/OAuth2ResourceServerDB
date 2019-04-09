package com.oauth2.Oauth2Client.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestController {

//	@PreAuthorize("#oauth2.hasScope('read')")
	@GetMapping("/test")
	public String test() {
		return "Hello World...this is resource server endpoint";
	}
	
	@PreAuthorize("hasAuthority('my-admin')")
	@RequestMapping("/write")
	public String write() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof OAuth2Authentication) {
            Object details = auth.getDetails();
            
            OAuth2Authentication oauth = (OAuth2Authentication) SecurityContextHolder
                    .getContext().getAuthentication();
            HashMap client = (HashMap) oauth.getUserAuthentication().getDetails();
            System.out.println(((Map)client.get("oauth2Request")).get("scope"));
        }
 
		return "Hello World...this is resource server write endpoint";
	}
}