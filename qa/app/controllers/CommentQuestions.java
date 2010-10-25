package controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import models.Answer;
import models.Question;
import models.User;
import models.CommentQuestion;
import models.CommentAnswer;
import play.Logger;
import play.data.validation.Required;

public class CommentQuestions extends Posts {
	
	
	public static void edit(long id){
				
		CommentQuestion c = CommentQuestion.findById(id);		
		if(c == null){
			flash("error", "could not find Comment with id "+id);
			redirect("/");
		}
		
		render(c);
		
	}
	
	public static void setContent(long id, String content){
		Logger.debug("Setting new content: \""+content+"\"");
		
		CommentQuestion c = CommentQuestion.findById(id);		
		if(c == null){
			flash("error", "could not find answer with id "+id);
			redirect("/");
		}
		c.setContent(content);
		flash.put("info","Content of answer "+id+" changed");
		
		Question q = c.getQuestion();
		//sends a redirect
		Questions.view(q.getId());
		
	}
	
	
}
