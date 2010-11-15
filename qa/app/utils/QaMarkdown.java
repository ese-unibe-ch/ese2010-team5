package utils;

import org.pegdown.PegDownProcessor;

public class QaMarkdown {

	private static PegDownProcessor processor = new PegDownProcessor();
	
	private QaMarkdown(){}
	
	public static String toHtml(String markdown){
		
		if(markdown == null)
			throw new IllegalArgumentException("input needed");
		
		if(processor != null){			
			return processor.markdownToHtml(markdown);
		}
		
		return markdown;
		
	}
	
	
}
