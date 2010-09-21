<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="screeningFrequency.title"/></title>
<meta name="heading" content="<fmt:message key='${heading}'/>"/>
<meta name="menu" content="CourseMenu"/>
<link rel="stylesheet" type="text/css" href="/styles/autocomplete.css" />
<style type="text/css">
    .hidden { display: none; }
</style>

</head>

<c:set var="buttons">
<input type="button" style="margin-right: 5px"
onclick="location.href='<c:url value="/officer/screeningfrequencyform.html?samplerid=${samplerid}"/>'"
value="<fmt:message key="button.add"/>"/>

<input type="button" onclick="location.href='<c:url value="/samplers.html"/>'"
value="<fmt:message key="button.done"/>"/>
</c:set>
<hr />

<br />

<c:out value="${buttons}" escapeXml="false"/>
<display:table name="screeningFrequencyList" cellspacing="0" cellpadding="0" requestURI=""
id="screeningFrequencyList" pagesize="25" class="table screeningFrequencyList" export="false">
<display:column sortable="true" titleKey="screeningFrequency.description">
    <a href="<c:url value="/officer/screeningfrequencyform.html?samplerid=${screeningFrequencyList.sampler.id}&frequencyid=${screeningFrequencyList.id}"/>">${screeningFrequencyList.description}</a>
</display:column>

<display:column property="frequency" escapeXml="true" sortable="true" titleKey="screeningFrequency.frequency"/>
<c:forEach items="${parameters}" var="parameter">
<display:column titleKey="${parameter.name}">
    <c:forEach items="${screeningFrequencyList.parameterNames}" var="cur" >
        <c:if test="${cur.name == parameter.name}">
	        <img src="/images/tick_blue.gif"/>
	    </c:if>
    </c:forEach>
</display:column>
</c:forEach>

<display:setProperty name="paging.banner.item_name" value="screening frquency"/>
<display:setProperty name="paging.banner.items_name" value="screening frequencies"/>

</display:table>

<c:out value="${buttons}" escapeXml="false"/>



</html>