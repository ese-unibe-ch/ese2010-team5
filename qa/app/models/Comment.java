package models;

import play.*;
import play.db.jpa.*;
import utils.QaDB;

import javax.persistence.*;
import java.util.*;

public class Comment extends Post{
	
	//Comment belongs to a Post(either a Question or an Answer)
	private Post post;
	
	public Comment(User owner, String content, Post inPost) {
		super(owner, content);
		this.post = inPost;
		this.post.addComment(this);
	}

	public Post getPost() {
		return post;
	}

	
	protected void doDelete() {
		// TODO Auto-generated method stub
		
	}
    
}
