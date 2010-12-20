package utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.management.Notification;
import javax.naming.directory.SearchResult;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import play.Logger;

import models.*;
import models.impl.Answer;
import models.impl.Comment;
import models.impl.Post;
import models.impl.Question;
import models.impl.Tag;
import models.impl.User;

/**
 * The Class QaDB that manages the Question and answers Database.
 */
public class QaDB {

	/**
	 * The Enum OrderBy that holds the possible ordertype.
	 */
	public enum OrderBy {
		DATE, 
        RATING,
		/* maybe more to come here */
	}

	/* anonymous user, needed to assign questions of deleted users */
	/** The ANONYMOUS. */
	public static User ANONYMOUS = new User("ANONYMOUS","");

	/* maps: id -> instance> */
	/** The answers. */
	private static HashMap<Long, Answer> answers = new HashMap<Long, Answer>();
	
	/** The questions. */
	private static HashMap<Long, Question> questions = new HashMap<Long, Question>();
	
	/** The users. */
	private static HashMap<Long, User> users = new HashMap<Long, User>();
	
	/** The comments. */
	private static HashMap<Long, Comment> comments = new HashMap<Long, Comment>();
	
	/** The tags. */
	private static HashMap<Long, Tag> tags = new HashMap<Long, Tag>();
	
	/** The notifications. */
	private static HashMap<Long, INotification> notifications = new HashMap<Long, INotification>();
	

	/* functions to add */
	/**
	 * Adds the answer.
	 *
	 * @param a the answer
	 * @return the answer
	 */
	public static Answer addAnswer(Answer a) {
		if (a == null)
			return null;
		answers.put(a.getId(), a);
		return a;
	}

	/**
	 * Adds the question.
	 *
	 * @param q the question
	 * @return the question
	 */
	public static Question addQuestion(Question q) {
		if (q == null)
			return null;
		questions.put(q.getId(), q);
		return q;
	}

	/**
	 * Adds the user.
	 *
	 * @param u the user
	 * @return the user
	 */
	public static User addUser(User u) {
		if (u == null)
			return null;
		users.put(u.getId(), u);
		return u;
	}

	/**
	 * Adds the comment.
	 *
	 * @param c the comment
	 * @return the comment
	 */
	public static Comment addComment(Comment c) {
		if (c == null)
			return null;
		comments.put(c.getId(), c);
		return c;
	}

	/**
	 * Adds the tag.
	 *
	 * @param tag
	 * @return the tag
	 */
	public static Tag addTag(Tag tag) {
		if (tag == null)
			return null;
		tags.put(tag.getId(), tag);
		return tag;

	}
	
	/**
	 * Adds the notification.
	 *
	 * @param n the notification
	 * @return the notification
	 */
	public static INotification addNotification(INotification n){
		if(n == null)
			return null;
		
		notifications.put(n.getId(), n);
		return n;
		
	}

	/* functions to delete */
	/**
	 * Delete post.
	 *
	 * @param inPost
	 * @return true, if successful
	 */
	public static boolean delPost(Post inPost) {

		if (inPost == null)
			return false;
		
		/*Answers has to remove themself from the list hold by the question*/
		/*same for comments*/
		inPost.delete();

		if (questions.containsKey(inPost.getId()))
			return questions.remove(inPost.getId()) != null;
		if (comments.containsKey(inPost.getId()))
			return comments.remove(inPost.getId()) != null;

		return answers.remove(inPost.getId()) != null;
	}

	/**
	 * Removes the user.
	 *
	 * @param user the user
	 */
	public static void removeUser(IUser user) {
		if (user == null)
			return;
		users.remove(user);
	}
	
	/**
	 * Delete notification.
	 *
	 * @param n the notifiaction
	 */
	public static void delNotification(INotification n){
		if(n == null)
			return;
		
		/*remove also from list of the user*/
		n.delete();
		
		notifications.remove(n);
	}

