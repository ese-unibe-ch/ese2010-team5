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
import utils.QaMarkdown;

/**
 * The Class Answer that inherits from the abstract Class Post.
 */
public class Answer extends Post {

	/** The question this answer belongs to. */
	private IQuestion question;
	
	/** The boolean value to see if it's rated as the best. */
	private boolean isBest = false;

	private String renderedContent;
		
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
		setContent(content);
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
	
	public void setContent(String editedContent, boolean rendered){
		if(rendered){
			super.setContent(editedContent);
			renderedContent = editedContent;
		}else{
			super.setContent(editedContent);
			//mark renderedContent as dirty, so that it will be re-rendered
			//when needed
			renderedContent = null;
		}
		
	}
	
	public void setContent(String editedContent){
		setContent(editedContent,  false);		
	}
	
	public String getContent(){
		
		if(renderedContent == null){
			/*render markdown*/			
			renderedContent = QaMarkdown.toHtml(content);	
		}
		return renderedContent;
	}
	
	public String getMarkdown(){
		return content;
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
	
	public boolean save(){
		QaDB.addAnswer(this);
		return true;
	}

}
