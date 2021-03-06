package team5customtests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import utils.QaDB;
import models.*;
import models.impl.Post;
import models.impl.User;
import models.mock.MockNotification;
import models.mock.MockQuestion;
import models.mock.MockUser;

import org.junit.Before;
import org.junit.*;

public class UserTest extends UnitTest{
	Date curDate;
	User use;
	IUser use1,use2;
	INotification notify1,notify2;
	IQuestion quest1,quest2;
	List<INotification> notifications;

	@Before
	public void setUp() throws Exception {
		use = new User("Hans");
		use1 = new MockUser("Fritz");
		use2 = new MockUser("Johann");
		quest1 = new MockQuestion(use1,"TestQuestion");
		quest2 = new MockQuestion(use2,"TestQuestion2");
		notify1 = new MockNotification(use2,quest1);
		notify2 = new MockNotification(use1,quest2);
		curDate = new Date(System.currentTimeMillis());
		notifications = new LinkedList<INotification>();
	}
	
	@Test
	public void shouldProperlyAddAndReturnNotifications(){
		use.addNotification(notify1);
		notifications.add(notify1);
		assertEquals(use.getNotifications(),notifications);
		use.addNotification(notify2);
		notifications.add(notify2);
		assertEquals(use.getNotifications(),notifications);
	}
	

	@Test
	public void shouldProperlyRegisterandUnregister(){
		use.registerPost((Post) quest1);
		use.unregister((Post) quest1);
	}
	
	@Test
	public void shouldReturnUserName(){
		assertEquals("Hans",use.getUsername());
		assertEquals("Hans",use.getName());
	}
	
	@Test
	public void shouldSetUserName(){
		assertEquals("Hans",use.getName());
		use.setUsername("Peter");
		assertEquals("Peter",use.getName());
	}
	
	@Test
	public void shouldProperlySetAndGetEmail(){
		use.setEmail("myaddress@myprovider.mydomain");
		assertEquals("myaddress@myprovider.mydomain",use.getEmail());
	}
	
	@Test
	public void shouldReturnUserID(){
		assertTrue(use.getId()>0);
	}
	
	@Test 
	public void shouldProperlySetAndGetHomePage(){
		use.setHomepage("www.internet.ch");
		assertEquals("www.internet.ch",use.getHomepage());
	}
	
	@Test
	public void shouldProperlySetAndGetDateOfBirth(){
		use.setBirthDate(curDate);
		assertEquals(curDate,use.getBirthDate());
	}

}
