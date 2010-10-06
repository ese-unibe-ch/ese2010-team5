import org.apache.asyncweb.common.HttpResponse;
import org.apache.commons.httpclient.HttpStatus;
import org.junit.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void testThatIndexPageWorks() {
        
    		Response response = GET("/");        
        
        //redirect expected        
        assertStatus(302, response);
        
    }
    
    
    
    
    
    
}