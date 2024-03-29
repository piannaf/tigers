<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="samplerMediaList.title"/></title>
<script type="text/javascript" src="<c:url value='/scripts/effects.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/lightwindow.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/styles/lightwindow.css'/>" type="text/css" media="screen" />
</head>
<div id="breadcrumb"><a href="<c:url value='samplermediasamplers.html'/>"><fmt:message key="reportSamplers.title"/></a> &raquo;</div>
<h2>Sampler Media Files for <em><c:out value="${tag}" escapeXml="true"/></em></h2>

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

var timeoutHndlr=null;
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
		
		e.parentNode.style.background = "#E0FFE0";
		//if(timeoutHndlr) clearTimeout(timeoutHndlr);
		timeoutHndlr = setTimeout(function(){
			e.parentNode.style.background = "";
			timeoutHndlr = null;
		}, 5000);
		
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

<hr/>
<c:if test="${numSamplerMedia < 10}">
<h2>Add Sampler Media File</h2>
<form enctype="multipart/form-data" method="post" action="samplermediaform.html" onsubmit="return validateUploadForm(this);">
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
<script type="text/javascript">
<!--
function validateUploadForm(f) {
	var v = f.elements['file'].value;
	if(!v) {
		alert('You need to supply a file to upload.');
		return false;
	}
	p=v.lastIndexOf('.');
	if(p)
		ext = v.substring(p+1).toLowerCase();
	else
		ext = "";
	if(ext != "jpg" && ext != "jpe" && ext != "jpeg" && ext != "png" && ext != "bmp" && ext != "gif" && ext != "mp4" && ext != "flv" && ext != "m4a") {
		alert("The selected file type is not allowed.  Only image (JPEG/PNG/GIF/BMP) or audio/video (MP3/MP4/M4A/FLV) files can be uploaded.");
		return false;
	}
	return true;
}
//-->
</script>
</c:if>
<c:if test="${numSamplerMedia >= 10}">
<p>The maximum number of sampler media files allowed per sampler is 10, so no more media files can be added to this sampler.</p>
</c:if>
