package controllers;

import java.util.Collection;
import java.util.List;

import models.Answer;
import models.Comment;
import models.Post;
import models.Question;

import play.Logger;
import play.mvc.Controller;
import play.mvc.With;
import utils.QaDB;
import utils.QaDB.OrderBy;

public class Questions extends Posts {	
	
	
	public static void list(){
		
		Collection<Question> questions = QaDB.findAllQuestions(OrderBy.RATING);
		render(questions);
	}
	
	public static void delete(long id){
		
		Post p = QaDB.findPostById(id);
		
		if(p == null){
			flash.error("could not find Question with id "+id);
		}else{
			if(QaDB.delPost(p))
				flash.put("info","Question "+p.getId()+" deleted");
			else
				flash.put("error","Could not delete "+p.getId());
		}			
		
		list();		
		
	}
	
	public static void markBestAnswer(long qId, long aId){
		
		Logger.debug("marking best answer "+aId+" for question "+qId);
		
		Question q = QaDB.findQuestionById(qId);		
		
		if(q == null){
			flash("error", "failed setting best answer for q: "+qId);
			redirect("/");
		}
		
		Collection<Answer> answers = q.getAnswers();		
		for(Answer a : answers){
			if(!a.isBest() && a.getId() == aId)
				a.setIsBest(true);
			else
				a.setIsBest(false);
		}
		
		flash("info","answer "+aId+" marked as best");
		view(qId);

	}

	// TODO need to update DB
	public static void create(String content, String title){
		
		Logger.debug("Create Question with content: "+content);		
		Question q = QaDB.addQuestion(new Question(user, title, content));	
		
		flash.put("info", "Question "+q.getId()+" created");
		
		list();
		
	}
	
	public static void addAnswer(String answer, long qId){
		Question q = QaDB.findQuestionById(qId);
		if(q == null){
			flash.error("could not find question q: "+qId);			
			redirect("/");
		}
		Answer newAnswer = QaDB.addAnswer(new Answer(user, answer,q));		
				
		flash.put("info", "answer Created: "+newAnswer.getId());		
		view(qId);		
			
	}
	

	public static void addComment(String comment, long cId){
		
		Question q = QaDB.findQuestionById(cId);
		
		if(q == null){
			flash.error("could not find question q: "+cId);
			redirect("/");
			return;
		}
		
		Comment newComment = QaDB.addComment(new Comment(user,comment,q));
		
		flash.put("info", "new Comment created "+newComment.getId());
		view(cId);
	}
	
	public static void view(long id){
		Logger.debug("Show question: "+id);
		
		Question q = QaDB.findQuestionById(id);
		if(q == null){
			flash("error", "could not find question with id "+id);
			redirect("/");
		}
		
		List<Answer> answers = q.getAnswers();
		if(! answers.isEmpty()){			
			/* find best answer*/
			int idx = -1;
			for(Answer a: answers){
				idx++;
				if(a.isBest()){					
					break;
				}
			}
			/* move to the head if found*/
			if(idx >= 0){
				Answer bestAnswer = answers.remove(idx);
				answers.add(0, bestAnswer);
			}
			
		}
		
		render(q,answers);
		
	}
	
	public static void edit(long id){
		Logger.debug("Edit question: "+id);
		
		Question q = QaDB.findQuestionById(id);
		if(q == null){
			flash("error", "could not find question with id "+id);
			redirect("/");
		}
		
		render(q);
		
	}
	
	public static void setContent(long id, String title, String content){
		Logger.debug("Setting new content: \""+content+"\"");
		
		Question q = QaDB.findQuestionById(id);
		if(q == null){
			flash("error", "could not find question with id "+id);
			redirect("/");
		}
		q.setTitle(title);
		q.setContent(content);
		flash.put("info","Content of question "+id+" changed");
		view(id);
		
	}
	
}
