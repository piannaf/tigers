<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="sampleMediaList.title"/></title>

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

<display:column style="width: 8em; text-align: center;" title="Controls">
<a href='javascript:void(editSM(<c:out value="${samplerMediaList.id}"/>));'>Edit</a>
<a href='javascript:void(deleteSM(<c:out value="${samplerMediaList.id}"/>));'>Delete</a>
</display:column>
<display:column titleKey="samplerMedia.description">
<span id='sm_description_<c:out value="${samplerMediaList.id}" escapeXml="true"/>'><c:out value="${samplerMediaList.description}" escapeXml="true"/></span>
</display:column>
<display:column titleKey="samplerMedia.fileName">
<a href="/samplermedia/<c:out value="${samplerMediaList.id}" escapeXml="true"/>_<c:out value="${samplerMediaList.fileName}" escapeXml="true"/>"><c:out value="${samplerMediaList.fileName}" escapeXml="true"/></a>
</display:column>

<display:setProperty name="paging.banner.item_name" value="samplermedia"/>
<display:setProperty name="paging.banner.items_name" value="samplermedia"/>
</display:table>

<hr/>
<h2>Add Sampler Media File</h2>
<form enctype="multipart/form-data" method="post" action="samplermediaform.html" onsubmit="if(this.form.file.value)return true;alert('You need to supply a file to upload.');return false;">
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
