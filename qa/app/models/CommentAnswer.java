package models;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CommentAnswer extends Post{

	
	private static List<CommentAnswer> commentListA = new LinkedList<CommentAnswer>();
	
	//Comment belongs to a Answer
	private Answer answer;
	
	public CommentAnswer(User owner, String content, Answer inAnswer) {
		super(owner, content);
		commentListA.add(this);
		this.answer = inAnswer;
		answer.addComment(this);
	}

	public Answer getAnswer(){
		return answer;
	}
	
	@Override
	protected void doDelete() {
		commentListA.remove(this);		
	}

	public static void setCommentList(List<CommentAnswer> commentList) {
		CommentAnswer.commentListA = commentList;
	}

	public static List<CommentAnswer> getCommentList() {
		return commentListA;
	}
	
	public static Collection<CommentAnswer> findAll(){
		return commentListA;
	}

	public  String toString(){
		return "rating: "+getVotation()+", "+getContent();
	}	
	


}
