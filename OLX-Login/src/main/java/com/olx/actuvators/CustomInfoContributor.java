package com.olx.actuvators;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.InfoContributor;

import com.olx.repository.UserRepository;

public class CustomInfoContributor implements InfoContributor {
	
	@Autowired
	UserRepository userRepository;
	

	@Override
	public void contribute(Builder builder) {
		// { "total-registered-users": 478, "active-login-count": 35 }
		//long usersCount=userRepository.count();
		Map<String, Object> userDetails = new HashMap<>();
		userDetails.put("total-registered-users", 40);
		userDetails.put("active-login-count", 35);
		builder.withDetails(userDetails);
	}

}