	/* find single instance */
	/**
	 * Find user by id.
	 *
	 * @param id
	 * @return the user
	 */
	public static User findUserById(long id) {
		return users.get(id);
	}

	/**
	 * Find user by name.
	 *
	 * @param inName the username
	 * @return the user
	 */
	public static User findUserByName(String inName) {
		for (User u : users.values()) {
			if (u.getName().equals(inName))
				return u;
		}

		return null;
	}

	/**
	 * Find question by id.
	 *
	 * @param id
	 * @return the question
	 */
	public static Question findQuestionById(long id) {
		return questions.get(id);
	}

	/**
	 * Find answer by id.
	 *
	 * @param id
	 * @return the answer
	 */
	public static Answer findAnswerById(long id) {
		return answers.get(id);
	}

	/**
	 * Find post by id.
	 *
	 * @param id
	 * @return the post
	 */
	public static Post findPostById(long id) {
		if (questions.containsKey(id))
			return questions.get(id);
		else if (answers.containsKey(id))
			return answers.get(id);
		else
			return comments.get(id);
	}

	/**
	 * Find comment by id.
	 *
	 * @param id
	 * @return the comment
	 */
	public static Comment findCommentById(long id) {
		return comments.get(id);
	}

	/**
	 * Find tag by name.
	 *
	 * @param name
	 * @return the tag
	 */
	public static Tag findTagByName(String name) {

		for (Tag tag : tags.values()) {
			if (tag.getName().equals(name)) {
				return tag;
			}
		}

		return null;
	}

	
	
	
	/**
	 * Find notification by id.
	 *
	 * @param id
	 * @return the notification
	 */
	public static INotification findNotificationById(long id){
		return notifications.get(id);
	}

	/* find multiple instances */

	/**
	 * Find all users.
	 *
	 * @return a usercollection
	 */
	public static Collection<User> findAllUsers() {
		return users.values();
	}

	/**
	 * Find all questions.
	 *
	 * @return the collection
	 */
	public static Collection<Question> findAllQuestions() {
		return questions.values();
	}

	/**
	 * Find all questions.
	 *
	 * @param inOrderBy the ordertype
	 * @return the collection
	 */
	public static Collection<Question> findAllQuestions(OrderBy inOrderBy) {

		List<Question> result = new LinkedList<Question>();
		for (Question q : questions.values()) {
			result.add(q);
		}

		Collections.sort(result, new QaSorter(inOrderBy));

		return result;

	}

	/**
	 * Find all questions of user.
	 *
	 * @param user
	 * @return the questionslist
	 */
	public static List<IQuestion> findAllQuestionsOfUser(IUser user) {
		List<IQuestion> result = new LinkedList<IQuestion>();
		for (Question q : questions.values()) {
			if (q.getOwner() == user) {
				result.add(q);
			}
		}
		return result;
	}
	
	/**
	 * Find all questions of user.
	 *
	 * @param user the user
	 * @param orderBy the orderstyle
	 * @return the list
	 */
	public static List<IQuestion> findAllQuestionsOfUser(IUser user, OrderBy orderBy) {
		List<IQuestion> result = findAllQuestionsOfUser(user);
		
		Collections.sort(result, new QaSorter(orderBy));
		
		return result;
	}

	/**
	 * Find all answers.
	 *
	 * @return an answercollection
	 */
	public static Collection<Answer> findAllAnswers() {
		return answers.values();
	}

	/**
	 * Find all comments.
	 *
	 * @return the collection
	 */
	public static Collection<Comment> findAllComments() {
		return comments.values();
	}

	/**
	 * Find all tags.
	 *
	 * @return the collection
	 */
	public static Collection<Tag> findAllTags() {
		return tags.values();
	}
	
	/**
	 * Find all questions tagged with.
	 *
	 * @param tag
	 * @return the collection
	 */
	public static Collection<Question> findAllQuestionsTaggedWith(Tag tag) {
		return findAllQuestionsTaggedWith(tag,null);
	}
	

