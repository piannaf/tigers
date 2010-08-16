<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="reportSamplers.title"/></title>
<meta name="menu" content="CourseMenu"/>
<link href="/styles/autocomplete.css" media="screen" rel="stylesheet" type="text/css" />

</head>

<c:forEach var="waterbody" items="${waterbodies}">
	<div><c:out value="${waterbody.name}" escapeXml="true"/></div>
	<ul>
	<c:forEach var="sampler" items="${samplers[waterbody.name]}">
	<c:url value="reportsamples.html" var="url">
	<c:param name="tag" value="${sampler.tag}"/>
	</c:url>
	<li><a href="<c:out value="${url}" escapeXml="true"/>"><c:out value="${sampler.tag}" escapeXml="true"/></a></li>
	</c:forEach>
	</ul>
</c:forEach>
