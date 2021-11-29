package com.olx.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class UserEntity_OLD {

	@Id
	@GeneratedValue
	private int id;

	private String username;
	private String password;
	private String roles;
	

	public UserEntity_OLD(int id, String username, String password, String roles, String firstname, String lastname,
			boolean active, String phoneNumber) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public UserEntity_OLD(String username, String password, String roles, String firstname, String lastname, boolean active,
			String phoneNumber) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;

	}

	public UserEntity_OLD() {
		super();
	}

}
