package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.SortType;

import models.INotification;
import models.IPost;
import models.IQuestion;
import models.IUser;
import models.impl.Answer;
import models.impl.Comment;
import models.impl.Notification;
import models.impl.Post;
import models.impl.Question;
import models.impl.Tag;
import models.impl.User;

import play.Logger;
import play.data.validation.Error;
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
	public static void list(String tagname, String sort){
		
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
		if(params._contains("sort")){
			sortOrderEnum = OrderBy.valueOf(params.get("sort"));
		}
		
		Collection<Question> questions = QaDB.findAllQuestions(sortOrderEnum);
		String sort = sortOrderEnum.name();
		renderTemplate("Questions/list.html",questions,sort);		
	}
	 
	
	public static void listUser(String inSort){
						
		List<IQuestion> questions = null;
		OrderBy	orderBy = inSort != null ?
				OrderBy.valueOf(inSort) :
				OrderBy.DATE;
		String sort = orderBy.name();
		String tagname = "";
		
		if(user == null){
			flash.error("not logged in");			
			listAll();
			return;
		}else{
			tagname 	= user.getName();
			questions = QaDB.findAllQuestionsOfUser(user,orderBy);			
			render("Questions/list.html",questions,sort,tagname);
		}
		
	}
	
	public static void listSubscriptions(String inSort){
						
		List<IQuestion> questions = null;
		OrderBy	orderBy = inSort != null ?
				OrderBy.valueOf(inSort) :
				OrderBy.DATE;
		String sort = orderBy.name();
		String tagname = "";
		
		if(user == null){
			flash.error("not logged in");
			listAll();
			return;
		}else{
			tagname 	= "my subscriptions";
			questions = user.getSubscriptions();
			Collections.sort(questions, new QaSorter(orderBy));
			render("Questions/list.html",questions,sort,tagname);
		}
		
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
	public static void save(String content, String title, String[] tag){
		
		validation.required(title);
		validation.required(content);
		validation.required(tag);
		
		if (validation.hasErrors()){
			 params.flash(); // add http parameters to the flash scope
	         validation.keep(); // keep the errors for the next request
	         create();
		}
		
		Question.createQuestion(user, title, content, tag);	
		
		Logger.debug("Question with title \"" + title + "\" created.");
		flash.put("info", "Question with title \"" + title + "\" created.");
		
		listAll();
		
	}
	
	/**
	 * Creates the question.
	 */
	public static void create(){
		if (Security.connected() == null)
			listAll();
		render();	
	}

	/**
	 * Adds the answer.
	 *
	 * @param answer the answer
	 * @param qId the q id
	 */
	public static void addAnswer(String answer, long qId){
		Question question = QaDB.findQuestionById(qId);
		if(question == null){
			flash.error("could not find question q: "+qId);			
			redirect("/");
		}
		question.addAnswer(user, answer);
		flash.put("info", "Answer to question \""+question.getTitle()+"\" created.");
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
		Comment newComment = q.addComment(user, comment);
		
		flash.put("info", "new Comment created " + newComment.getId());
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
		
		if(user != null){
			for(INotification n : user.getNotifications()){
				if(user.equals(n.getSubscriber()) &&
						q.equals(n.getQuestion())){
					n.markAsRead();
				}
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
		flash.put("info","Content of question \"" + q.getTitle() + "\" changed");
		view(id);
	}
	
	public static void toggleSubscriber(long qId, long userId){
		IQuestion q = QaDB.findQuestionById(qId);
		IUser u = QaDB.findUserById(userId);
		
		if(u == null || q == null){
			flash.error("someting went wrong. user: %s, quest: %s", u,q);
			view(qId);
		}
		
		if(q.isSubscriber(u)){
			q.remSubscriber(u);
			flash("info", "unsubscribed from "+q.getTitle());

		}else{
			q.addSubscriber(u);
			flash("info", "subscribed to "+q.getTitle());
		}
		view(q.getId());
	}
}
