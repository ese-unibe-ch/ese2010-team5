package models;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Answer extends Post {
	
	private static List<Answer> answerList = new LinkedList<Answer>();
	
	private List<CommentAnswer> commentsA = new LinkedList<CommentAnswer>();

	
	
	
	//Answer belongs to a Question
	private Question question;
	
		
	public Answer(User owner, String content, Question inQuestion) {
		super(owner,content);		
		answerList.add(this);
		this.question = inQuestion;
		question.addAnswer(this);
	}	
	
	public Question getQuestion(){
		return question;
	}
	
	//alper
	public List<CommentAnswer> getComments(){
		return this.commentsA;
	}
	
	//alper
	public void addComment(CommentAnswer newComment){
		this.commentsA.add(newComment);
	}
	
	public  String toString(){
		return "rating: "+getVotation()+", "+getContent();
	}	
	
	public static Collection<Answer> findAll(){
		return answerList;
	}
	
	@Override
	protected void doDelete() {	  
		answerList.remove(this);	  
	}	

}
