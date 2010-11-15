package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.SortType;

import models.Answer;
import models.Comment;
import models.IQuestion;
import models.IUser;
import models.Notification;
import models.Post;
import models.Question;
import models.Tag;
import models.User;

import play.Logger;
import play.mvc.Controller;
import play.mvc.With;
import utils.QaDB;
import utils.QaSorter;
import utils.QaDB.OrderBy;

/**
 * The Class Questions.
 */
public class Questions extends Posts {	
	
	
	/**
	 * List the questions.
	 *
	 * @param sort the sort
	 * @param tagname the tag's name
	 */
	public static void list(String sort, String tagname){
		
		Tag tag = null;		
		Collection<Question> questions = null;
		OrderBy	orderBy = OrderBy.DATE;
		
		if(tagname != null){
			tag = QaDB.findTagByName(tagname);			
		}
		if(sort != null && sort.length() > 0){
			orderBy = OrderBy.valueOf(sort);
		}else{
			sort = orderBy.name();
		}	
		
				
		if(tag != null){
			questions = QaDB.findAllQuestionsTaggedWith(tag, orderBy);			
		}else{
			questions = QaDB.findAllQuestions(orderBy);
		}		 		
		
		render(questions,sort,tagname);		
		
				
		
	}	
	
	/**
	 * List all default.
	 */
	public static void listAll(){		
		
		OrderBy sortOrderEnum = OrderBy.DATE;
		
		Collection<Question> questions = QaDB.findAllQuestions(sortOrderEnum);
		String sort = sortOrderEnum.name();
		renderTemplate("Questions/list.html",questions,sort);
			
	}
	
	
	
	/**
	 * Mark best answer.
	 *
	 * @param qId the id of the question
	 * @param aId the id of the answer being marked as the best
	 */
	public static void markBestAnswer(long qId, long aId){
		
		Logger.debug("marking best answer "+aId+" for question "+qId);
		
		IQuestion q = QaDB.findQuestionById(qId);		
		
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

	/**
	 * Save a question.
	 *
	 * @param content the content
	 * @param title the title
	 * @param tags the tags
	 * @param tag the tag
	 */
	public static void save(String content, String title, String tags, String[] tag){
		
		Logger.debug("Create Question with content: "+content);
		
		StringBuffer tagStr = new StringBuffer();
		int arrL = tag.length;
		for (int j = 0; j < arrL; j++){
			tagStr.append(tag[j]);
			tagStr.append(", ");
		}
		tags = tagStr.toString();
		 		
		Question q = QaDB.addQuestion(new Question(user, title, content, tags));	
		
		Logger.debug("Question "+" created");
		flash.put("info", "Question "+q.getId()+" created");
		
		listAll();
		
	}
	
	/**
	 * Creates the question.
	 */
	public static void create(){
		render();
		
	}

	/**
	 * Adds the answer.
	 *
	 * @param answer the answer
	 * @param qId the q id
	 */
	public static void addAnswer(String answer, long qId){
		Question q = QaDB.findQuestionById(qId);
		if(q == null){
			flash.error("could not find question q: "+qId);			
			redirect("/");
		}
		Answer newAnswer = QaDB.addAnswer(new Answer(user, answer,q));
		flash.put("info", "answer Created: "+newAnswer.getId());
		
		/*publish notifications for subscribers*/
		List<IUser> subscribers = q.getSubscribers();
		for(IUser subscriber : subscribers){
			/*the logged in user is the originator of this notification*/
			((User) subscriber).addNotification(new Notification(user,q, Notification.Type.NEW_ANSWER));
		}
		
		view(qId);		
			
	}
	

	/**
	 * Adds the comment.
	 *
	 * @param comment the comment
	 * @param qId the q id
	 */
	public static void addComment(String comment, long qId){
		
		Question q = QaDB.findQuestionById(qId);
		
		if(q == null){
			flash.error("could not find question q: "+qId);
			redirect("/");
			return;
		}
		
		Comment newComment = QaDB.addComment(new Comment(user,comment,q));
		
		flash.put("info", "new Comment created "+newComment.getId());
		view(qId);
	}
	
	/**
	 * View the question.
	 *
	 * @param id the id
	 */
	public static void view(long id){
		Logger.debug("Show question: "+id);
		
		IQuestion q = QaDB.findQuestionById(id);
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
	
	/**
	 * Edits the question.
	 *
	 * @param id the id
	 */
	public static void edit(long id){
		Logger.debug("Edit question: "+id);
		
		IQuestion q = QaDB.findQuestionById(id);
		if(q == null){
			flash("error", "could not find question with id "+id);
			redirect("/");
		}
		
		render(q);
		
	}
	
	/**
	 * Sets the content.
	 *
	 * @param id the id
	 * @param title the title
	 * @param content the content
	 */
	public static void setContent(long id, String title, String content){
		Logger.debug("Setting new content: \""+content+"\"");
		
		IQuestion q = QaDB.findQuestionById(id);
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
