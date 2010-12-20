package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import play.mvc.Controller;
import play.mvc.With;
import utils.QaImporter;

/**
 * The Class Admin.
 */
public class Admin extends Controller {

	
	
	/**
	 * Index.
	 */
	public static void index(){		
		
		render();
		
	}
	
	
	
	/**
	 * Upload an XML sample file.
	 *
	 * @param file XML-Import file
	 */
	public static void upload(File file){
	
		String[] logs = new String[]{};
		
		if(file != null){			
			
			FileInputStream fis;
      try {
	      fis = new FileInputStream(file);
	      QaImporter.importXml(fis);
	      logs = QaImporter.getLog();
      } catch (FileNotFoundException e) {
	      flash.error("something went wrong: %s", e.getMessage());
      }
		}else{
			flash.error("file is null");
		}
		
		render("Admin/index.html",logs);
		
	}
	
	
}
