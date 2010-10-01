<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="samplingSchedule.title"/></title>
<meta name="heading" content="<fmt:message key="samplingSchedule.title"/>"/>
<meta name="menu" content="CourseMenu"/>
<link rel="stylesheet" type="text/css" href="/styles/autocomplete.css" />

<script type="text/javascript">
    	window.onload = function() {
    		document.getElementById("waterbody").focus();
    		new Ajax.Autocompleter("waterbody", "autocomplete_choices", "/waterbodies", {});
    	}
</script>
</head>

<form:form commandName="samplingSchedule" method="post" action="/officer/samplingschedule.html" id="samplingSchedule">
<form:errors path="*" cssClass="error" element="div"/>

<appfuse:label styleClass="desc" key="samplingSchedule.waterbody"/>
<form:errors path="waterbody" cssClass="fieldError"/>
<form:input path="waterbody" id="waterbody" cssClass="text medium"/>
<div id="autocomplete_choices" class="autocomplete"></div>
<input type="submit" class="button" name="search" value="<fmt:message key="button.search"/>"/>
<br />
<br />
<form:radiobuttons path="groupBy" items="${groupByList}"  />

</form:form>

<c:if test="${not empty firstTime}">
    <display:table name="screeningFrequencyList" cellspacing="0" cellpadding="0" requestURI=""
    id="screeningFrequencyList" pagesize="25" class="table screeningFrequencyList" export="false">
    <c:choose>
    <c:when test="${samplingSchedule.groupBy == 'Sampler'}">
        <display:column property="sampler.tag" escapeXml="true" sortable="true" titleKey="samplingSchedule.sampler" group="1"/>
        <display:column property="frequency" escapeXml="true" sortable="true" titleKey="screeningFrequency.frequency" group="2"/>
    </c:when>
    <c:otherwise>
        <display:column property="frequency" escapeXml="true" sortable="true" titleKey="screeningFrequency.frequency" group="1"/>
        <display:column property="sampler.tag" escapeXml="true" sortable="true" titleKey="samplingSchedule.sampler" group="2"/>
    </c:otherwise>
    </c:choose>
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

    <display:setProperty name="paging.banner.item_name" value="screening frquency"/>
    <display:setProperty name="paging.banner.items_name" value="screening frequencies"/>

    </display:table>
</c:if>