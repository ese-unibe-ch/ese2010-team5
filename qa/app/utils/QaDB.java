package utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


import models.*;

public class QaDB {
	
	public enum OrderBy{
		DATE,
		RATING,
		/* maybe more to come here */
	}
	
	/* anonymous user, needed to assign questions of deleted users*/
	public static User ANONYMOUS = new User("ANONYMOUS");

	/* maps: id -> instance> */
	private static HashMap<Long,Answer> 	answers 		= new HashMap<Long, Answer>();
	private static HashMap<Long,Question> questions 	= new HashMap<Long, Question>();
	private static HashMap<Long,User> 		users 			= new HashMap<Long, User>();
	private static HashMap<Long,Comment> comments 		= new HashMap<Long, Comment>();
	private static HashMap<Long,Tag> tags = new HashMap<Long, Tag>();	
	
	/* functions to add*/
	public static Answer addAnswer(Answer a){
		if(a == null) return null;		
		answers.put(a.getId(), a);
		return a;
	}
	
	public static Question addQuestion(Question q){
		if(q == null) return null;		
		questions.put(q.getId(), q);
		return q;
	}
	
	public static User addUser(User u){
		if(u== null) return null;		
		users.put(u.getId(), u);
		return u;
	}
	
	public static Comment addComment(Comment c){
		if(c== null) return null;		
		comments.put(c.getId(), c);
		return c;
	}	
	
	public static Tag addTag(Tag tag) {
		if(tag == null)
			return null;
		tags.put(tag.getId(), tag);
		return tag;
		
	}	
	
	/* functions to delete*/
	public static boolean delPost(Post inPost){
		
		if(inPost == null) return false;
		
		if(questions.containsKey(inPost.getId()))
			return questions.remove(inPost.getId()) != null;
		
		return answers.remove(inPost.getId()) != null;
	}
	
	public static void removeUser(User user){
		if (user == null)
			return;
		users.remove(user);
	}
	
	/* find single instance*/
	public static User findUserById(long id){
		return users.get(id);
	}
	
	public static User findUserByName(String inName){
		for(User u : users.values()){
			if(u.getName().equals(inName))
				return u;
		}
		
		return null;
	}
	
	public static Question findQuestionById(long id){
		return questions.get(id);
	}
	
	public static Answer findAnswerById(long id){
		return answers.get(id);
	}
	
	public static Post findPostById(long id){
		if(questions.containsKey(id))
			return questions.get(id);
		else if(answers.containsKey(id))
		  return answers.get(id);
		else
		  return comments.get(id);  
	}
	
	public static Comment findCommentById(long id){
		return comments.get(id);
	}
	
	public static Tag findTagByName(String name){
		for(Tag tag : tags.values()){
			if(tag.getName().equals(name))
				return tag;
		}
		
		return null;
	}
	
	/* find multiple instances */
	
	public static Collection<User> findAllUsers(){
		return users.values();
	}
	
	public static Collection<Question> findAllQuestions(){
		return questions.values();
	}
	
	public static Collection<Question> findAllQuestions(OrderBy inOrderBy){
		
		List<Question> result = new LinkedList<Question>();
		for(Question q : questions.values()){
			result.add(q);
		}
		
		Collections.sort(result, new QaSorter(inOrderBy));
		
		return result;		
		
	}
	
	public static Collection<Question> findAllQuestionsOfUser(User user){
		List<Question> result = new LinkedList<Question>();
		for(Question q : questions.values()){
			if(q.getOwner() == user){
				result.add(q);
			}
		}
		return result;
	}
	
	public static Collection<Answer> findAllAnswers(){
		return answers.values();
	}	
	
	public static Collection<Comment> findAllComments(){
		return comments.values();
	}
	
	public static Collection<Tag> findAllTags(){
		return tags.values();
	}
	
	public static Collection<Question> findAllQuestionsTaggedWith(Tag tag){
		List<Question> result = new LinkedList<Question>();
		for(Question q : questions.values()){
			if(q.isTaggedWith(tag)){
				result.add(q);
			}
		}
		return result;
	}
	
	public static Collection<Question> findAllQuestionsTaggedWith(Tag[] tags){
		List<Question> result = new LinkedList<Question>();
		for(Question q : questions.values()){
			if(q.isTaggedWith(tags)){
				result.add(q);
			}
		}
		return result;
	}

	
	
	
}
