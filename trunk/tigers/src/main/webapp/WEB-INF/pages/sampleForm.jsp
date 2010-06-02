<%@ include file="/common/taglibs.jsp"%>  
<head>     
	<title><fmt:message key="sampleList.title"/></title>     
	<meta name="heading" content="<fmt:message key='sampleList.heading'/>"/> 
</head>  

<form:form commandName="sample" method="post" action="sampleform.html" id="sampleForm"> 
<form:errors path="*" cssClass="error" element="div"/> 
<form:hidden path="id"/> 
<ul>  
	
	
   
	<li>         
		<appfuse:label styleClass="desc" key="sample.date_taken"/>         
		<form:errors path="date_taken" cssClass="fieldError"/>         
		<form:input path="date_taken" id="date_taken" cssClass="text medium"/>     
	</li>      
	
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
</script> 