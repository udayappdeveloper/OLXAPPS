package com.olx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.UserDto;
import com.olx.service.OlxLoginService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/olx/login")
public class OlxLoginController {
	@Autowired
	OlxLoginService loginService;

	@PostMapping(value = "/user/authenticate", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "To Login a User")
	public ResponseEntity<String> authenticate(@RequestBody UserDto authenticationRequest) {
		return loginService.loginUser(authenticationRequest);
	}

	@PostMapping(value = "/user/validate/token")
	@ApiOperation(value = "To validate a token")
	public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String authToken) {
		return loginService.validateToken(authToken);
	}

	@PostMapping(value = "/user/name")
	@ApiOperation(value = "To validate a token")
	public ResponseEntity<String> getUserName(@RequestHeader("Authorization") String authToken) {
		return loginService.getUserName(authToken);
	}

	@DeleteMapping(value = "/user/logout", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })

	@ApiOperation(value = "To Logout  a User")
	public boolean logoutUser(@RequestHeader("auth-token") String authToken) {
		return loginService.logoutUser(authToken);
	}

	@PostMapping(value = "/user", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })

	@ApiOperation(value = "To Register a User")
	public UserDto registerAnUser(@RequestBody UserDto user) {
		return loginService.registerAnUser(user);
	}

	@GetMapping(value = "/user")
	@ApiOperation(value = "To return the user information ")
	public UserDto returnAUser(@RequestHeader("Authorization") String authToken) {
		return loginService.returnAUser(authToken);
	}

}
