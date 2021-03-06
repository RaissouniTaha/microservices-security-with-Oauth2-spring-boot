package com.appsdeveloperblog.ws.api.ResourceServer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.ws.api.ResourceServer.response.UserRest;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private Environment environment;
	
	@GetMapping("/status/check")
	public String status() {
		return "Working on port:  "+environment.getProperty("local.server.port");
	}
	
	@PreAuthorize("hasRole('developer') or #id == #jwt.subject")
	//@Secured("ROLE_developer")
	@DeleteMapping(path = "/{id}")
	public String deleteUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
		
		return "Delete user with id "+id+" and JWT subject "+ jwt.getSubject();
	}
	
	@PostAuthorize("returnObject.userId == #jwt.subject")
	@GetMapping(path = "/{id}")
	public UserRest getUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
		
		return new UserRest("Taha", "Raissouni", "62b9d4b0-4ec8-4547-87a4-8dc4b2b325");
	}
}
