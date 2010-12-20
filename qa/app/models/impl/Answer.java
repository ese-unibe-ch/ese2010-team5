package models.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import models.IQuestion;
import models.IUser;

import utils.QaDB;

/**
 * The Class Answer that inherits from the abstract Class Post.
 */
public class Answer extends Post {

	/** The question this answer belongs to. */
	private IQuestion question;
	
	/** The boolean value to see if it's rated as the best. */
	private boolean isBest = false;
		
	/**
	 * Instantiates a new answer.
	 *
	 * @param owner the owner of the answer
	 * @param content the content of the answer
	 * @param inQuestion the question the answer belongs to
	 */
	public Answer(IUser owner, String content, IQuestion inQuestion) {
		super(owner,content);
		this.question = inQuestion;
		question.addAnswer(this);		
	}	
	
	/**
	 * Gets the question the answer belongs to.
	 *
	 * @return the question
	 */
	public IQuestion getQuestion(){
		return question;
	}
	
	public void setQuestion(IQuestion q){
		if(q == null) return;
		
		if(question != null)
			question.delAnswer(this);
		
		question = q;
		question.addAnswer(this);
		
	}
	
	
	public  String toString(){
		return "rating: "+getVotation()+", "+getContent();
	}
	
	/**
	 * Checks if is rated as best Answer.
	 *
	 * @return true, if is best
	 */
	public boolean isBest(){
		return isBest;
	}
	
	/**
	 * Sets the checks if is best.
	 *
	 * @param inIsBest the new checks if is best
	 */
	public void setIsBest(boolean inIsBest){
		this.isBest = inIsBest;
	}
	
	
	protected void doDelete() {
		/*remove me from question*/
		question.delAnswer(this);
		//QaDB.removeAnswer(this);
	}

	/**
	 * Sets the owner.
	 *
	 * @param user the new owner
	 */
	public void setOwner(IUser user) {
		this.owner = user;
		
	}	
	/**
	 * Save this answer
	 * 
	 * @return true if no error occured
	 */	
	public boolean save(){
		QaDB.addAnswer(this);
		return true;
	}

}
