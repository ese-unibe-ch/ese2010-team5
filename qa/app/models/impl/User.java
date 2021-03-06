package models.impl;

import java.util.*;

import models.INotification;
import models.IQuestion;
import models.IUser;

import utils.QaDB;


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
	private List<INotification> notifications;
	
	/** */
	private List<IQuestion> 		subscriptions;
	
	/** The posts list. */
	private List<Post> posts;
	
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
		this.notifications = new LinkedList<INotification>();
		this.posts = new LinkedList<Post>();
		this.subscriptions = new LinkedList<IQuestion>();
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
	 * @param email the new email address
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
	 * @param n the notification
	 */
	public void addNotification(INotification n){
		notifications.add(n);
	}
	
	/**
	 * Gets the notifications.
	 *
	 * @return the notifications
	 */
	public List<INotification> getNotifications(){
		return notifications;
	}

	/**
	 * Deletes the user and assigns all posts to an anonymous user.
	 *
	 */
	public void delete() {
	
		
		//clone posts to prevent concurrent modification
		List<Post> clone = new LinkedList<Post>();
		for(Post post : posts){
			clone.add(post);
		}
		
		for (Post post : clone){
			post.unregister();
		}
		this.posts.clear();
		QaDB.removeUser(this);
		
		//assign all the posts this user has made to anonymous
		assignQuestionsToAnonymous();
		assignAnswersToAnonymous();
		assignCommentsToAnonymous();
		
		//destroying all the information about the user
		this.setUsername("blank"+id);
		this.setBirthDate(new Date(0));
		this.setEmail(null);
		this.setHomepage(null);
		this.notifications.clear();
		this.posts.clear();
		this.subscriptions.clear();
		
	}
	/**
	 * Assign comments to anonymous.
	 */
	private void assignCommentsToAnonymous() {
		List<Comment> comments = QaDB.findAllCommentsOfUser(this);
		
		for (Comment comment : comments){
			comment.setOwner(QaDB.findUserByName(QaDB.ANONYMOUS.getName()));
		}
	}

		/**
		 * Assign answers to anonymous.
		 */
	private void assignAnswersToAnonymous() {
		List<Answer> answers = QaDB.findAllAnswersOfUser(this);
		
		for (Answer answer : answers){
			answer.setOwner(QaDB.findUserByName(QaDB.ANONYMOUS.getName()));
		}
	}

	/**
	 * Assign questions to anonymous.
	 */
	private void assignQuestionsToAnonymous() {
		List<IQuestion> result = QaDB.findAllQuestionsOfUser(this);
		
		for (IQuestion q : result){
			q.setOwner(QaDB.findUserByName(QaDB.ANONYMOUS.getName()));
		}
		
	}
	
	/**
	 * Delete answers.
	 */
	//unused
	private void deleteAnswers(){
		List<Answer> answers = QaDB.findAllAnswersOfUser(this);
		
		for (Answer answer : answers){
			answer.delete();
		}
		
	}
	//unused
	private void deleteComments(){
		List<Comment> comments = QaDB.findAllCommentsOfUser(this);
		
		for (Comment comment : comments)
			comment.delete();
	}

	public void registerPost(Post post) {
		this.posts.add(post);
		
	}

	/**
	 * Unregister from a post.
	 *
	 * @param post the post
	 */
	public void unregister(Post post) {
		this.posts.remove(post);
	}
	
  public void delNotification(INotification notif) {
	  
  	if(notif == null)return;
  	notifications.remove(notif);  	
	  
  }
	
  public void addSubscription(IQuestion quest) {
	  if(quest == null) return;	  
	  subscriptions.add(quest);	  
  }
	
  public List<IQuestion> getSubscriptions() {	  
	  return subscriptions;
  }
	
  public void remSubscription(IQuestion quest) {
	  if(quest == null) return;
	  subscriptions.remove(quest);
	  
  }

}
