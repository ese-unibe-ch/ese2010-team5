package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import models.IQuestion;
import models.IUser;
import models.impl.Answer;
import models.impl.Question;
import models.impl.User;

public class QaImporter {

	private static final XMLInputFactory factory	
		= XMLInputFactory.newInstance();
	
	private static IUser 			dummyUser 	= new User("dummy","");
	private static IQuestion  dummyQuest  = new Question(dummyUser,"dummy quest");
	
	private static HashMap<Long, User> 		userMap = new HashMap<Long, User>();
	private static HashMap<Long, Question> questMap = new HashMap<Long, Question>();
	private static HashMap<Long, Answer> answerMap = new HashMap<Long, Answer>();
	
	private static List<String> logs = new LinkedList();
	
	private enum STATE{
		UNKNOWN,
		
		USER,
		USER_NAME,
		USER_MAIL,
		USER_HOMEPAGE,		
		
		QUESTION,
		QUESTION_OWNER,
		QUESTION_TIMESTAMP,
		QUESTION_TITLE,
		QUESTION_CONTENT,
		QUESTION_TAGS,
		QUESTION_TAG,
		
		ANSWER,
		ANSWER_OWNER,		
		ANSWER_TIMESTAMP,
		ANSWER_CONTENT,
		ANSWER_QUESTIONID,
		ANSWER_MARKASBEST,		
		
	}
	
	public static boolean importXml(InputStream is){
		try {
			XMLStreamReader r = factory.createXMLStreamReader(is);
			STATE state = STATE.UNKNOWN;
			logs = new LinkedList<String>();
			userMap.clear();
			questMap.clear();
			answerMap.clear();
			StringBuilder content = new StringBuilder();
			long timestamp = 0;
			String id = "";
			User u 		= null;
			Question q = null;
			Answer	 a = null;
			while(r.hasNext()){
				switch(r.next()){
				case XMLStreamReader.START_ELEMENT:					
					if(r.getLocalName().equals("user")){
						state = STATE.USER;
						u = new User("gaga","");
						/*we do a mapping*/						
						id = r.getAttributeValue(0);
						userMap.put(Long.valueOf(id), u);
					}else if(r.getLocalName().equals("email")){
						state = STATE.USER_MAIL;
					}else if(r.getLocalName().equals("displayname")){
						state = STATE.USER_NAME;
					}else if(r.getLocalName().equals("website")){
						state = STATE.USER_HOMEPAGE;						
					}else if(r.getLocalName().equals("question")){
						state = STATE.QUESTION;
						q=new Question(dummyUser, "dummy content will be replaced later");						
						id = r.getAttributeValue(0);
						questMap.put(Long.valueOf(id), q);						
						System.out.println("new question: "+id);						
					}else if(r.getLocalName().equals("ownerid")){
						state = q != null ? STATE.QUESTION_OWNER : STATE.ANSWER_OWNER; 
					}else if(r.getLocalName().equals("creationdate")){
						state = q != null ? STATE.QUESTION_TIMESTAMP 
								: STATE.ANSWER_TIMESTAMP;
					}else if(r.getLocalName().equals("body")){
						state = q != null ? STATE.QUESTION_CONTENT 
								: STATE.ANSWER_CONTENT;
					}else if(r.getLocalName().equals("title")){
						state = q != null ? STATE.QUESTION_TITLE 
								: STATE.UNKNOWN;
					}else if(r.getLocalName().equals("tags")){
						state = q != null ? STATE.QUESTION_TAGS
								: STATE.UNKNOWN;
					}else if(r.getLocalName().equals("tag")){
						state = STATE.QUESTION_TAG;
					}else if(r.getLocalName().equals("questionid")){
						state = STATE.ANSWER_QUESTIONID;
					}else if(r.getLocalName().equals("answer")){
						state = STATE.ANSWER;
						a=	new Answer(dummyUser, "dummy", dummyQuest);						
						id = r.getAttributeValue(0);
						answerMap.put(Long.valueOf(id), a);					
						
					}else if(r.getLocalName().equals("accepted")){
						state = STATE.ANSWER_MARKASBEST;
					}
					
					break;				
									
				case XMLStreamReader.END_ELEMENT:	
					if(r.getLocalName().equals("user")){						
						u = null;
					}else if(r.getLocalName().equals("question")){						
						q.setContent(content.toString(),true);						
						content = new StringBuilder();
						q = null;
					}else if(r.getLocalName().equals("answer")){						
						a.setContent(content.toString());						
						content = new StringBuilder();
						a = null;
					}
					state = STATE.UNKNOWN;	
					break;
				case XMLStreamReader.END_DOCUMENT:
					logs.add("nr of imported users: "+userMap.size());
					logs.add("nr of imported questions: "+questMap.size());
					logs.add("nr of imported answers: "+answerMap.size());
					
					toDB();
					
					break;
				default:
					switch(state){
					case USER_MAIL:						
						u.setEmail(r.getText());
						break;
					case USER_NAME:
						u.setUsername(r.getText());
						break;
					case USER_HOMEPAGE:
						u.setHomepage(r.getText());
						break;
					case QUESTION_OWNER:
						id = r.getText();
						q.setOwner(userMap.get(Long.parseLong(id)));
						break;
					case QUESTION_TIMESTAMP:
						timestamp = Long.parseLong(r.getText());						
						q.setTimestamp(new Date(timestamp*1000));
						break;
					case QUESTION_TITLE:
						q.setTitle(r.getText());
						break;
					case QUESTION_CONTENT:
						content.append(r.getText());
						break;
					case QUESTION_TAG:
						q.addTag(r.getText());
						break;
					case ANSWER_CONTENT:
						content.append(r.getText());
						break;
					case ANSWER_TIMESTAMP:
						timestamp = Long.parseLong(r.getText());
						a.setTimestamp(new Date(timestamp));
						break;
					case ANSWER_OWNER:
						id = r.getText();
						a.setOwner(userMap.get(Long.valueOf(id)));
						break;
					case ANSWER_QUESTIONID:
						id = r.getText();
						a.setQuestion(questMap.get(Long.valueOf(id)));
					case ANSWER_MARKASBEST:
						a.setIsBest(r.getText().equals("true"));
					}					
				}				
			}
			return true;
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			logs.add("error: "+e.getMessage());
		}	catch (Exception e){
			logs.add("error: "+e.getMessage());
		}
		
		return false;	
		
	}
	
	public static String[] getLog(){
		return logs != null ? 
				logs.toArray(new String[]{}) : 
				new String[]{};
	}
	
	private static void toDB(){
		/*users*/
		for(User u : userMap.values()){
			QaDB.addUser(u);
		}
		for(Question q : questMap.values()){
			QaDB.addQuestion(q);
		}
		for(Answer a : answerMap.values()){
			QaDB.addAnswer(a);
		}
		
		
	}
	
	public static void main(String[] args){
		
		File f = new File(args[0]);
		
		try {
			importXml(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
