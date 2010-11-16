package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import models.IUser;
import models.impl.User;

public class QaImporter {

	private static final XMLInputFactory factory	
		= XMLInputFactory.newInstance();	
	
	private enum STATE{
		UNKNOWN,
		USER,
		USER_NAME,
		USER_MAIL,
		USER_HOMEPAGE,		
		QUESTION,
		ANSWER
	}
	
	public static boolean importXml(InputStream is){
		try {
			XMLStreamReader r = factory.createXMLStreamReader(is);
			STATE state = STATE.UNKNOWN;
			IUser u = null;
			while(r.hasNext()){
				switch(r.next()){
				case XMLStreamReader.START_ELEMENT:					
					if(r.getLocalName().equals("user")){
						state = STATE.USER;
						u = new User("gaga");
						/*we need a setter method on the user for 'id'*/
						String id = r.getAttributeValue(0);
						System.out.println("new user: "+id);
					}else if(r.getLocalName().equals("email")){
						state = STATE.USER_MAIL;
					}else if(r.getLocalName().equals("displayname")){
						state = STATE.USER_NAME;
					}else if(r.getLocalName().equals("website")){
						state = STATE.USER_HOMEPAGE;
					}					
					break;				
									
				case XMLStreamReader.END_ELEMENT:
					if(r.getLocalName().equals("user")){
						System.out.println("done with user: "+u.getName());
						System.out.println("mail: "+u.getEmail());
						System.out.println("hompage: "+u.getHomepage());
					}
					state = STATE.UNKNOWN;	
					break;
				case XMLStreamReader.END_DOCUMENT:
					System.out.println("we are done juhui");
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
					}
					
				}
				
			}
			return true;
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
		
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
