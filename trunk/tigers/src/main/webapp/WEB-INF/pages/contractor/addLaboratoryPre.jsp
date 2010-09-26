<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="user.add.laboratory.title"/></title>
    <meta name="heading" content="<fmt:message key='user.add.laboratory.heading'/>"/>
    <meta name="menu" content="ContractorLabMenu"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
</head>




<form:form commandName="lab" method="post" action="/contractor/addlaboratorypre.html" id="addLaboratoryPreForm"
				>
	<ul>
	    <li class="info">
	        <fmt:message key="addLaboratoryPre.message"/>
	    </li>
	    <li>
	        <appfuse:label key="user.username" styleClass="desc"/>
	        <form:errors path="tag" cssClass="fieldError"/>
	        <form:input path="tag" id="tag" cssClass="text medium" cssErrorClass="text medium error"/>
	        
	        <input type="submit" class="button" name="submit"/>
    	</li>
    </ul>
</form:form>