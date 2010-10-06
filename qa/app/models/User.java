package models;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.emory.mathcs.backport.java.util.LinkedList;

public class User {

	private String username;	
	private long id;	
	private static Map<Long,User> instanceMap = new HashMap();  
	private static long idCounter = 1;	 
	

	public User(String username) {
		this.username = new String(username);
		this.id = idCounter++;
		instanceMap.put(id, this);
	}

	public String getName() {
		return this.username;
	}
	
	public long getId(){
		return id;
	}
	
	public static User findByName(String name){
		for(User u : instanceMap.values()){
			if(u.getName().equals(name))
				return u;
		}
		return null;
	}
	
	public static User findById(long inId){
		return instanceMap.get(inId);
	}
	
	public static Collection<User> findAll(){
		return instanceMap.values();
	}
	
	

}
