<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="assignLaboratory.title"/></title>
    <meta name="heading" content="<fmt:message key='assignLaboratory.heading'/>"/>
    <meta name="menu" content="ContractorSamplerMenu"/>
</head>

<!-- ======================================================================================== -->
<form:form commandName="assignLab" method="post" action="/contractor/assignlaboratory.html" id="findContractorForm"
				>
	<ul>
	    <li class="info">
	        <fmt:message key="assignLaboratory.message"/>
	    </li>
	    <li>
	        <appfuse:label key="assignLaboratory.samplerId" styleClass="desc"/>
	        <form:errors path="samplerId" cssClass="fieldError"/>
	        <form:select path="samplerId" id="samplerId" cssClass="text medium">
	        	<c:forEach items="${samplerIdList}" var="samplerId">
	        		<option value="<c:out value="${samplerId}"/>"
	        			<c:choose>
	        				<c:when test="${samplerId == id}">selected</c:when>
	        			</c:choose> 
	        		><c:out value="${samplerId}"/></option>
	        	</c:forEach>
	        </form:select>
	        <appfuse:label key="assignLaboratory.lab" styleClass="desc"/>
	        <form:errors path="lab" cssClass="fieldError"/>
	        <form:select path="lab" id="lab" cssClass="text medium">
	        	<c:forEach items="${labList}" var="labname">
	        		<option value="<c:out value="${labname}"/>"
	        			<c:choose>
	        				<c:when test="${labname == lab}">selected</c:when>
	        			</c:choose> 
	        		><c:out value="${labname}"/></option>
	        	</c:forEach>
	        </form:select>
	        <input type="submit" class="button" name="submit"/>
    	</li>
    </ul>
</form:form>
<!-- ======================================================================================== -->



