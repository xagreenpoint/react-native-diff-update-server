function showTip(textJson){
	  var handle =null;
	  $('a[id^="tip_"]').hover(function(){ 
		     var ids=$(this).attr("id");		
		     var id=ids.split("_")[1];         
		     $('a[id^="tip_"]').css("text-decoration","none");
			 $('a[id^="tip_"]').css("cursor","pointer");	
			 handle=setTimeout(function(){		    		       			       			    			  	   
				 $.getJSON(ctx+"/pages/jsontemp/"+textJson,function(data){ 
					 $.each(data,function(infoIndex,info){ 
						 if(infoIndex==id){	   
	                     var offset =  $('#'+ids).offset();
	                     var width = offset.left+$('#'+ids).width(); 
	                     $('#'+ids).parent().append("<div class='popover' style='position: absolute;top:10;padding:5px;'><div class='arrow' style='position:absolute;top:0;border-left: 20px solid transparent; border-right: 20px solid transparent;border-bottom: 5px solid #0287D0;'></div><div class='tip'></div></div>");                           
	                     $(".tip").attr("style","z-index:100;position:absolute;width:360px;border:2px solid #0287D0;white-space:normal;word-break:break-all;text-align:left;background-color:#fff;color:#000;padding:2px;overflow:hidden; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 3px 7px rgba(0, 0, 0, 0.5);  -moz-box-shadow: 0 3px 7px rgba(0, 0, 0, 0.5); box-shadow: 0 3px 7px rgba(0, 0, 0, 0.5);");
	                     var clientWidth=$(window).width();
	                     $(".popover").css("left",offset.left-$('.tip').width()/2);
	                     $(".arrow").css("left","150px");
	                     if(clientWidth>1024&&width>900){
	                            $(".popover").css("left",offset.left-$('.tip').width()/1.1);
	                            $(".arrow").css("left","290px");
	                     }
	                     if(width<250){
	                    	 $(".popover").css("left",offset.left/3);
	                         $(".arrow").css("left",width/3);
	                     }
	                     if(clientWidth<=1024&&width>650){
					             $(".popover").css("left",offset.left-$('.tip').width()/1.1);
					             $(".arrow").css("left","290px");
					     }
	                     var name="<p>"+info["name"]==undefined?"":"<span style='color:#0287D0'>"+info["name"]+":</span>";
	                     var mark=info["mark"]==undefined?"":info["mark"]+"<br/>";
	                     var dimension=info["dimension"]==undefined?"":"<span style='color:#0287D0'>统计维度:</span>"+info["dimension"]+"<br/>";
	                     var calculation=info["calculation"]==undefined?"":"<span style='color:#0287D0'>计算公式:</span>"+info["calculation"]+"<br/>";
	                     var area=info["area"]==undefined?"":"<span style='color:#0287D0'>统计地域维度:</span>"+info["area"]+"</p>";
	                     $(".tip").html(name+mark+dimension+calculation+area);                          					                              					        
					     }
					 }); 			    	 
		     });		     			   
			}, 200);
		 },function(){
			 clearTimeout(handle);
		     $('div.popover').hide();	
		 });	
}

function showTipDynamic(data){
	  var handle =null;
	  $('a[id^="tip_"]').hover(function(){ 
		     var ids=$(this).attr("id");		
		     var id=ids.split("_")[1];         
		     $('a[id^="tip_"]').css("text-decoration","none");
			 $('a[id^="tip_"]').css("cursor","pointer");	
			 handle=setTimeout(function(){
				 var tipHtml = "";
				 $.each(data,function(infoIndex,info){ 
                     var offset =  $('#'+ids).offset();
                     var width = offset.left+$('#'+ids).width(); 
                     $('#'+ids).parent().append("<div class='popover' style='position: absolute;top:10;padding:5px;'><div class='arrow' style='position:absolute;top:0;border-left: 20px solid transparent; border-right: 20px solid transparent;border-bottom: 5px solid #0287D0;'></div><div class='tip'></div></div>");                           
                     $(".tip").attr("style","z-index:100;position:absolute;width:360px;border:2px solid #0287D0;white-space:normal;word-break:break-all;text-align:left;background-color:#fff;color:#000;padding:2px;overflow:hidden; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 3px 7px rgba(0, 0, 0, 0.5);  -moz-box-shadow: 0 3px 7px rgba(0, 0, 0, 0.5); box-shadow: 0 3px 7px rgba(0, 0, 0, 0.5);");
                     var clientWidth=$(window).width();
                     $(".popover").css("left",offset.left-$('.tip').width()/2);
                     $(".arrow").css("left","150px");
                     if(clientWidth>1024&&width>900){
                            $(".popover").css("left",offset.left-$('.tip').width()/1.1);
                            $(".arrow").css("left","290px");
                     }
                     if(width<250){
                    	 $(".popover").css("left",offset.left/3);
                         $(".arrow").css("left",width/3);
                     }
                     if(clientWidth<=1024&&width>650){
				             $(".popover").css("left",offset.left-$('.tip').width()/1.1);
				             $(".arrow").css("left","290px");
				     }
                     var name="<p>"+info["name"]==undefined?"":"<span style='color:#0287D0'>"+info["name"]+":</span>";
                     var mark=info["mark"]==undefined?"":info["mark"]+"<br/>";
                     tipHtml += name+mark;
				 });
				 $(".tip").html(tipHtml);
			},200);
		 },function(){
			 clearTimeout(handle);
		     $('div.popover').hide();	
		 });	
}
