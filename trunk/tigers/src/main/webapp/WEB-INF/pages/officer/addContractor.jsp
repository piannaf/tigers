<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="user.add.contractor.title"/></title>
    <meta name="heading" content="<fmt:message key='user.add.contractor.heading'/>"/>
    <meta name="menu" content="OfficerAdministrationMenu"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
</head>

<spring:bind path="user.*">
    <c:if test="${not empty status.errorMessages}">
    <div class="error">
        <c:forEach var="error" items="${status.errorMessages}">
            <img src="<c:url value="/images/iconWarning.gif"/>"
                alt="<fmt:message key="icon.warning"/>" class="icon"/>
            <c:out value="${error}" escapeXml="false"/><br />
        </c:forEach>
    </div>
    </c:if>
</spring:bind>

<form:form commandName="user" method="post" action="/officer/addcontractor.html"  id="addContractor">
<form:hidden path="id"/>
<form:hidden path="version"/>
<form:hidden path="enabled"/>
<form:hidden path="accountExpired"/>
<form:hidden path="accountLocked"/>
<form:hidden path="credentialsExpired"/>


<form:hidden path="password"/>
<form:hidden path="confirmPassword"/>


<c:if test="${empty user.version}">
    <input type="hidden" name="encryptPass" value="true"/>
</c:if>

<ul>
    <li class="buttonBar right">
        <%-- So the buttons can be used at the bottom of the form --%>
        <c:set var="buttons">
            <input type="submit" class="button" name="save" onclick="bCancel=false" value="<fmt:message key="button.save"/>"/>
            <input type="submit" class="button" name="cancel" onclick="bCancel=true" value="<fmt:message key="button.cancel"/>"/>
        </c:set>
        <c:out value="${buttons}" escapeXml="false"/>
    </li>
    <li class="info">
        <p><fmt:message key="userProfile.officer.message"/></p>
    </li>
    <li>
        <appfuse:label styleClass="desc" key="user.username"/>
        <form:errors path="username" cssClass="fieldError"/>
        <form:input path="username" id="username" cssClass="text large" cssErrorClass="text large error"/>
    </li>
    <li>
        <appfuse:label styleClass="desc" key="user.companyName"/>
        <form:errors path="companyName" cssClass="fieldError"/>
        <form:input path="companyName" id="companyName" cssClass="text large" cssErrorClass="text medium error" maxlength="50"/>       
    </li>
    <li>
        <div>
            <div class="left">
                <appfuse:label styleClass="desc" key="user.email"/>
                <form:errors path="email" cssClass="fieldError"/>
                <form:input path="email" id="email" cssClass="text medium" cssErrorClass="text medium error"/>
            </div>
            <div>
                <appfuse:label styleClass="desc" key="user.phoneNumber"/>
                <form:errors path="phoneNumber" cssClass="fieldError"/>
                <form:input path="phoneNumber" id="phoneNumber" cssClass="text medium" cssErrorClass="text medium error"/>
            </div>
        </div>
    </li>
    <li>
        <label class="desc"><fmt:message key="user.address.address"/></label>
        <div class="group">
            <div>
				<p><appfuse:label key="user.address.address"/></p>
                <form:errors path="address.address" cssClass="fieldError"/>
                <form:input path="address.address" id="address.address" cssClass="text large" cssErrorClass="text large error"/>                
            </div>
            <div class="left">
                <p><appfuse:label key="user.address.city"/></p>
                <form:errors path="address.city" cssClass="fieldError"/>
                <form:input path="address.city" id="address.city" cssClass="text medium" cssErrorClass="text medium error"/>
            </div>
            <div>
                <p><appfuse:label key="user.address.province"/></p>
                <form:errors path="address.province" cssClass="fieldError"/>
                <form:input path="address.province" id="address.province" cssClass="text state" cssErrorClass="text state error"/>
            </div>
            <div class="left">
                <p><appfuse:label key="user.address.postalCode"/></p>
                <form:errors path="address.postalCode" cssClass="fieldError"/>
                <form:input path="address.postalCode" id="address.postalCode" cssClass="text medium" cssErrorClass="text medium error"/>
            </div>
            <div>
                <p><appfuse:label key="user.address.country"/></p>
                <appfuse:country name="address.country" prompt="" default="${user.address.country}"/>
            </div>
        </div>
    </li>
    

    <li class="buttonBar bottom">
        <c:out value="${buttons}" escapeXml="false"/>
    </li>
</ul>
</form:form>

<script type="text/javascript">
    Form.focusFirstElement($('addContractor'));
    highlightFormElements();

    function passwordChanged(passwordField) {
        if (passwordField.id == "password") {
            var origPassword = "${user.password}";
        } else if (passwordField.id == "confirmPassword") {
            var origPassword = "${user.confirmPassword}";
        }

        if (passwordField.value != origPassword) {
            createFormElement("input", "hidden",  "encryptPass", "encryptPass",
                              "true", passwordField.form);
        }
    }

<!-- This is here so we can exclude the selectAll call when roles is hidden -->
function onFormSubmit(theForm) {
<c:if test="${param.from == 'list'}">
    selectAll('userRoles');
</c:if>
    return validateUser(theForm);
}
</script>

<v:javascript formName="user" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>

