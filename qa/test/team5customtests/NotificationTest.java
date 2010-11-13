package team5customtests;

import static org.junit.Assert.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

import org.junit.Before;
import org.junit.*;

public class NotificationTest  extends UnitTest{
	IUser use;
	IQuestion quest;
	Notification notify;
		

	@Before
	public void setUp() throws Exception {
		//notify = new Notification(use,quest,null);
	}

	@Test
	public void testNotification() {
		fail("Not yet implemented");
	}

}
