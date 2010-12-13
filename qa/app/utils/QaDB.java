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

public class QaDB {

	public enum OrderBy {
		DATE, RATING,
		/* maybe more to come here */
	}

	/* anonymous user, needed to assign questions of deleted users */
	public static User ANONYMOUS = new User("ANONYMOUS","");

	/* maps: id -> instance> */
	private static HashMap<Long, Answer> answers = new HashMap<Long, Answer>();
	private static HashMap<Long, Question> questions = new HashMap<Long, Question>();
	private static HashMap<Long, User> users = new HashMap<Long, User>();
	private static HashMap<Long, Comment> comments = new HashMap<Long, Comment>();
	private static HashMap<Long, Tag> tags = new HashMap<Long, Tag>();
	private static HashMap<Long, INotification> notifications = new HashMap<Long, INotification>();
	

	/* functions to add */
	public static Answer addAnswer(Answer a) {
		if (a == null)
			return null;
		answers.put(a.getId(), a);
		return a;
	}

	public static Question addQuestion(Question q) {
		if (q == null)
			return null;
		questions.put(q.getId(), q);
		return q;
	}

	public static User addUser(User u) {
		if (u == null)
			return null;
		users.put(u.getId(), u);
		return u;
	}

	public static Comment addComment(Comment c) {
		if (c == null)
			return null;
		comments.put(c.getId(), c);
		return c;
	}

	public static Tag addTag(Tag tag) {
		if (tag == null)
			return null;
		tags.put(tag.getId(), tag);
		return tag;

	}
	
	public static INotification addNotification(INotification n){
		if(n == null)
			return null;
		
		notifications.put(n.getId(), n);
		return n;
		
	}

	/* functions to delete */
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

	public static void removeUser(IUser user) {
		if (user == null)
			return;
		users.remove(user);
	}
	
	public static void delNotification(INotification n){
		if(n == null)
			return;
		
		/*remove also from list of the user*/
		n.delete();
		
		notifications.remove(n);
	}

	/* find single instance */
	public static User findUserById(long id) {
		return users.get(id);
	}

	public static User findUserByName(String inName) {
		for (User u : users.values()) {
			if (u.getName().equals(inName))
				return u;
		}

		return null;
	}

	public static Question findQuestionById(long id) {
		return questions.get(id);
	}

	public static Answer findAnswerById(long id) {
		return answers.get(id);
	}

	public static Post findPostById(long id) {
		if (questions.containsKey(id))
			return questions.get(id);
		else if (answers.containsKey(id))
			return answers.get(id);
		else
			return comments.get(id);
	}

	public static Comment findCommentById(long id) {
		return comments.get(id);
	}

	public static Tag findTagByName(String name) {

		for (Tag tag : tags.values()) {
			if (tag.getName().equals(name)) {
				return tag;
			}
		}

		return null;
	}

	
	
	
	public static INotification findNotificationById(long id){
		return notifications.get(id);
	}

	/* find multiple instances */

	public static Collection<User> findAllUsers() {
		return users.values();
	}

	public static Collection<Question> findAllQuestions() {
		return questions.values();
	}

	public static Collection<Question> findAllQuestions(OrderBy inOrderBy) {

		List<Question> result = new LinkedList<Question>();
		for (Question q : questions.values()) {
			result.add(q);
		}

		Collections.sort(result, new QaSorter(inOrderBy));

		return result;

	}

	public static List<IQuestion> findAllQuestionsOfUser(IUser user) {
		List<IQuestion> result = new LinkedList<IQuestion>();
		for (Question q : questions.values()) {
			if (q.getOwner() == user) {
				result.add(q);
			}
		}
		return result;
	}
	
	public static List<IQuestion> findAllQuestionsOfUser(IUser user, OrderBy orderBy) {
		List<IQuestion> result = findAllQuestionsOfUser(user);
		
		Collections.sort(result, new QaSorter(orderBy));
		
		return result;
	}

	public static Collection<Answer> findAllAnswers() {
		return answers.values();
	}

	public static Collection<Comment> findAllComments() {
		return comments.values();
	}

	public static Collection<Tag> findAllTags() {
		return tags.values();
	}
	
	public static Collection<Question> findAllQuestionsTaggedWith(Tag tag) {
		return findAllQuestionsTaggedWith(tag,null);
	}
	

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

	public static Collection<Question> findAllQuestionsTaggedWith(String tag) {
		List<Question> result = new LinkedList<Question>();
		for (Question q : questions.values()) {
			if (q.isTaggedWith(tag)) {
				result.add(q);
			}
		}
		return result;
	}

	public static Collection<Question> findAllQuestionsTaggedWith(Tag[] tags) {
		List<Question> result = new LinkedList<Question>();
		for (Question q : questions.values()) {
			if (q.isTaggedWith(tags)) {
				result.add(q);
			}
		}
		return result;
	}

	public static List<Answer> findAllAnswersOfUser(IUser user) {
		List<Answer> result = new LinkedList<Answer>();
		for (Answer a : answers.values()) {
			if (a.getOwner() == user) {
				result.add(a);
			}
		}
		return result;
	}

	public static void removeAnswer(Answer answer) {
		answers.remove(answer);

	}

	public static void removeQuestion(IQuestion question) {
		questions.remove(question);

	}

	public static void removeComment(IComment comment) {
		comments.remove(comment);
	}

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
	public static List<IQuestion> findAllQuestionsByQuery(String query){
		
		List<IQuestion> searchResult = new LinkedList<IQuestion>();
				
		for(Question q : questions.values()){
			if(q != null){
				
				String content = q.getContent();
				String title   = q.getTitle();
				
				if(content != null){
					if(content.toLowerCase().contains(query.toLowerCase())
							&& !searchResult.contains(q))
						searchResult.add(q);
				}
				if(title != null){
					if(title.toLowerCase().contains(query.toLowerCase())
							&& !searchResult.contains(q))
						searchResult.add(q);
				}
			}
		}
		
		Collections.sort(searchResult, new QaSorter(OrderBy.DATE));
		
		return searchResult;		
	}	
	
	public static List<IUser> findAllUsersByQuery(String query){
		
		List<IUser> searchResult = new LinkedList<IUser>();
				
		for(IUser u : users.values() ){
			if(u != null){				
				String name = u.getName();				
				
				if(name != null){
					if(name.toLowerCase().contains(query.toLowerCase())
							&& ! searchResult.contains(u))
						searchResult.add(u);
				}				
			}
		}
		
		return searchResult;
	}
		
	

}
