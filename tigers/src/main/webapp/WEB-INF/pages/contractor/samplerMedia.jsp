<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="samplerMediaList.title"/></title>
<script type="text/javascript" src="<c:url value='/scripts/effects.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/lightwindow.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/styles/lightwindow.css'/>" type="text/css" media="screen" />
</head>

<c:set var="buttons">
<input type="button" style="margin-right: 5px"
onclick="location.href='<c:url value="/samplermediaform.html"/>'"
value="<fmt:message key="button.add"/>"/>
</c:set>
<hr />
<hr />

<script type="text/javascript">
<!--
function unHtmlSpecialChar(s) {
	return s.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&");
}
function htmlSpecialChar(s) {
	return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
}

function editSM(id) {
	var e = $('sm_description_' + id);
	if((newValue = prompt("Enter in a new caption for the sampler media file.", unHtmlSpecialChar(e.innerHTML))) != null) {
		e.innerHTML = htmlSpecialChar(newValue);
		try {
			var p = e.parentNode.parentNode.childNode[0].childNode[0];
			if(p.className=="lightwindow")
				p.setAttribute("caption", htmlSpecialChar(newValue));
				//no-one cares about the loss of the download link >_>
		} catch(err) {}
		
		// send this off and forget about it :P
		new Ajax.Request('samplermediaform.html', {method: "post", parameters: {
			id: id,
			description: newValue
		}});
	}
	return 0;
}

function deleteSM(id) {
	if(!confirm("Are you sure you wish to delete this sampler media file?")) return 0;
	
	$("deleteform").elements['id'].value = id;
	$("deleteform").submit();
	
	return 0;
}

//-->
</script>

<!-- hidden form to handle JS deletion -->
<form style="display: none;" action="samplermediaform.html" method="post" id="deleteform">
	<input type="hidden" name="delete" value="1"/>
	<input type="hidden" name="id" value=""/>
</form>




<display:table name="samplerMediaList" cellspacing="0" cellpadding="0" requestURI=""
id="samplerMediaList" pagesize="25" class="table sampleList">

<display:column style="width: 40%;" titleKey="samplerMedia.fileName">
<a rel="Galleries[samplermedia]" class="lightwindow" caption='<c:out value="${samplerMediaList.description}" escapeXml="true"/>' href="/samplermedia/<c:out value="${samplerMediaList.id}" escapeXml="true"/>_<c:out value="${samplerMediaList.fileName}" escapeXml="true"/>"><c:out value="${samplerMediaList.fileName}" escapeXml="true"/></a>
</display:column>
<display:column titleKey="samplerMedia.description">
<div id='sm_description_<c:out value="${samplerMediaList.id}" escapeXml="true"/>' ondblclick='editSM(<c:out value="${samplerMediaList.id}"/>);'><c:out value="${samplerMediaList.description}" escapeXml="true"/></div>
</display:column>
<display:column style="width: 8em; text-align: center;" title="Controls">
<a href='javascript:void(editSM(<c:out value="${samplerMediaList.id}"/>));'>Edit</a>
&nbsp;<a href='javascript:void(deleteSM(<c:out value="${samplerMediaList.id}"/>));'>Delete</a>
</display:column>

<display:setProperty name="paging.banner.item_name" value="samplermedia"/>
<display:setProperty name="paging.banner.items_name" value="samplermedia"/>
</display:table>

<script type="text/javascript">
<!--
for(var i in document.links) {
	e = document.links[i];
	if(e.className=="lightwindow") {
		p=e.href.lastIndexOf('.');
		if(!p) continue;
		ext = e.href.substring(p+1).toLowerCase();
		if(ext == "flv" || ext == "mp4" || ext == "mp3" || ext == "m4a") {
			e.setAttribute("caption", e.getAttribute("caption") + "<br/><center><a href=\"" + e.href + "\">Download File</a></center>");
			e.setAttribute("params", "lightwindow_width=670,lightwindow_height=570");
			e.href="/images/player.swf?file=" + e.href + "&autostart=true";
		}
	}
}
//-->
</script>

<hr/>
<c:if test="${numSamplerMedia < 10}">
<h2>Add Sampler Media File</h2>
<form enctype="multipart/form-data" method="post" action="samplermediaform.html" onsubmit="if(this.elements['file'].value)return true;alert('You need to supply a file to upload.');return false;">
<input type="hidden" name="tag" value="${tag}"/>
<table>
	<tr>
		<td><label for="file" class="desc">File <span class="req">*</span></label></td>
		<td><input type="file" name="file" id="file" class="text large"/></td>
	</tr>
	<tr>
		<td><label for="description" class="desc">Caption</label></td>
		<td><input type="text" name="description" id="description" class="text large"/></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="submit" value="Add File" class="button"/>
		</td>
	</tr>
</table>
</form>
</c:if>
<c:if test="${numSamplerMedia >= 10}">
<p>The maximum number of sampler media files allowed per sampler is 10, so no more media files can be added to this sampler.</p>
</c:if>
