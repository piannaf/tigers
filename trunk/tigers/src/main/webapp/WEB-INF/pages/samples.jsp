<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="sampleList.title"/></title>
<meta name="heading" content="<fmt:message key='sampleList.heading'/>"/>
<meta name="menu" content="CourseMenu"/>
</head>

<c:set var="buttons">
<input type="button" style="margin-right: 5px"
onclick="location.href='<c:url value="/sampleform.html"/>'"
value="<fmt:message key="button.add"/>"/>

<input type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'"
value="<fmt:message key="button.done"/>"/>
</c:set>

<c:out value="${buttons}" escapeXml="false"/>
<display:table name="sampleList" cellspacing="0" cellpadding="0" requestURI=""
id="sampleList" pagesize="25" class="table sampleList" export="true">

<display:column property="id" escapeXml="true" sortable="true"
url="/sampleform.html" paramId="id" paramProperty="id" titleKey="sample.id"/>
<display:column property="date_taken" escapeXml="true" sortable="true" titleKey="sample.date_taken"/>
<display:column property="ph" escapeXml="true" sortable="true" titleKey="sample.ph"/>
<display:column property="ec" escapeXml="true" sortable="true" titleKey="sample.ec"/>
<display:column property="temperature" escapeXml="true" sortable="true" titleKey="sample.temperature"/>
<display:column property="collar_depth" escapeXml="true" sortable="true" titleKey="sample.collar_depth"/>
<display:column property="arsenic" escapeXml="true" sortable="true" titleKey="sample.arsenic"/>
<display:column property="grease" escapeXml="true" sortable="true" titleKey="sample.grease"/>
<display:column property="fluoride" escapeXml="true" sortable="true" titleKey="sample.fluoride"/>
<display:column property="chromium" escapeXml="true" sortable="true" titleKey="sample.chromium"/>
<display:column property="username" escapeXml="true" sortable="true" titleKey="sample.username"/>

<display:column property="tag" escapeXml="true" sortable="true" titleKey="sample.tag"/>

<display:setProperty name="paging.banner.item_name" value="sample"/>
<display:setProperty name="paging.banner.items_name" value="samples"/>

<display:setProperty name="export.excel.filename" value="Course List.xls"/>
<display:setProperty name="export.csv.filename" value="Course List.csv"/>
<display:setProperty name="export.pdf.filename" value="Course List.pdf"/>
</display:table>

<c:out value="${buttons}" escapeXml="false"/>

<script type="text/javascript">
highlightTableRows("sampleList");
</script>

