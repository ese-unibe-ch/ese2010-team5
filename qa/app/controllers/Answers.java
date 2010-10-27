package controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import models.Answer;
import models.Comment;
import models.Question;
import models.User;
import play.Logger;
import play.data.validation.Required;
import utils.QaDB;

public class Answers extends Posts {
	
	
	public static void edit(long id){
				
		Answer a = QaDB.findAnswerById(id);		
		if(a == null){
			flash("error", "could not find answer with id "+id);
			redirect("/");
		}
		
		render(a);
		
	}
	
	public static void setContent(long id, String content){
		Logger.debug("Setting new content: \""+content+"\"");
		
		Answer a = QaDB.findAnswerById(id);		
		if(a == null){
			flash("error", "could not find answer with id "+id);
			redirect("/");
		}
		a.setContent(content);
		flash.put("info","Content of answer "+id+" changed");
		
		Question q = a.getQuestion();
		//sends a redirect
		Questions.view(q.getId());
		
	}
	
	public static void addComment(String comment, long aId){
		
		Answer a = QaDB.findAnswerById(aId);
		
		if(a == null){
			flash.error("could not find question q: "+aId);
			redirect("/");
			return;
		}
		
		Comment newComment = QaDB.addComment(new Comment(user,comment,a));
		
		flash.put("info", "new Comment created "+newComment.getId());
		view(aId);
	}	
	
	public static void view(long id){
		Logger.debug("Show Answer: " +id);
		
		Answer a = QaDB.findAnswerById(id);
		if(a == null){
			flash("error", "could not find answer with id: " + id);
			redirect("/");
		}
		
	}
	
}
