package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import play.mvc.Controller;
import play.mvc.With;
import utils.QaImporter;

public class Admin extends Controller {

	
	
	public static void index(){		
		
		render();
		
	}
	
	
	
	public static void upload(File file){
	
		String[] logs = new String[]{};
		
		if(file != null){
			flash.success("importing..");
			
			FileInputStream fis;
      try {
	      fis = new FileInputStream(file);
	      QaImporter.importXml(fis);
	      logs = QaImporter.getLog();
      } catch (FileNotFoundException e) {
	      // TODO Auto-generated catch block
	      flash.error("something went wrong: %s", e.getMessage());
      }
		}else{
			flash.error("file is null");
		}
		
		render("Admin/index.html",logs);
		
	}
	
	
}
