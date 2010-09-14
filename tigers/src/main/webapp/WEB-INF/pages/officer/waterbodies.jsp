<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="waterbodyList.title"/></title>
<meta name="heading" content="<fmt:message key='waterbodyList.heading'/>"/>
<meta name="menu" content="CourseMenu"/>
<link rel="stylesheet" type="text/css" href="/styles/autocomplete.css" />
<style type="text/css">
    .hidden { display: none; }
</style>
<script type="text/javascript">
    	window.onload = function() {
    		document.getElementById("waterbody").focus();
    		new Ajax.Autocompleter("waterbody", "autocomplete_choices", "/waterbodies", {});
    	}
</script>
</head>

<c:set var="buttons">
<input type="button" style="margin-right: 5px"
onclick="location.href='<c:url value="/officer/waterbodyform.html"/>'"
value="<fmt:message key="button.add"/>"/>

<input type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'"
value="<fmt:message key="button.done"/>"/>
</c:set>
<hr />
<form method="get" action="/officer/waterbodies.html">

<label for="waterbody">Water Body:</label>
<input type="text" id="waterbody" name="waterbody" />
<div id="autocomplete_choices" class="autocomplete"></div>
<input type="submit" id="submit"  value="Search" />
</form>

<hr />
<br />

<c:out value="${buttons}" escapeXml="false"/>
<display:table name="waterbodyList" cellspacing="0" cellpadding="0" requestURI=""
id="waterbodyList" pagesize="25" class="table waterbodyList" export="false">
<display:column property="name" escapeXml="true" sortable="true" 
url="/officer/waterbodyform.html" paramId="id" paramProperty="id" titleKey="waterbody.name"/>
<display:column escapeXml="true" sortable="true" titleKey="waterbody.type">
    <c:choose>
        <c:when test="${waterbodyList.type == 'G'}">
            Ground
        </c:when>
        <c:otherwise>
            Surface
        </c:otherwise>
    </c:choose></display:column>
<display:column property="id" class="hidden" headerClass="hidden"/>

<display:setProperty name="paging.banner.item_name" value="waterbody"/>
<display:setProperty name="paging.banner.items_name" value="waterbodies"/>

</display:table>

<c:out value="${buttons}" escapeXml="false"/>



