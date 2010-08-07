<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="email.title"/></title>
    <meta name="heading" content="<fmt:message key='email.heading'/>"/>
    <meta name="menu" content="Email"/>   
</head>

<!-- ================================================================================ -->
<form:form commandName="email" method="post" id="emailExampleForm" action="emailexample.html">
<form:errors path="*" cssClass="error" element="div"/>
<ul>
	<li>
		<fieldset>
			<legend><fmt:message key="email.sendTo"/></legend>
			<form:errors path="to" cssClass="fieldError"/>
			<form:checkbox path="to" id="sean" value="sean"/>SEAN
			<form:checkbox path="to" id="jane" value="jane"/>JANE
			<form:checkbox path="to" id="justin" value="justin"/>JUSTIN<br/>
			<form:checkbox path="to" id="dell" value="dell"/>DELL
			<form:checkbox path="to" id="xing" value="xing"/>XING
			<form:checkbox path="to" id="nicholas" value="nicholas"/>NICHOLAS
		</fieldset>
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
    </li>
</ul>

</form:form>


