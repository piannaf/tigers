<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="reportSampler.title"/></title>
<meta name="menu" content="CourseMenu"/>
<link href="/styles/autocomplete.css" media="screen" rel="stylesheet" type="text/css" />

</head>

<h2>Samples Graph for Sampler <em><c:out value="${tag}" escapeXml="true"/></em></h2>

<div>
<c:out value="${parameterThresholds.min}" escapeXml="true"/> / <c:out value="${parameterThresholds.max}" escapeXml="true"/>
</div>

<c:forEach var="sample" items="${data}">
<div>Date: <c:out value="${sample.key}" escapeXml="true"/>, Value: <c:out value="${sample.value}" escapeXml="true"/></div>
</c:forEach>
