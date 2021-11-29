package com.olx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginDeligateImpl implements LoginDeligate {

	@Autowired
	RestTemplate restTemplate;

	@Override
	public boolean validateJWTToken(String jwtToken) {
		Boolean authTokenValdidate = false;
		try {
			HttpHeaders headers = new HttpHeaders();

			// can set the content Type
			headers.setContentType(MediaType.APPLICATION_JSON);
			// Can add token for the authorization
			headers.add(HttpHeaders.AUTHORIZATION, jwtToken);
			headers.add("headerInfo", "data");
			// put your customBean to header
			HttpEntity<String> entity = new HttpEntity<>(jwtToken, headers);
			// can post and get the ResponseBean
//			authTokenValdidate = restTemplate.postForObject("http://API-GATEWAY/olx/user/validate/token", entity,
//					Boolean.class);

			authTokenValdidate = restTemplate.postForObject("http://API-GATEWAY/olx/login/user/validate/token", entity,
					Boolean.class);
			return authTokenValdidate;
		}

		catch (Exception e) {

			return false;
		}

	}

	/*
	 * @Override public String getUserInformation(String authToken) {
	 * 
	 * UserDto userInformation =
	 * restTemplate.getForObject("http://API-GATEWAY/olx/login/user//user",
	 * UserDto.class);
	 * 
	 * return userInformation.getUserName();
	 * 
	 * }
	 */

	@Override
	public String getUserInformation(String authToken) {

		/// user/name

		String userName = "";
		try {
			HttpHeaders headers = new HttpHeaders();

			// can set the content Type
			headers.setContentType(MediaType.APPLICATION_JSON);
			// Can add token for the authorization
			headers.add(HttpHeaders.AUTHORIZATION, authToken);
			headers.add("headerInfo", "data");
			// put your customBean to header
			HttpEntity<String> entity = new HttpEntity<>(authToken, headers);
			// can post and get the ResponseBean
//			authTokenValdidate = restTemplate.postForObject("http://API-GATEWAY/olx/user/validate/token", entity,
//					Boolean.class);

			userName = restTemplate.postForObject("http://API-GATEWAY/olx/login/user/name", entity, String.class);
			return userName;
		}

		catch (Exception e) {

			return null;
		}

	}
}
