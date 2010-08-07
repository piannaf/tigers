<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="mainMenu.title"/></title>
    <meta name="heading" content="<fmt:message key='mainMenu.heading'/>"/>
    <meta name="menu" content="MainMenu"/>
</head>

<p><fmt:message key="mainMenu.message"/></p>

<div class="separator"></div>

<ul class="glassList">
    <li>
        <a href="<c:url value='/samples.html'/>"><fmt:message key="menu.main.searchSamples"/></a>
    </li>
    <li>
        <a href="<c:url value='/sampleform.html'/>"><fmt:message key="menu.main.addSample"/></a>
    </li>
    <li>
    	<a href="<c:url value='/officer/samplesearch.html'/>"><fmt:message key="menu.main.sampleSearch"/></a>
    </li>
    <li>
    	<a href="<c:url value='emailexample.html'/>"><fmt:message key="menu.main.emailExample"/></a>
    </li>
<!-- 
    <li>
        <a href="<c:url value='/userform.html'/>"><fmt:message key="menu.user"/></a>
    </li>
    <li>
        <a href="<c:url value='/fileupload.html'/>"><fmt:message key="menu.selectFile"/></a>
    </li>
-->
</ul>
