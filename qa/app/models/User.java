package models;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.emory.mathcs.backport.java.util.LinkedList;

public class User {

	private String username;
	private String email;
	private Date birthDate;
	private String homepage;
	private long id;	  
	private static long idCounter = 1;	 

	public User(String username) {
		this.username = new String(username);
		this.id = idCounter++;
		this.birthDate = new Date(0);
	}

	public String getName() {
		return this.username;
	}
	
	public String getUsername() {
		return username;
	}

	// TODO: inform DB when username is changed!
	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public long getId(){
		return id;
	}

}
