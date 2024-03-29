<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="reportSamplers.title"/></title>
<meta name="menu" content="CourseMenu"/>
<link href="/styles/autocomplete.css" media="screen" rel="stylesheet" type="text/css" />

</head>
<div id="breadcrumb"><a href="<c:url value='reportsamplers.html'/>"><fmt:message key="reportSamplers.title"/></a> &raquo;</div>
<h2>Samples Report for Sampler <em><c:out value="${tag}" escapeXml="true"/></em></h2>

<display:table name="sampleList" cellspacing="0" cellpadding="0" requestURI=""
	id="sampleList" pagesize="25" class="table sampleList" export="true">

	<display:column property="date_taken" escapeXml="true" sortable="true"
		paramId="id" paramProperty="id" titleKey="sample.date_taken"/>
	<display:column property="phString" escapeXml="true" sortable="true" titleKey="sample.ph"/>
	<display:column property="ecString" escapeXml="true" sortable="true" titleKey="sample.ec"/>
	<display:column property="arsenicString" escapeXml="true" sortable="true" titleKey="sample.arsenic"/>
	<display:column property="chromiumString" escapeXml="true" sortable="true" titleKey="sample.chromium"/>
	<display:column property="collar_depthString" escapeXml="true" sortable="true" titleKey="sample.collar_depth"/>
	<display:column property="fluorideString" escapeXml="true" sortable="true" titleKey="sample.fluoride"/>
	<display:column property="greaseString" escapeXml="true" sortable="true" titleKey="sample.grease"/>
	<display:column property="temperatureString" escapeXml="true" sortable="true" titleKey="sample.temperature"/>

	<display:setProperty name="paging.banner.item_name" value="sample"/>
	<display:setProperty name="paging.banner.items_name" value="sample"/>

	<display:setProperty name="export.excel.filename" value="Sample List.xls"/>
	<display:setProperty name="export.csv.filename" value="Sample List.csv"/>
	<display:setProperty name="export.pdf.filename" value="Sample List.pdf"/>
</display:table>

<c:if test="${not empty sampleList}">
<hr/>
<h2>Generate Graph</h2>
<div>Select the parameter for which you want a graph to be generated for.</div>
<!-- Select the parameters for which you want the graph to contain.  You can select multiple parameters by holding down the Ctrl key. -->

<form action="<c:url value='reportgraphsamples.html'/>" method="get">
<div style="text-align: center; vertical-align: top; width: 100%;">
<input type="hidden" name="tag" value="<c:out value='${tag}' escapeXml='true'/>"/>
<select name="display_parameter"> <!--  multiple="multiple" size="8" cols="20" -->
<c:forEach var="parameterNames" items="${params}">
<option value="<c:out value='${parameterNames.id}' escapeXml='true'/>"><c:out value="${parameterNames.name}" escapeXml="true"/></option>
</c:forEach>
</select>
&nbsp;<input type="submit" value="Generate Graph"/>
</div>
</form>
</c:if>
