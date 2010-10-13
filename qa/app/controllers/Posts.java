package controllers;

import models.Answer;
import models.Post;
import models.Question;

public class Posts extends Auth {
 
	public static void rate(long id, boolean up){
		
		Post post = Post.findById(id);
		
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