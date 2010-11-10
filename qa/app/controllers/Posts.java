package controllers;

import utils.QaDB;
import models.Answer;
import models.Comment;
import models.Post;
import models.Question;

public class Posts extends Auth {
 
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
	
	public static void search(){
		render();
	}
	
	
	public static void searchContent(String content){
		Post p = QaDB.findPostByText(content);
		
		if(p == null){
			flash("error", "could not find Post with content "+content);
			redirect("/");
		}
		
		render(p);
		
	}
}                      