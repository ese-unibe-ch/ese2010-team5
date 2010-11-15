package controllers;

import play.Logger;
import utils.QaDB;
import utils.QaMarkdown;
import models.Answer;
import models.Post;
import models.Question;

public class Posts extends Auth {
 
	
	public static void markdownPreview(String input){
		
		if(input != null){
			String preview = QaMarkdown.toHtml(input);			
			renderText(preview);			
		}else{
			renderText("input is null");
		}
		
		
	}
	
	
	
	public static void rate(long id, boolean up){
		
		Post post = QaDB.findPostById(id);
		
		if(post == null){
			error("could not find post with id: "+id);
			return;
		}		
		
		if(up)
			post.rateUp(user);
		else
			post.rateDown(user);
		
		//render new voting
		renderText(post.getVotation());
		
	}	
	
}                      