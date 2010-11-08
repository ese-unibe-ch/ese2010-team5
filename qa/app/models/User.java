package models;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;


/**
 * The Class User.
 */
public class User implements IUser {

	/** The username. */
	private String username;
	
	/** The email. */
	private String email;
	
	/** The birth date. */
	private Date birthDate;
	
	/** The homepage. */
	private String homepage;
	
	/** The id. */
	private long id;	  
	
	/** The notifications. */
	private List<Notification> notifications;
	
	/** The id counter. */
	private static long idCounter = 1;	

	/**
	 * Instantiates a new user.
	 *
	 * @param username the username
	 */
	public User(String username) {
		this.username = new String(username);
		this.id = idCounter++;
		this.birthDate = new Date(0);
		this.notifications = new LinkedList<Notification>();
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.username;
	}	
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the birth date.
	 *
	 * @return the birth date
	 */
	public Date getBirthDate() {
		
		return birthDate;
	}

	/**
	 * Sets the birth date.
	 *
	 * @param birthDate the new birth date
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Gets the homepage.
	 *
	 * @return the homepage
	 */
	public String getHomepage() {
		return homepage;
	}

	/**
	 * Sets the homepage.
	 *
	 * @param homepage the new homepage
	 */
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId(){
		return id;
	}
	
	/**
	 * Adds the notification.
	 *
	 * @param n the n
	 */
	public void addNotification(Notification n){
		notifications.add(n);
	}
	
	/**
	 * Gets the notifications.
	 *
	 * @return the notifications
	 */
	public List<Notification> getNotifications(){
		return notifications;
	}

}
