<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="laboratoryList.title"/></title>
    <meta name="heading" content="<fmt:message key='laboratoryList.heading.available'/>"/>
    <meta name="menu" content="AdminMenu"/>
</head>




<display:table name="userList" cellspacing="0" cellpadding="0" requestURI="" 
    defaultsort="1" id="users" pagesize="25" class="table" export="true">
    <display:column property="username" escapeXml="true" sortable="true" titleKey="user.username" style="width: 25%"
         paramId="id" paramProperty="id"/>
    <display:column property="companyName" escapeXml="true" sortable="true" titleKey="activeUsers.companyName" style="width: 34%"/>
    <display:column property="email" sortable="true" titleKey="user.email" style="width: 25%" autolink="true" media="html"/>
    <display:column property="email" titleKey="user.email" media="csv xml excel pdf"/>
    <display:column titleKey="user.add" style="width: 16%; padding-left: 15px" media="html" 
        url="addexistinglab.html" paramId="id" paramProperty="id">
        <input type="button" value="add" />
    </display:column>
    
    <display:column property="enabled" titleKey="user.enabled" media="csv xml excel pdf"/>

    <display:setProperty name="paging.banner.item_name" value="user"/>
    <display:setProperty name="paging.banner.items_name" value="users"/>

    <display:setProperty name="export.excel.filename" value="User List.xls"/>
    <display:setProperty name="export.csv.filename" value="User List.csv"/>
    <display:setProperty name="export.pdf.filename" value="User List.pdf"/>
</display:table>

<c:out value="${buttons}" escapeXml="false" />

<script type="text/javascript">
    highlightTableRows("users");
</script>
