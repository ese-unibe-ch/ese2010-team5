package models;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CommentQuestion extends Post{

	
	private static List<CommentQuestion> commentList = new LinkedList<CommentQuestion>();
	
	//Comment belongs to a Question
	private Question question;
	
	public CommentQuestion(User owner, String content, Question inQuestion) {
		super(owner, content);
		commentList.add(this);
		this.question = inQuestion;
		question.addComment(this);
	}

	public Question getQuestion(){
		return question;
	}
	
	@Override
	protected void doDelete() {
		commentList.remove(this);		
	}

	public static void setCommentList(List<CommentQuestion> commentList) {
		CommentQuestion.commentList = commentList;
	}

	public static List<CommentQuestion> getCommentList() {
		return commentList;
	}
	
	public static Collection<CommentQuestion> findAll(){
		return commentList;
	}

	public  String toString(){
		return "rating: "+getVotation()+", "+getContent();
	}	
	


}
