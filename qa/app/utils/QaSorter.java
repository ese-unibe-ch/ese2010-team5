package utils;

import java.util.Comparator;

import utils.QaDB.OrderBy;

import models.Post;

public class QaSorter implements Comparator<Post>{

	private OrderBy orderBy;
	
	public QaSorter(OrderBy inOrderBy){
		this.orderBy = inOrderBy;
	}	
	
	
  public int compare(Post p1, Post p2) {
	  
  	if(p1 == null || p2 == null)
  		return 0;
  	
  	
  	switch(orderBy){
  	case DATE:
  		
  		if(p1.getTimestamp().before(p2.getTimestamp()))
  			return 1;
  		if(p1.getTimestamp().after(p2.getTimestamp()))
  			return -1;
  		
  		/* same DATE*/
  		return 0;
  	case RATING:
  		
  		if(p1.getVotation() > p2.getVotation())
  			return -1;
  		if(p1.getVotation() < p2.getVotation())
  			return 1;
  		/* same rating*/
  		return 0;
  		
  	}
  	
  	
	  return 0;
  }	
}
