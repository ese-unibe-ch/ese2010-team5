package models;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.emory.mathcs.backport.java.util.LinkedList;

public class User {

	private String username;	
	private long id;	  
	private static long idCounter = 1;	 
	

	public User(String username) {
		this.username = new String(username);
		this.id = idCounter++;	
	}

	public String getName() {
		return this.username;
	}
	
	public long getId(){
		return id;
	}

}
