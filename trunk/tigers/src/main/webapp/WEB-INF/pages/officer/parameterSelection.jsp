<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="parameterSelection.title"/></title>
<meta name="heading" content="<fmt:message key='${heading}'/>"/>
<meta name="menu" content="CourseMenu"/>
</head>

<display:table name="screeningFrequencyList" cellspacing="0" cellpadding="0" requestURI=""
    id="screeningFrequencyList" pagesize="25" class="table screeningFrequencyList" export="false">
    <display:column property="frequency" escapeXml="true" sortable="true" titleKey="screeningFrequency.frequency" group="1"/>
    <display:column property="description" escapeXml="true" sortable="true" titleKey="screeningFrequency.description"/>
    <c:forEach items="${parameters}" var="parameter">
        <display:column titleKey="${parameter.name}">
        <c:forEach items="${screeningFrequencyList.parameterNames}" var="cur" >
            <c:if test="${cur.name == parameter.name}">
                <img src="/images/tick_blue.gif"/>
            </c:if>
        </c:forEach>
    </display:column>
    </c:forEach>

</display:table>

<form:form commandName="parameterSelectionForm" method="post" action="parameterselection.html" id="parameterSelectionForm">
<form:errors path="*" cssClass="error" element="div"/>
<h1>Select parameters to be screened</h1>
<table>
	<tr>
		<td>
            <form:errors path="parameterNames" cssClass="fieldError"/>
            <form:checkboxes path="parameterNames" items="${parameterNames}" itemValue="id" itemLabel="name" />
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr></tr>                    	<tr>
		<td>
            <input type="submit" value="Prev" name="prev" />
		</td>
		<td>
            <input type="submit" value="Next" name="next" />
		</td>
		<td>
             <input type="submit" value="Cancel" name="mycancel" onclick="return confirm('Are you sure you want to cancel?')"/>
		</td>
	</tr>
</table>
</form:form>