<%@ include file="/common/taglibs.jsp"%>
<head>
	<title><fmt:message key="samplerForm.title"/></title>
	<meta name="heading" content="<fmt:message key='waterbodyList.heading'/>"/>
	<script type="text/javascript">

	var previousWindowOnLoadFn = window.onload

	window.onload = function() {
		init();
		previousWindowOnLoadFn();
	}

	function init() {
		document.getElementById("name").focus();
	}

</script>
</head>

<form:form commandName="waterbody" method="post" action="waterbodyform.html" id="waterbodyForm">
<form:errors path="*" cssClass="error" element="div"/>

<table>
	<tr>
		<td>
			<appfuse:label styleClass="desc" key="waterbody.name"/>
		</td>
		<td colspan="2">
			<form:errors path="name" cssClass="fieldError"/>
			<form:input path="name" id="name" cssClass="text medium"/>
		</td>
	</tr>
	<tr>
		<td>
			<label for="type" class="desc">Type <span class="req">*</span></label>
		</td>
		<td>
			<form:errors path="type" cssClass="fieldError"/>
            <c:choose>
  				<c:when test="${empty waterbody.id}">
  					<form:select path="type">
				        <form:option value="" label="Select" />
            	        <form:option value="G" label="Ground"/>
        	            <form:option value="S" label="Surface"/>
                    </form:select>
  				</c:when>
  				<c:otherwise>
                    <form:select path="type" disabled="true">
				        <form:option value="" label="Select" />
            	        <form:option value="G" label="Ground"/>
        	            <form:option value="S" label="Surface"/>
                    </form:select>
  				</c:otherwise>
 			</c:choose>
	    </td>
	    <td>
	    </td>
	</tr>
    <tr></tr>
	<tr>
		<td>
			<form:hidden path="id"/>
		</td>
		<td>
		</td>
        <td>
	    </td>
	</tr>
	<tr>
		<td>
			<input type="submit" class="button" name="save" value="<fmt:message key="button.save"/>"/>
		</td>
		<td>
			<input type="submit" class="button" name="cancel" value="<fmt:message key="button.cancel"/>"/>
		</td>
		<td>
            <c:if test="${not empty waterbody.id}">
				<input type="submit" class="button" name="delete" onclick="return confirmDelete('waterbody')"
				value="<fmt:message key="button.delete"/>" />
			</c:if>
		</td>
	</tr>
</table>

</form:form>


