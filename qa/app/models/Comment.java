package models;

import play.*;
import play.db.jpa.*;
import utils.QaDB;

import javax.persistence.*;
import java.util.*;

public class Comment extends Post{
	
	//Comment belongs to a Post(either a Question or an Answer)
	private Post post;
	
	protected Comment(User owner, String content) {
		super(owner, content);
		
		QaDB.addComment(this);
	}

	public Post getPost() {
		return post;
	}

	@Override
	protected void doDelete() {
		// TODO Auto-generated method stub
		
	}
    
}
