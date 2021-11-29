package com.olx.actuvators;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;

public class CustomInfoContributor implements InfoContributor {

	@Override
	public void contribute(Builder builder) {
		// { "total-registered-users": 478, "active-login-count": 35 }

		Map<String, Object> userDetails = new HashMap<>();
		userDetails.put("total-registered-users", 478);
		userDetails.put("active-login-count", 35);
		builder.withDetails(userDetails);
	}

}
