package controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import models.Answer;
import models.Comment;
import models.Post;

import models.User;

import models.CommentAnswer;
import play.Logger;
import play.data.validation.Required;
import utils.QaDB;

public class CommentAnswers extends Posts {
	
	
	public static void edit(long id){
				
		Comment cA = QaDB.findCommentById(id);		
		if(cA == null){
			flash("error", "could not find Comment with id "+id);
			redirect("/");
		}
		
		render(cA);
		
	}
	
	public static void setContent(long id, String content){
		Logger.debug("Setting new content: \""+content+"\"");
		
		Comment cA = QaDB.findCommentById(id);		
		if(cA == null){
			flash("error", "could not find answer with id "+id);
			redirect("/");
		}
		cA.setContent(content);
		flash.put("info","Content of answer "+id+" changed");
		
		Post a = cA.getPost();
		//sends a redirect
		Answers.view(a.getId());
		
	}
	
	
}
