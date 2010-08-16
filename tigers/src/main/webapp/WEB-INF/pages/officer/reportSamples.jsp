<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="reportSampler.title"/></title>
<meta name="menu" content="CourseMenu"/>
<link href="/styles/autocomplete.css" media="screen" rel="stylesheet" type="text/css" />

</head>

<h2>Samples Report for Sampler <em><c:out value="${tag}" escapeXml="true"/></em></h2>

<display:table name="sampleList" cellspacing="0" cellpadding="0" requestURI=""
	id="sampleList" pagesize="25" class="table sampleList" export="true">

	<display:column property="id" escapeXml="true" sortable="true"
		url="../sampleform.html" paramId="id" paramProperty="id" titleKey="sample.id"/>
	<display:column property="tag" escapeXml="true" sortable="true" titleKey="sample.tag"/>
	<display:column property="ph" escapeXml="true" sortable="true" titleKey="sample.ph"/>

	<display:setProperty name="paging.banner.item_name" value="sample"/>
	<display:setProperty name="paging.banner.items_name" value="sample"/>

	<display:setProperty name="export.excel.filename" value="Sample List.xls"/>
	<display:setProperty name="export.csv.filename" value="Sample List.csv"/>
	<display:setProperty name="export.pdf.filename" value="Sample List.pdf"/>
</display:table>

<c:if test="${not empty sampleList}">
<hr/>
<h2>Generate Graph</h2>
<div>Select the parameters for which you want the graph to contain.  You can select multiple parameters by holding down the Ctrl key.</div>

<form action="<c:url value='reportgraphsamples.html'/>" method="get">
<div style="text-align: center; vertical-align: top; width: 100%;">
<input type="hidden" name="tag" value="<c:out value='${tag}' escapeXml='true'/>"/>
<select name="display_parameter"> <!--  multiple="multiple" size="8" cols="20" -->
<c:forEach var="parameterNames" items="${params}">
<option value="<c:out value='${parameterNames.parameter_id}' escapeXml='true'/>" selected="selected"><c:out value="${parameterNames.name}" escapeXml="true"/></option>
</c:forEach>
</select>
&nbsp;<input type="submit" value="Generate Graph"/>
</div>
</form>
</c:if>
