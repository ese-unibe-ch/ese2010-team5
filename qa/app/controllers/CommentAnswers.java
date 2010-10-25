package controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import models.Answer;

import models.User;

import models.CommentAnswer;
import play.Logger;
import play.data.validation.Required;

public class CommentAnswers extends Posts {
	
	
	public static void edit(long id){
				
		CommentAnswer cA = CommentAnswer.findById(id);		
		if(cA == null){
			flash("error", "could not find Comment with id "+id);
			redirect("/");
		}
		
		render(cA);
		
	}
	
	public static void setContent(long id, String content){
		Logger.debug("Setting new content: \""+content+"\"");
		
		CommentAnswer cA = CommentAnswer.findById(id);		
		if(cA == null){
			flash("error", "could not find answer with id "+id);
			redirect("/");
		}
		cA.setContent(content);
		flash.put("info","Content of answer "+id+" changed");
		
		Answer a = cA.getAnswer();
		//sends a redirect
		Answers.view(a.getId());
		
	}
	
	
}
