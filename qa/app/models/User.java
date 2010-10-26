package models;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.emory.mathcs.backport.java.util.LinkedList;

public class User {

	private String username;
	private String email;
	private String dateOfBirth;
	private long id;	  
	private static long idCounter = 1;	 
	

	public User(String username) {
		this.username = new String(username);
		this.id = idCounter++;	
	}

	public String getName() {
		return this.username;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public long getId(){
		return id;
	}

}
