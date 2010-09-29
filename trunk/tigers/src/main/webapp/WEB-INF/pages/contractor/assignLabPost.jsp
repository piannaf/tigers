<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="assignLaboratory.title"/></title>
    <meta name="heading" content="<fmt:message key='assignLaboratory.heading'/>"/>
    <meta name="menu" content="ContractorSamplerMenu"/>
</head>

<!-- ======================================================================================== -->
<form:form commandName="assignLab" method="post" action="/contractor/assignlabpost.html" id="findContractorForm"
				>
	<ul>
	    <li class="info">
	        <fmt:message key="assignLab.message"/>
	    </li>
	    <li>
	        <appfuse:label key="assignLaboratory.sampler" styleClass="desc"/>
	        <table class="detail" cellpadding="5">
			<tr>
				<td>
					<fmt:message key="sampler.tag"/>:
				</td>
				<td>
					${sampler.tag}
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="waterbody.name"/>:
				</td>
				<td>
					${sampler.waterbody.name}
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="user.username"/>:
				</td>
				<td>
					${sampler.laboratory.username}
				</td>
			</tr>
		</table>
	        <form:hidden path="samplerId"/>
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



