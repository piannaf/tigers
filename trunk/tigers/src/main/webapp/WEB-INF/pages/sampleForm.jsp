<%@ include file="/common/taglibs.jsp"%>  
<head>     
	<title><fmt:message key="sampleList.title"/></title>     
	<meta name="heading" content="<fmt:message key='sampleList.heading'/>"/> 
	
    <script type="text/javascript" src="<c:url value='/scripts/calendar/jscal2.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/calendar/lang/en.js'/>"></script>
    <link rel="stylesheet" type="text/css" href="/styles/jscal2/jscal2.css" />
    <link rel="stylesheet" type="text/css" href="/styles/jscal2/border-radius.css" />
    <link rel="stylesheet" type="text/css" href="/styles/jscal2/gold/gold.css" />
</head>  

<form:form commandName="sample" method="post" action="sampleform.html" id="sampleForm"> 
<form:errors path="*" cssClass="error" element="div"/> 
<form:hidden path="id"/> 
<ul>  
	
	
   
	<li>         
		<appfuse:label styleClass="desc" key="sample.date_taken"/>         
		<form:errors path="date_taken" cssClass="fieldError"/>         
		<form:input  path="date_taken" id="date_taken" cssClass="text medium"/>
		<button id="dateButton" type="button" class="button"> ... </button>
	</li>      
	<script>
	   var dt=new Date();
	   var year=dt.getFullYear();
	   var month=dt.getMonth();
	   var date=dt.getDay();
	   var hour=dt.getHours();
	   var min=dt.getMinutes();
	   var datetime=year+"-"+month+"-"+date+" "+hour+" "+min;
	  
	   document.getElementById("date_taken").value=datetime;
	  
    </script>
	<li>         
		<appfuse:label styleClass="desc" key="sample.ph"/>         
		<form:errors path="ph" cssClass="fieldError"/>         
		<form:input path="ph" id="ph" cssClass="text medium"/>     
	</li>     
	
	<li>         
		<appfuse:label styleClass="desc" key="sample.ec"/>         
		<form:errors path="ec" cssClass="fieldError"/>         
		<form:input path="ec" id="ec" cssClass="text medium"/>    
	</li>     
		
	<li>         
		<appfuse:label styleClass="desc" key="sample.temperature"/>         
		<form:errors path="temperature" cssClass="fieldError"/>         
		<form:input path="temperature" id="temperature"	cssClass="text medium"/>    
	</li>     
	
	<li>         
		<appfuse:label styleClass="desc" key="sample.collar_depth"/>         
		<form:errors path="collar_depth" cssClass="fieldError"/>         
		<form:input path="collar_depth" id="collar_depth" cssClass="text medium"/>    
	</li>     
	
	<li>         
		<appfuse:label styleClass="desc" key="sample.arsenic"/>         
		<form:errors path="arsenic" cssClass="fieldError"/>         
		<form:input path="arsenic" id="arsenic" cssClass="text medium"/>    
	</li>     
	
	<li>         
		<appfuse:label styleClass="desc" key="sample.grease"/>         
		<form:errors path="grease" cssClass="fieldError"/>         
		<form:input path="grease" id="grease" cssClass="text medium"/>    
	</li>     
	
	<li>         
		<appfuse:label styleClass="desc" key="sample.fluoride"/>         
		<form:errors path="fluoride" cssClass="fieldError"/>         
		<form:input path="fluoride" id="fluoride" cssClass="text medium"/>    
	</li>     
	
	<li>         
		<appfuse:label styleClass="desc" key="sample.chromium"/>         
		<form:errors path="chromium" cssClass="fieldError"/>         
		<form:input path="chromium" id="chromium" cssClass="text medium"/>    
	</li>     
	
	<li>         
		<appfuse:label styleClass="desc" key="sample.username"/>         
		<form:errors path="username" cssClass="fieldError"/>         
		<form:input path="username" id="username" cssClass="text medium"/>    
	</li>     
	
	<li>         
		<appfuse:label styleClass="desc" key="sample.tag"/>         
		<form:errors path="tag" cssClass="fieldError"/>         
		<form:input path="tag" id="tag" cssClass="text medium"/>     
	</li>      
	
	<li class="buttonBar bottom">         
		<input type="submit" class="button" name="save" value="<fmt:message key="button.save"/>"/>         
		<c:if test="${not empty sample.id}">         
		<input type="submit" class="button" name="delete" onclick="return confirmDelete('sample')"              
			value="<fmt:message key="button.delete"/>" />         
		</c:if>         
		<input type="submit" class="button" name="cancel" value="<fmt:message key="button.cancel"/>"/>     
	</li> 
</ul> 
</form:form>  

<script type="text/javascript">     
	Form.focusFirstElement($('sampleForm'));
	
    Calendar.setup(
    	{
    	    inputField  : "date_taken",      // id of the input field
    	    //dateFormat    : "%d/%m/%Y %H:%M",      // the date format
    	    trigger      : "dateButton",    // id of the button
        	showTime: 12,
        	onTimeChange : updateFields

    	}
	);


    function updateFields(cal) {
        
//	    var date = cal.selection.get();
//	    if (date) {
//	            date = Calendar.intToDate(date);
//	            document.getElementById("date_taken").value = Calendar.printDate(date, "%d/%m/%Y");
//	    }
	    var hour=cal.getHours();
	    var min=cal.getMinutes();
	    date=document.getElementById("date_taken").value.split(" ")[0];
	    document.getElementById("date_taken").value=date+" "+hour+":"+min;
	};
</script> 