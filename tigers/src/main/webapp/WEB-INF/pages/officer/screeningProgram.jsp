<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="screeningProgram.title"/></title>
<meta name="heading" content="<fmt:message key="screeningProgram.title"/>"/>
<meta name="menu" content="CourseMenu"/>
<link rel="stylesheet" type="text/css" href="/styles/autocomplete.css" />

<script type="text/javascript">
    	window.onload = function() {
    		document.getElementById("waterbody").focus();
    		new Ajax.Autocompleter("waterbody", "autocomplete_choices", "/waterbodies", {});
    	}
</script>
</head>

<form:form commandName="screeningProgramForm" method="post" action="/officer/screeningprogram.html" id="screeningProgramForm">
<form:errors path="*" cssClass="error" element="div"/>

<appfuse:label styleClass="desc" key="screeningProgram.waterbody"/>
<form:errors path="waterbody" cssClass="fieldError"/>
<form:input path="waterbody" id="waterbody" cssClass="text medium"/>
<div id="autocomplete_choices" class="autocomplete"></div>
<input type="submit" class="button" name="search" value="<fmt:message key="button.search"/>"/>
<br />
<c:if test="${not empty firstTime}">
    <input type="submit" value="Next" name="next" />
    <input type="submit" value="Cancel" name="cancel" />
</c:if>
</form:form>
