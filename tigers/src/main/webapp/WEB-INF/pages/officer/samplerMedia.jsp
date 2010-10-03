<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="samplerMediaList.title"/></title>

</head>

<display:table name="samplerMediaList" cellspacing="0" cellpadding="0" requestURI=""
id="samplerMediaList" pagesize="25" class="table sampleList">

<display:column titleKey="samplerMedia.fileName" style="width:40%;">
<a href="/samplermedia/<c:out value="${samplerMediaList.id}" escapeXml="true"/>_<c:out value="${samplerMediaList.fileName}" escapeXml="true"/>"><c:out value="${samplerMediaList.fileName}" escapeXml="true"/></a>
</display:column>
<display:column titleKey="samplerMedia.description" property="description" escapeXml="true" style="width:60%;"/>

<display:setProperty name="paging.banner.item_name" value="samplermedia"/>
<display:setProperty name="paging.banner.items_name" value="samplermedia"/>
</display:table>

