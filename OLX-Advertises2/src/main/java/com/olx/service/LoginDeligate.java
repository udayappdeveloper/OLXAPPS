package com.olx.service;


public interface LoginDeligate {

	public boolean validateJWTToken(String authToken);
	
	public String getUserInformation(String authToken);

}
