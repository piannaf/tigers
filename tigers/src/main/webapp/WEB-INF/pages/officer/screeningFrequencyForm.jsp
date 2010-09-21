<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="screeningFrequency.title"/></title>
<meta name="heading" content="<fmt:message key='${heading}'/>"/>
<meta name="menu" content="CourseMenu"/>
<link rel="stylesheet" type="text/css" href="/styles/autocomplete.css" />
<style type="text/css">
    .hidden { display: none; }
</style>
<script type="text/javascript">

	var previousWindowOnLoadFn = window.onload

	window.onload = function() {
		init();
		previousWindowOnLoadFn();
	}

	function init() {
		document.getElementById("description").focus();
	}

    </script>
</head>

<form:form commandName="screeningFrequency" method="post" action="screeningfrequencyform.html" id="screeningFrequencyForm">
<form:errors path="*" cssClass="error" element="div"/>

<table>
	<tr>
		<td>
			<appfuse:label styleClass="desc" key="screeningFrequency.description"/>
		</td>
		<td colspan="2">
			<form:errors path="description" cssClass="fieldError"/>
			<form:input path="description" id="description" cssClass="text medium"/>
		</td>
	</tr>
	<tr>
		<td>
			<appfuse:label styleClass="desc" key="screeningFrequency.frequency"/>
		</td>
		<td>
			<form:errors path="frequency" cssClass="fieldError"/>
            <form:select path="frequency">
                <form:option value="" label="Select" />
            	<form:option value="daily" />
                <form:option value="weekly" />
               	<form:option value="fortnightly" />
               	<form:option value="monthly" />
                <form:option value="half yearly" />
               	<form:option value="yearly" />
            </form:select>
	    </td>
	    <td>
            <form:hidden path="id"/>
	    </td>
	</tr>
    <tr></tr>
	<tr>
		<td>
            <form:errors path="parameterNames" cssClass="fieldError"/>
            <appfuse:label styleClass="desc" key="screeningFrequency.parameters"/>
		</td>
		<td>
            <form:checkboxes path="parameterNames" items="${parameterNames}" itemValue="id" itemLabel="name" />
		</td>
        <td>
	    </td>
	</tr>
	<tr></tr>
	<tr>
		<td>
			<input type="submit" class="button" name="save" value="<fmt:message key="button.save"/>"/>
		</td>
		<td>
			<input type="button" onclick="location.href='<c:url value="/officer/screeningfrequencies.html"><c:param name="id" value="${screeningFrequency.sampler.id}"/></c:url >'"
			  value="<fmt:message key="button.cancel"/>"/>
		</td>
		<td>
            <c:if test="${not empty screeningFrequency.id}">
				<input type="submit" class="button" name="delete" onclick="return confirmDelete('screening frequency')"
				value="<fmt:message key="button.delete"/>" />
			</c:if>
		</td>
	</tr>
</table>

</form:form>