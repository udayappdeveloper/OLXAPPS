package com.olx.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("tokendoc")
public class AuthTokenDocument {
	/*
	 * @Id private int id;
	 * 
	 * public int getId() { return id; }
	 */

	/*
	 * public void setId(int id) { this.id = id; }
	 */
	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	private String authToken;

}
