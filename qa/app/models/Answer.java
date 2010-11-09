package models;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import utils.QaDB;

public class Answer extends Post {

	//Answer belongs to a Question
	private Question question;
	private boolean isBest = false;
		
	public Answer(IUser owner, String content, Question inQuestion) {
		super(owner,content);
		this.question = inQuestion;
		question.addAnswer(this);
		QaDB.addAnswer(this);
	}	
	
	public Question getQuestion(){
		return question;
	}
	
	
	public  String toString(){
		return "rating: "+getVotation()+", "+getContent();
	}
	
	public boolean isBest(){
		return isBest;
	}
	
	public void setIsBest(boolean inIsBest){
		this.isBest = inIsBest;
	}
	
	
	protected void doDelete() {	  
		/* do somthing when im getting deleted*/	  
	}	

}
