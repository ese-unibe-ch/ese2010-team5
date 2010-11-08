package models;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.pegdown.PegDownProcessor;

import utils.QaMarkdown;

/**
 * The abstract Class Post.
 */
public abstract class Post implements IPost {
	
	/** The owner. */
	private IUser owner;
	
	/** The content. in original markdown*/
	private String content;
	/** Rendered content in html */
	private String renderedContent;
	
	/** The timestamp. */
	private Date timestamp;
	
	/** The id. */
	private long id;
	
	/** The votes list. */
	private LinkedList<Vote> votes;
	
	/** The comments list. */
	private List<Comment> comments = new LinkedList<Comment>();	

	/** The id counter. */
	private static long idCounter = 1;
	
	/**
	 * Instantiates a new post.
	 *
	 * @param inUser the in user that created the post
	 * @param inContent the content of the post
	 */
	protected Post(IUser inUser, String inContent){
		this.owner = inUser;
		/*also renders the markdown*/
		setContent(inContent);
		this.timestamp = new Date(System.currentTimeMillis());
		this.id = idCounter++;
		this.votes = new LinkedList<Vote>();
	}
	
	/**
	 * Adds a comment to the post.
	 *
	 * @param comment the comment
	 */
	public void addComment(Comment comment){
		this.comments.add(comment);
	}
	
	/**
	 * Gets the comments made to the post.
	 *
	 * @return the comments
	 */
	public List<Comment> getComments(){
		return comments;
	}
	
	/**
	 * Gets the owner who created the post.
	 *
	 * @return the owner
	 */
	public IUser getOwner() {
		return this.owner;
	}
	
	/**
	 * Return the renderedContent
	 *
	 * @return the content
	 */
	public String getContent() {
		return this.renderedContent;
	}
	
	/**
	 * 
	 */
	public String getMarkdown(){
		return content;
	}
	
	/**
	 * Gets the timestamp of the creation of the post.
	 *
	 * @return the timestamp
	 */
	public Date getTimestamp(){
		return timestamp;
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
	 * The delete method.
	 */	
	
	protected abstract void doDelete();
	
	
	/**
	 * Rates an entry with +1 when up is true, -1 otherwise.
	 *
	 * @param user - The User who rates the entry.
	 * @param up the up
	 */
	private void rate(IUser user, boolean up) {
		
		if(user == null) return;
		
		int rateValue = up ? +1 : -1;
		
		//check if it is my own post
		if(user.equals(getOwner()))
			return;		
		
		//check if user already voted
		for(Vote v : votes){
			if(v.getUser() != null && v.getUser().equals(user)){
				v.setValue(rateValue);
				return;
			}
				
		}		
		//else new vote
		votes.add(new Vote(rateValue, user));
	}
	
	
	/**
	 * Rates an entry with +1.
	 * 
	 * @param user
	 *            - The user who rates the entry.
	 */
	public void rateUp(IUser user) {
		rate(user,true);
	}

	/**
	 * Rates an entry with -1.
	 * 
	 * @param user
	 *            - The user who rates the entry.
	 */
	public void rateDown(IUser user) {
		rate(user,false);
	}

	/**
	 * Calculates the average mark for the entry.
	 *
	 * @return the votation
	 */
	public int getVotation() {		
		
		int votation = 0;
		for (Vote singleVote : votes) {
			votation += singleVote.getValue();
		}		
		return votation;

	}
	
	
	/**
	 * Sets the content to a new value.
	 *
	 * @param editedContent the new content
	 */
	public void setContent(String editedContent){
		content = editedContent;
		
		/*also update the rendered one*/
		renderedContent = QaMarkdown.toHtml(content);	
		
	}
	
	
	

}
