<%@ include file="/common/taglibs.jsp"%>



<c:forEach var="checkItem" items="${checkList}" varStatus="status">
	
		<input type="checkbox" name="${param.checkedItems}" value="${chekcItem.value}">
		<c:out value="${checkItem.label}" escapeXml="false"/><br/>
</c:forEach>

