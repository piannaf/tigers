<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="screeningProgramConfirm.title"/></title>
<meta name="heading" content="<fmt:message key="screeningProgramConfirm.title"/>"/>
<meta name="menu" content="CourseMenu"/>
</head>

<display:table name="screeningItemList" cellspacing="0" cellpadding="0" requestURI=""
    id="screeningItemList" pagesize="25" class="table screeningItemList" export="false">
    <display:column property="tag" escapeXml="true" sortable="true" titleKey="screeningProgramConfirm.tag" />
    <c:forEach items="${parameters}" var="parameter">
        <display:column titleKey="${parameter.name}">
        <c:forEach items="${screeningItemList.parameterNames}" var="cur" >
            <c:if test="${cur.name == parameter.name}">
                <img src="/images/tick_blue.gif"/>
            </c:if>
        </c:forEach>
    </display:column>
    </c:forEach>

</display:table>

<form:form commandName="screeningProgramConfirmForm" method="post" action="screeningprogramconfirm.html" id="screeningProgramConfirmForm">
<table>
	<tr>
		<td>
            <input type="submit" value="Prev" name="prev" />
		</td>
		<td>
            <input type="submit" value="Confirm" name="confirm" />
		</td>
		<td>
             <input type="submit" value="Cancel" name="mycancel" onclick="return confirm('Are you sure you want to cancel?')"/>
		</td>
	</tr>
</table>
</form:form>
