<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userProfile.title"/></title>
    <meta name="heading" content="<fmt:message key='userProfile.heading'/>"/>
    <meta name="menu" content="AdminMenu"/>
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

<form:form commandName="user" method="post" action="userform.html" onsubmit="return onFormSubmit(this)" id="userForm">
<form:hidden path="id"/>
<form:hidden path="version"/>
<input type="hidden" name="from" value="<c:out value="${param.from}"/>"/>

<c:if test="${cookieLogin == 'true' || userself == 'false'}">
    <form:hidden path="password"/>
    <form:hidden path="confirmPassword"/>
</c:if>

<c:if test="${empty user.version}">
    <input type="hidden" name="encryptPass" value="true"/>
</c:if>

<ul>
    <li class="buttonBar right">
        <%-- So the buttons can be used at the bottom of the form --%>
        <c:set var="buttons">
            <input type="submit" class="button" name="save" onclick="bCancel=false" value="<fmt:message key="button.save"/>"/>

        <c:if test="${param.from == 'list' and param.method != 'Add'}">
            <input type="submit" class="button" name="delete" onclick="bCancel=true;return confirmDelete('user')"
                value="<fmt:message key="button.delete"/>"/>
            <input type="submit" class="button" name="reset" onclick="bCancel=true;return confirmReset('user')"
                value="<fmt:message key="button.reset"/>"/>
        </c:if>

            <input type="submit" class="button" name="cancel" onclick="bCancel=true" value="<fmt:message key="button.cancel"/>"/>
        </c:set>
        <c:out value="${buttons}" escapeXml="false"/>
    </li>
    <li class="info">
        <c:choose>
            <c:when test="${param.from == 'list'}">
                <p><fmt:message key="userProfile.admin.message"/></p>
            </c:when>
            <c:otherwise>
                <p><fmt:message key="userProfile.message"/></p>
            </c:otherwise>
        </c:choose>
    </li>
    <li>
        <appfuse:label styleClass="desc" key="user.username"/>
        <form:errors path="username" cssClass="fieldError"/>
        <form:input path="username" id="username" cssClass="text large" cssErrorClass="text large error"/>
    </li>
    
    <c:if test="${cookieLogin != 'true' && userself == 'true'}">
    <li>
        <div>
            <div class="left">
                <appfuse:label styleClass="desc" key="user.password"/>
                <form:errors path="password" cssClass="fieldError"/>
                <form:password path="password" id="password" cssClass="text medium" cssErrorClass="text medium error" onchange="passwordChanged(this)" showPassword="true"/>
            </div>
            <div>
                <appfuse:label styleClass="desc" key="user.confirmPassword"/>
                <form:errors path="confirmPassword" cssClass="fieldError"/>
                <form:password path="confirmPassword" id="confirmPassword" cssClass="text medium" cssErrorClass="text medium error" showPassword="true"/>
            </div>
        </div>
    </li>
    </c:if>
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
<c:choose>
    <c:when test="${param.from == 'list' or param.method == 'Add'}">
    <li>
        <fieldset>
            <legend><fmt:message key="userProfile.accountSettings"/></legend>
            <form:checkbox path="enabled" id="enabled"/>
            <label for="enabled" class="choice"><fmt:message key="user.enabled"/></label>

            <form:checkbox path="accountExpired" id="accountExpired"/>
            <label for="accountExpired" class="choice"><fmt:message key="user.accountExpired"/></label>

            <form:checkbox path="accountLocked" id="accountLocked"/>
            <label for="accountLocked" class="choice"><fmt:message key="user.accountLocked"/></label>

            <form:checkbox path="credentialsExpired" id="credentialsExpired"/>
            <label for="credentialsExpired" class="choice"><fmt:message key="user.credentialsExpired"/></label>
        </fieldset>
    </li>
    <li>
    	<form:errors path="roleList" cssClass="fieldError"/>
        <fieldset class="pickList">
       		<legend><fmt:message key="userProfile.accountSettings"/></legend>
            <c:set var="checkedList" value="${user.roleList}" scope="request"/>
            <c:set var="checkList" value="${availableRoles}" scope="request"/>
            <c:import url="/WEB-INF/pages/radioList.jsp">
            	<c:param name="checkedItems" value="userRoles"/>
            </c:import>
            <!--table class="pickList">            
                <tr>
                    <th class="pickLabel">
                        <appfuse:label key="user.availableRoles" colon="false" styleClass="required"/>
                    </th>
                    <td></td>
                    <th class="pickLabel">
                        <appfuse:label key="user.roles" colon="false" styleClass="required"/>
                    </th>
                </tr>
                <c:set var="leftList" value="${availableRoles}" scope="request"/>
                <c:set var="rightList" value="${user.roleList}" scope="request"/>
                <c:import url="/WEB-INF/pages/pickList.jsp">
                    <c:param name="listCount" value="1"/>
                    <c:param name="leftId" value="availableRoles"/>
                    <c:param name="rightId" value="userRoles"/>
                </c:import>
            </table-->
        </fieldset>        
    </li>
    </c:when>
    <c:when test="${not empty user.username}">
    <li>
        <strong><appfuse:label key="user.roles"/>:</strong>
        <c:forEach var="role" items="${user.roleList}" varStatus="status">
            <c:out value="${role.label}"/><c:if test="${!status.last}">,</c:if>
            <input type="hidden" name="userRoles" value="<c:out value="${role.label}"/>"/>
        </c:forEach>
        <form:hidden path="enabled"/>
        <form:hidden path="accountExpired"/>
        <form:hidden path="accountLocked"/>
        <form:hidden path="credentialsExpired"/>
    </li>
    </c:when>
</c:choose>
    <li class="buttonBar bottom">
        <c:out value="${buttons}" escapeXml="false"/>
    </li>
</ul>
</form:form>

<script type="text/javascript">
    Form.focusFirstElement($('userForm'));
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