	/**
	 * Find all questions tagged with.
	 *
	 * @param tag 
	 * @param orderBy the orderstyle
	 * @return the collection
	 */
	public static Collection<Question> findAllQuestionsTaggedWith(Tag tag, OrderBy orderBy) {
		List<Question> result = new LinkedList<Question>();
		for (Question q : questions.values()) {
			if (q.isTaggedWith(tag)) {
				result.add(q);
			}
		}
		
		if(orderBy != null){
			Collections.sort(result, new QaSorter(orderBy));
		}
		
		return result;
	}

	/**
	 * Find all questions tagged with.
	 *
	 * @param tag
	 * @return the collection
	 */
	public static Collection<Question> findAllQuestionsTaggedWith(String tag) {
		List<Question> result = new LinkedList<Question>();
		for (Question q : questions.values()) {
			if (q.isTaggedWith(tag)) {
				result.add(q);
			}
		}
		return result;
	}

	/**
	 * Find all questions tagged with.
	 *
	 * @param tags
	 * @return a questioncollection
	 */
	public static Collection<Question> findAllQuestionsTaggedWith(Tag[] tags) {
		List<Question> result = new LinkedList<Question>();
		for (Question q : questions.values()) {
			if (q.isTaggedWith(tags)) {
				result.add(q);
			}
		}
		return result;
	}

	/**
	 * Find all answers of user.
	 *
	 * @param user
	 * @return the list
	 */
	public static List<Answer> findAllAnswersOfUser(IUser user) {
		List<Answer> result = new LinkedList<Answer>();
		for (Answer a : answers.values()) {
			if (a.getOwner() == user) {
				result.add(a);
			}
		}
		return result;
	}

	/**
	 * Removes the answer.
	 *
	 * @param answer
	 */
	public static void removeAnswer(Answer answer) {
		answers.remove(answer);

	}

	/**
	 * Removes the question.
	 *
	 * @param question
	 */
	public static void removeQuestion(IQuestion question) {
		questions.remove(question);

	}

	/**
	 * Removes the comment.
	 *
	 * @param comment
	 */
	public static void removeComment(IComment comment) {
		comments.remove(comment);
	}

	/**
	 * Find all comments of user.
	 *
	 * @param user
	 * @return the commentslist
	 */
	public static List<Comment> findAllCommentsOfUser(User user) {
		List<Comment> result = new LinkedList<Comment>();
		for (Comment comment : comments.values()) {
			if (comment.getOwner() == user) {
				result.add(comment);
			}
		}
		
		return result;
	}
	
	/*searching*/
	/**
	 * Find all questions by query.
	 *
	 * @param query
	 * @return the list
	 */
	public static List<IQuestion> findAllQuestionsByQuery(String query){
		
		List<IQuestion> searchResult = new LinkedList<IQuestion>();
				
		for(Question q : questions.values()){
			if(q != null){
				
				String content = q.getContent();
				String title   = q.getTitle();
				
				if(content != null){
					if(content.toLowerCase().contains(query.toLowerCase()))
						searchResult.add(q);
				}
				if(title != null){
					if(title.toLowerCase().contains(query.toLowerCase())
							&& !searchResult.contains(q) )
						searchResult.add(q);
				}
			}
		}
		
		Collections.sort(searchResult, new QaSorter(OrderBy.DATE));
		
		return searchResult;		
	}	
	
	/**
	 * Find all users by query.
	 *
	 * @param query
	 * @return the list
	 */
	public static List<IUser> findAllUsersByQuery(String query){
		
		List<IUser> searchResult = new LinkedList<IUser>();
				
		for(IUser u : users.values() ){
			if(u != null){				
				String name = u.getName();				
				
				if(name != null){
					if(name.toLowerCase().contains(query.toLowerCase()))
						searchResult.add(u);
				}				
			}
		}
		
		return searchResult;
	}
		
	

}
