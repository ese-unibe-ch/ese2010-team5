package models;

import java.util.*;

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
	 * Deletes the user and assigns all questions to an anonymous user.
	 * Answers of the deleted user are deleted too.
	 */
	public void delete() {
		//assignQuestionsToAnonymous();
		//deleteAnswers();
		
		//clone posts to prevent concurrent modification
		List<Post> clone = new LinkedList<Post>();
		for(Post post : posts){
			clone.add(post);
		}
		
		for (Post post:clone){
			post.unregister();
		}
		this.posts.clear();
		QaDB.removeUser(this);
	}

	/**
	 * Assign questions to anonymous.
	 */
	private void assignQuestionsToAnonymous() {
		List<Question> result = (List<Question>) QaDB.findAllQuestionsOfUser(this);
		// list is empty, why?
		for (IQuestion q : result){
			q.setOwner(QaDB.findUserByName(QaDB.ANONYMOUS.getName()));
		}
		
	}
	
	/**
	 * Delete answers.
	 */
	private void deleteAnswers(){
		List<Answer> answers = QaDB.findAllAnswersOfUser(this);
		// list is empty, why?
		for (Answer a : answers){
			a.setOwner(QaDB.ANONYMOUS);
			QaDB.removeAnswer(a);
		}
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

}
