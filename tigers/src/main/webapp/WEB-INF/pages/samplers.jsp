<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="samplerList.title"/></title>
<meta name="heading" content="<fmt:message key='samplerList.heading'/>"/>
<meta name="menu" content="CourseMenu"/>
<link rel="stylesheet" type="text/css" href="/styles/autocomplete.css" />
<script type="text/javascript">
    	window.onload = function() {    	
    		document.getElementById("waterbody").focus(); 
    		new Ajax.Autocompleter("waterbody", "autocomplete_choices", "/waterbodies", {});
    	}
</script>
</head>

<c:set var="buttons">
<input type="button" style="margin-right: 5px" 
onclick="location.href='<c:url value="/officer/samplerform.html"/>'"
value="<fmt:message key="button.add"/>"/>

<input type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'"
value="<fmt:message key="button.done"/>"/>
</c:set>
<hr />
<form method="get" action="/samplers.html">

<label for="waterbody">Water Body:</label>
<input type="text" id="waterbody" name="waterbody" />
<div id="autocomplete_choices" class="autocomplete"></div>
<input type="submit" id="submit"  value="Search" />
</form>

<hr />

<c:out value="${buttons}" escapeXml="false"/>
<display:table name="samplerList" cellspacing="0" cellpadding="0" requestURI=""
id="samplerList" pagesize="25" class="table samplerList" export="false">
<display:column property="tag" escapeXml="true" sortable="true" title="Update Sampler"
url="/officer/samplerform.html" paramId="tag" paramProperty="tag" titleKey="sampler.tag"/>
<display:column property="purpose" escapeXml="true" sortable="true" titleKey="sampler.purpose"/>
<display:column property="waterbody.name" escapeXml="true" sortable="true" titleKey="sampler.waterbody"/>
<display:column property="latitude" escapeXml="true" sortable="true" titleKey="sampler.latitude"/>
<display:column property="longitude" escapeXml="true" sortable="true" titleKey="sampler.longitude"/>
<display:column property="comp_screening_freq" escapeXml="true" sortable="true" titleKey="sampler.comp_screening_freq"/>
<display:column property="contractor.companyName" escapeXml="true" sortable="true" titleKey="sampler.contractor"/>
<display:column property="laboratory.companyName" escapeXml="true" sortable="true" titleKey="sampler.laboratory"/>
<display:column property="license" escapeXml="true" sortable="true" titleKey="sampler.license"/>
<display:column property="collar_height" escapeXml="true" sortable="true" titleKey="sampler.collar_height"/>
<display:column property="depth_to_collar_screening_freq" escapeXml="true" sortable="true" titleKey="sampler.depth_to_collar_screening_freq"/>

<display:setProperty name="paging.banner.item_name" value="sampler"/>
<display:setProperty name="paging.banner.items_name" value="samplers"/>

</display:table>

<c:out value="${buttons}" escapeXml="false"/>



