<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="samplerMediaList.title"/></title>
<script type="text/javascript" src="<c:url value='/scripts/effects.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/lightwindow.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/styles/lightwindow.css'/>" type="text/css" media="screen" />

</head>
<div id="breadcrumb"><a href="<c:url value='samplermediasamplers.html'/>"><fmt:message key="reportSamplers.title"/></a> &raquo;</div>
<h2>Sampler Media Files for <em><c:out value="${tag}" escapeXml="true"/></em></h2>
<display:table name="samplerMediaList" cellspacing="0" cellpadding="0" requestURI=""
id="samplerMediaList" pagesize="25" class="table sampleList">

<display:column titleKey="samplerMedia.fileName" style="width:40%;">
<a rel="Galleries[samplermedia]" class="lightwindow" caption='<c:out value="${samplerMediaList.description}" escapeXml="true"/>' href="/samplermedia/<c:out value="${samplerMediaList.id}" escapeXml="true"/>_<c:out value="${samplerMediaList.fileName}" escapeXml="true"/>"><c:out value="${samplerMediaList.fileName}" escapeXml="true"/></a>
</display:column>
<display:column titleKey="samplerMedia.description" property="description" escapeXml="true" style="width:60%;"/>

<display:setProperty name="paging.banner.item_name" value="samplermedia"/>
<display:setProperty name="paging.banner.items_name" value="samplermedia"/>
</display:table>

<script type="text/javascript">
<!--
for(var i in document.links) {
	e = document.links[i];
	if(e.className=="lightwindow") {
		p=e.href.lastIndexOf('.');
		if(p)
			ext = e.href.substring(p+1).toLowerCase();
		else
			ext = "";
		if(ext == "flv" || ext == "mp4" || ext == "mp3" || ext == "m4a") {
			e.setAttribute("caption", e.getAttribute("caption") + "<br/><center><a href=\"" + e.href + "\">Download File</a></center>");
			e.setAttribute("params", "lightwindow_width=670,lightwindow_height=570");
			e.setAttribute("rel", ""); // video controls don't work in a gallery
			e.href="/images/player.swf?file=" + e.href + "&autostart=true";
		} else if(ext != "jpg" && ext != "jpeg" && ext != "jpe" && ext != "gif" && ext != "png" && ext != "bmp") {
			// unsupported file, remove lightwindow stuff
			e.setAttribute("class", "");
			e.setAttribute("rel", "");
		}
	}
}
//-->
</script>
