<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userList.title"/></title>
    <meta name="heading" content="<fmt:message key='userList.heading'/>"/>
    <meta name="menu" content="AdminMenu"/>
</head>

<!-- ================================================================================================= -->
<form:form commandName="search" method="post" action="/admin/usersearch.html" id="userSearchForm">
<form:errors path="*" cssClass="error" element="div"/>
<ul>
    <li>
    	<fieldset>
    		<legend><fmt:message key="user.username"/></legend> 
		        <form:input path="username" id="username" cssClass="text medium"/>
	    </fieldset>
    </li>
    <li>
    	<fieldset>
    		<legend><fmt:message key="user.companyName"/></legend>  
	        	<form:input path="companyName" id="companyName" cssClass="text medium"/>
	    </fieldset>    
    </li> 
    <li>
    	<fieldset>
            <legend><fmt:message key="user.roles"/></legend>
	        <c:set var="checkedList" value="${search.roleList}" scope="request"/>
			<c:set var="checkList" value="${availableRoles}" scope="request"/>
            <c:import url="/WEB-INF/pages/checkList.jsp">
            	<c:param name="checkedItems" value="roles"/>
            </c:import>
	    </fieldset>    
    </li>        
    <li class="buttonBar bottom">
        <input type="submit" class="button" name="search" value="<fmt:message key="button.search"/>"/>
    </li>
</ul>
</form:form>

<!-- ================================================================================================= -->

<display:table name="userList" cellspacing="0" cellpadding="0" requestURI="" 
    defaultsort="1" id="users" pagesize="25" class="table" export="true">
    <display:column property="username" escapeXml="true" sortable="true" titleKey="user.username" style="width: 25%"
        url="/userform.html?from=list" paramId="id" paramProperty="id"/>
    <display:column property="companyName" escapeXml="true" sortable="true" titleKey="activeUsers.companyName" style="width: 34%"/>
    <display:column property="currentRole" escapeXml="true" sortable="true" titleKey="activeUsers.roles" style="width: 34%"/>
    <display:column property="email" sortable="true" titleKey="user.email" style="width: 25%" autolink="true" media="html"/>
    <display:column property="email" titleKey="user.email" media="csv xml excel pdf"/>
    <display:column sortProperty="enabled" sortable="true" titleKey="user.enabled" style="width: 16%; padding-left: 15px" media="html">
        <input type="checkbox" disabled="disabled" <c:if test="${users.enabled}">checked="checked"</c:if>/>
    </display:column>
    <display:column property="enabled" titleKey="user.enabled" media="csv xml excel pdf"/>

    <display:setProperty name="paging.banner.item_name" value="user"/>
    <display:setProperty name="paging.banner.items_name" value="users"/>

    <display:setProperty name="export.excel.filename" value="User List.xls"/>
    <display:setProperty name="export.csv.filename" value="User List.csv"/>
    <display:setProperty name="export.pdf.filename" value="User List.pdf"/>
</display:table>



