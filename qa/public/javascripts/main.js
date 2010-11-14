/*function for text-editor tabs*/
function teTab(el){

	  if(!el) return;	  
	  /*find text-editor in correct context*/	  
	  var te = el.parent().parent();

	  if(!te) return;
		
	  /*find elements in context of te*/
	  var markdown = $(".tab-write textarea",te).val();
	  var preview = $(".tab-preview",te);
	  var write   = $(".tab-write",te);

	  var showPreview = el.text() == "preview" ? true : false;

	  $(".body",preview).html("Loading..");	  

	  /*switch tabs*/
	  if(showPreview){		  
		  write.hide();
		  preview.show();

		  $.post( "/preview"
		            ,{input:markdown}
		            ,function (data){
		              //update rating
		              $(".body",preview).html(data);
		                    
		            },"text/html");
		  
		}else{
			write.show();
		  preview.hide();
		}
}