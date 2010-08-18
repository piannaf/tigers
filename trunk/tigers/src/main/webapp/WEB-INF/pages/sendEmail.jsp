<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="email.title"/></title>
    <meta name="heading" content="<fmt:message key='email.heading'/>"/>
    <meta name="menu" content="Email"/>   
</head>

<!-- ================================================================================ -->
<form:form commandName="email" method="post" id="sendEmailForm" action="sendemail.html">
<form:errors path="*" cssClass="error" element="div"/>
<ul>
	<li>
    	<appfuse:label styleClass="desc" key="email.sendTo"/>
    	${company}
    </li>
    <li>
    	<appfuse:label styleClass="desc" key="email.subject"/>
    	<form:errors path="subject" cssClass="fieldError"/>
    	<form:input path="subject" id="subject" cssClass="text medium"/>
    </li>
    <li>
    	<appfuse:label styleClass="desc" key="email.content"/>
    	<form:errors path="content" cssClass="fieldError"/>
    	<form:textarea path="content" cssClass="text large"/>
    </li>
    <li class="buttonBar bottom">
        <input type="submit" class="button" name="send" value="<fmt:message key='button.send'/>"/> 
        <input type="submit" class="button" name="cancelSending" value="<fmt:message key='button.cancel'/>"/>      
    </li>
</ul>

</form:form>


