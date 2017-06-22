  	var list = ["id","userName","password","rpassword","email","checkcode"];
    var list2 = ["grade","department","major"];
    function checkNull(field){	//检查是否为空
    	var reg = /^\s*$/;//表示0个或多个空白符
    	var value =  document.getElementById(field).value;
    	if(reg.test(value)){
    		var span = document.getElementById(field+"Span");
    		span.style.visibility="visible";
    		return false;
    	}
    	return true;
    }
    function onFocus(field){
    	document.getElementById(field).onfocus = function(){
    		 document.getElementById(field+"Span").style.visibility="hidden";
    	}
    	
    }
    
    function onBlur(field){
    	document.getElementById(field).onblur = function(){
    		checkNull(field);
   	}
    }
    
    function clear(){
  	  for(var i=0;i<list2.length;i++){
  			onChange(list2[i]);
  		}
   }
  
    function checkForm(){
     
      	var flag = new Array();
      	var Flags = true;
      	for(var i=0;i<list.length;i++){
      		flag[i]=checkNull(list[i]);
      		Flags = (Flags&&flag[i]);
      		onFocus(list[i]);
      		onBlur(list[i]);
      	}
      	for(var i=0;i<list2.length;i++){
      		flag[i+list.length] = checkSelect(list2[i]);
      		Flags = (Flags&&flag[i+list.length]);
      	}
    		return Flags;
    }
    
	