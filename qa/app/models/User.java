package models;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import utils.QaDB;


public class User implements IUser {

	private String username;
	private String email;
	private Date birthDate;
	private String homepage;
	private long id;	  
	private List<Notification> notifications;
	private static long idCounter = 1;	

	public User(String username) {
		this.username = new String(username);
		this.id = idCounter++;
		this.birthDate = new Date(0);
		this.notifications = new LinkedList<Notification>();
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
	
	public void addNotification(Notification n){
		notifications.add(n);
	}
	
	public List<Notification> getNotifications(){
		return notifications;
	}

	/**
	 * Deletes the user and assigns all questions to an anonymous user.
	 * Answers of the deleted user are deleted too.
	 */
	public void delete() {
		assignQuestionsToAnonymous(this);
		
	}

	private void assignQuestionsToAnonymous(User user) {
		List<Question> result = (List<Question>) QaDB.findAllQuestionsOfUser(user);
		
		// for now i just set the owner to anonymous. no changes in DB.
		for (Question q : result){
			q.setOwner(QaDB.ANONYMOUS);
		}
		
	}

}
