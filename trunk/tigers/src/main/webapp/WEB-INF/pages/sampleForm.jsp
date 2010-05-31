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
		<form:textarea path="ec" id="ec" rows="10" cols="10"          	
			cssClass="text medium"/>    
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