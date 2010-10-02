<%@ include file="/common/taglibs.jsp"%>  
<head>     
	<title><fmt:message key="samplerMedia.formtitle"/></title>     
	
</head>  

<form:form commandName="samplermedia" method="post" action="samplermediaform.html" id="samplerMediaForm"> 
<form:errors path="*" cssClass="error" element="div"/> 
<form:hidden path="id"/> 
<ul>  
	
	
   
	<li>
		<appfuse:label styleClass="desc" key="samplerMedia.description"/>         
		<form:errors path="description" cssClass="fieldError"/>         
		<form:input path="description" id="description" cssClass="text medium"/>
	</li>     
	<li>
		<appfuse:label styleClass="desc" key="samplerMedia.fileName"/>         
		<form:errors path="fileName" cssClass="fieldError"/>         
		<form:input path="fileName" id="fileName" cssClass="text medium"/>     
	</li>
	
	
	<li class="buttonBar bottom">         
		<input type="submit" class="button" name="save" value="<fmt:message key="button.save"/>"/>         
		<c:if test="${not empty samplermedia.id}">         
		<input type="submit" class="button" name="delete" onclick="return confirmDelete('samplermedia')"              
			value="<fmt:message key="button.delete"/>" />         
		</c:if>         
		<input type="submit" class="button" name="cancel" value="<fmt:message key="button.cancel"/>"/>     
	</li> 
</ul> 
</form:form>  

<script type="text/javascript">     
	Form.focusFirstElement($('samplerMediaForm'));
</script> 