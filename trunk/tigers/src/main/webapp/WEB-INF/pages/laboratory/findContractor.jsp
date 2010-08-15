<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="findContractor.title"/></title>
    <meta name="heading" content="<fmt:message key='findContractor.heading'/>"/>
    <meta name="menu" content="LaboratoryMenu"/>
</head>

<!-- ======================================================================================== -->
<form:form commandName="tag" method="post" action="/laboratory/findcontractor.html" id="findContractorForm"
				>
	<ul>
	    <li class="info">
	        <fmt:message key="findContractor.message"/>
	    </li>
	    <li>
	        <appfuse:label key="findContractor.tag" styleClass="desc"/>
	        <form:errors path="tag" cssClass="fieldError"/>
	        <form:select path="tag" id="tag" cssClass="text medium">
	        	<c:forEach items="${samplerIdList}" var="samplerId">
	        		<option value="<c:out value="${samplerId}"/>"
	        			<c:choose>
	        				<c:when test="${samplerId == id}">selected</c:when>
	        			</c:choose> 
	        		><c:out value="${samplerId}"/></option>
	        	</c:forEach>
	        </form:select>
	        <input type="submit" class="button" name="submit"/>
    	</li>
    </ul>
</form:form>
<!-- ======================================================================================== -->
<c:if test="${not empty firstTime}">
<table class="detail" cellpadding="5">
	<tr>
		<td>
			<strong><fmt:message key="user.companyName"/>:</strong>
		</td>
		<td>
			${user.companyName}
		</td>
	</tr>
	<tr>
		<td>
			<strong><fmt:message key="user.phoneNumber"/>:</strong>			
		</td>
		<td>
			${user.phoneNumber}
		</td>
	</tr>
	<tr>
		<td>
			<strong><fmt:message key="user.email"/>:</strong>
		</td>
		<td>
			${user.email}
		</td>
	</tr>
	<tr>
		 <td><strong><fmt:message key="user.address.address"/>:</strong></td>
         <td>${user.address.address}</td>
    </tr>
    <tr>
		 <td><strong><fmt:message key="user.address.city"/>:</strong></td>
         <td>${user.address.city}</td>
    </tr>
	<tr>
		 <td><strong><fmt:message key="user.address.postalCode"/>:</strong></td>
         <td>${user.address.postalCode}</td>
    </tr>
    <tr>
		 <td><strong><fmt:message key="user.address.province"/>:</strong></td>
         <td>${user.address.province}</td>
    </tr>
    <tr>
		 <td><strong><fmt:message key="user.address.country"/>:</strong></td>
         <td>${user.address.country}</td>
    </tr>
	<tr>	
		<td class="buttonBar">
			<form method="post" action="/laboratory/findcontractor.html">
				<input type="hidden" name="emailTo" value="${user.username}" id="mailTo"/>
	   			<input type="submit" class="button" name="sendEmail" value="<fmt:message key="button.sendEmail"/>"/>
	   		</form>
		</td>
	</tr>
</table>
</c:if>

	
	
	

<!-- ======================================================================================== -->
