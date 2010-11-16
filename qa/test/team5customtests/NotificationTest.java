package team5customtests;

import static org.junit.Assert.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;
import models.INotification.Type;
import models.impl.Notification;
import models.mock.MockQuestion;
import models.mock.MockUser;

import org.junit.Before;
import org.junit.*;
import java.security.InvalidParameterException;

public class NotificationTest  extends UnitTest{
	IUser use;
	IQuestion quest;
	Notification notify,notify2;
	Notification.Type type;
		

	@Before
	public void setUp() throws Exception {
		use = new MockUser("Hans");
		quest = new MockQuestion(use,"JunitQuestion");
		notify = new Notification(use,quest,type.NEW_ANSWER);
	}
	
	@Test (expected=InvalidParameterException.class)
	public void shouldReturnErrorOnNULLUser(){
		notify2 = new Notification(null,quest,type.NEW_ANSWER);
	}
	
	@Test (expected=InvalidParameterException.class)
	public void shouldReturnErrorOnNULLQuestion(){
		notify2 = new Notification(use,null,type.NEW_ANSWER);
	}
	
	@Test (expected=InvalidParameterException.class)
	public void shouldReturnErrorOnNULLType(){
		notify2 = new Notification(use,quest,null);
	}
	
	@Test
	public void shouldReturnCorrectTimeStamp() {
		// Testing Timestamp with accuracy of seconds
		// because there has some time passed since construction
		assertEquals(System.currentTimeMillis() / 1000, notify.createdAt().getTime() / 1000);
	}

	
	@Test
	public void shouldNotBeReadAfterConstruction(){
		assertFalse(notify.isRead());
	}
	
	@Test
	public void shouldMarkAsRead(){
		notify.markAsRead();
		assertTrue(notify.isRead());
	}
	
	@Test
	public void shouldReturnBeloningQuestion(){
		assertEquals(quest,notify.getQuestion());
	}
	
	@Test
	public void shouldReturnOriginator(){
		assertEquals(use,notify.getOriginator());
	}
	
	@Test
	public void shouldReturnNotificationType(){
		assertEquals(Notification.Type.NEW_ANSWER,notify.getType());
	}


}
