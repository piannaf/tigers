<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="reportExceedance.title"/></title>
<meta name="menu" content="CourseMenu"/>
<link href="/styles/autocomplete.css" media="screen" rel="stylesheet" type="text/css" />

</head>
<div id="breadcrumb"><a href="<c:url value='reportexceedancesamplers.html'/>"><fmt:message key="reportExceedance.title"/></a> &raquo;</div>
<h2>Exceedance Report for Sampler <em><c:out value="${tag}" escapeXml="true"/></em></h2>

<c:if test="${not empty thresholds}">
<h3>Exceedance Thresholds Defined</h3>
<display:table name="thresholds" cellspacing="0" cellpadding="0" requestURI=""
    id="thresholds" class="table sampleList" export="true">

    <display:column property="parameter.name" escapeXml="true" sortable="true"
        url="waterbodyform.html" paramId="id" paramProperty="waterbody.id" titleKey="threshold.name"/>
    <display:column property="min" escapeXml="true" sortable="true" titleKey="threshold.min"/>
    <display:column property="max" escapeXml="true" sortable="true" titleKey="threshold.max"/>
</display:table>


<h3>Recorded Exceedance Samples</h3>
<c:if test="${not empty data}">
<display:table name="data" cellspacing="0" cellpadding="0" requestURI=""
    id="data" pagesize="25" class="table sampleList" export="true">

    <display:column property="sampleTaken" escapeXml="true" sortable="true"
        paramId="id" paramProperty="sampleId" titleKey="sample.date_taken"/>
    <display:column property="paramName" escapeXml="true" sortable="true" titleKey="exceedance.paramName"/>
    <display:column property="value" escapeXml="true" sortable="true" titleKey="exceedance.value"/>
    <display:column property="magnitude" escapeXml="true" sortable="true" titleKey="exceedance.magnitude"/>

    <display:setProperty name="paging.banner.item_name" value="sample"/>
    <display:setProperty name="paging.banner.items_name" value="sample"/>

    <display:setProperty name="export.excel.filename" value="Sample List.xls"/>
    <display:setProperty name="export.csv.filename" value="Sample List.csv"/>
    <display:setProperty name="export.pdf.filename" value="Sample List.pdf"/>
</display:table>
</c:if>
<c:if test="${empty data}">
No exceedance samples found.
</c:if>
</c:if>
<c:if test="${empty thresholds}">
No exceedance thresholds have been defined.
</c:if>