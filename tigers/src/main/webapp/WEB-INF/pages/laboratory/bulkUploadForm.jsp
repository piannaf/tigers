<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="bulkUpload.title"/></title>
    <meta name="heading" content="<fmt:message key='bulkUpload.heading'/>"/>
    <meta name="menu" content="LaboratoryMenu"/>
</head>

<!--
    The most important part is to declare your form's enctype to be "multipart/form-data"
-->

<spring:bind path="fileUpload.*">
    <c:if test="${not empty status.errorMessages}">
    <div class="error">    
        <c:forEach var="error" items="${status.errorMessages}">
            <img src="<c:url value="/images/iconWarning.gif"/>"
                alt="<fmt:message key="icon.warning"/>" class="icon" />
            <c:out value="${error}" escapeXml="false"/><br />
        </c:forEach>
    </div>
    </c:if>
</spring:bind>

<div class="separator"></div>

<form:form commandName="fileUpload" method="post" action="/laboratory/bulkupload.html" enctype="multipart/form-data"
    onsubmit="return validateFileUpload(this)" id="bulkUploadForm">
<ul>
    <li class="info">
        <fmt:message key="bulkUpload.message"/>
    </li>
    <li>
        <appfuse:label key="bulkUploadForm.name" styleClass="desc"/>
        <form:errors path="name" cssClass="fieldError"/>
        <!--form:input path="name" id="name" cssClass="text medium" cssErrorClass="text state error"/-->
        <form:select path="name" id="name" cssClass="text medium">
        	<c:forEach items="${samplerIdList}" var="samplerId">
        		<option value="<c:out value="${samplerId}"/>"><c:out value="${samplerId}"/></option>
        	</c:forEach>
        </form:select>
    </li>
    <li>
        <appfuse:label key="bulkUploadForm.file" styleClass="desc"/>
        <form:errors path="file" cssClass="fieldError"/>
        <input type="file" name="file" id="file" class="file medium"/>
    </li>
    <li class="buttonBar bottom">
        <input type="submit" name="upload" class="button" onclick="bCancel=false"
            value="<fmt:message key="button.upload"/>" />
        <input type="submit" name="cancel" class="button" onclick="bCancel=true"
            value="<fmt:message key="button.cancel"/>" />
    </li>
</ul>
</form:form>

<script type="text/javascript">
    Form.focusFirstElement($('bulkUploadForm'));
    highlightFormElements();
</script>
<v:javascript formName="fileUpload" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
