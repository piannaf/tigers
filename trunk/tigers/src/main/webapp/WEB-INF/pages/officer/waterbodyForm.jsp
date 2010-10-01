<%@ include file="/common/taglibs.jsp"%>
<head>
	<title><fmt:message key="waterbodyForm.title"/></title>
	<meta name="heading" content="<fmt:message key='waterbody.heading'/>"/>
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
<style type="text/css">
    .hidden { display: none; }
</style>
</head>

<form:form commandName="waterbodyForm" method="post" action="waterbodyform.html" id="waterbodyForm">
<form:errors path="*" cssClass="error" element="div"/>

<table>
	<tr>
		<td>
			<label for="name" class="desc">Name <span class="req">*</span></label>
		</td>
		<td colspan="2">
			<form:errors path="waterbody.name" cssClass="fieldError"/>
			<form:input path="waterbody.name" id="name" cssClass="text medium"/>
		</td>
	</tr>
	<tr>
		<td>
			<label for="type" class="desc">Type <span class="req">*</span></label>
		</td>
		<td>
			<form:errors path="waterbody.type" cssClass="fieldError"/>
            <c:choose>
  				<c:when test="${empty waterbodyForm.waterbody.id}">
  					<form:select path="waterbody.type">
				        <form:option value="" label="Select" />
            	        <form:option value="G" label="Ground"/>
        	            <form:option value="S" label="Surface"/>
                    </form:select>
  				</c:when>
  				<c:otherwise>
                    <form:select path="waterbody.type" disabled="true">
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
			<form:hidden path="waterbody.id"/>
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
            <c:if test="${not empty waterbodyForm.waterbody.id}">
				<input type="submit" class="button" name="delete" onclick="return confirmDelete('waterbody')"
				value="<fmt:message key="button.delete"/>" />
			</c:if>
		</td>
	</tr>
</table>
<c:if test="${not empty waterbodyForm.waterbody.id}">
    <hr />
    <h1>Thresholds</h1>
    <table>
        <thead>
            <tr>
                <th></th>
                <th>Minimum</th>
                <th>Maximum</th>
            </tr>  
        </thead>
        <tbody>
            <c:forEach items="${waterbodyForm.parameterThresholds}" var="threshold" varStatus="loop">
                <tr>
                    <td  class="hidden">
                        <form:input path="parameterThresholds[${loop.index}].id"/>
                    </td>
                    <td><strong><c:out value="${threshold.parameter.name}"></c:out></strong>
                        <form:hidden path="parameterThresholds[${loop.index}].parameter.id"/>
                    </td>
                    <td>
                        <form:input path="parameterThresholds[${loop.index}].min"/>
                    </td>  
                    <td>
                        <form:input path="parameterThresholds[${loop.index}].max"/>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td><input type="submit" name="update" value="<fmt:message key="button.update"/>" /></td>
            </tr>
        </tbody>
    </table>
</c:if>

</form:form>


