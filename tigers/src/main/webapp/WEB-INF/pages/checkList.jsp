<%@ include file="/common/taglibs.jsp"%>



<c:forEach var="checkItem" items="${checkList}" varStatus="status">
	
		<input type="checkbox" name="${param.checkedItems}" value="${checkItem.value}" 
			<c:forEach var="checkedItem" items="${checkedList}" varStatus="status">
				<c:choose>
					<c:when test="${checkedItem.value == checkItem.value}">
						checked
					</c:when>
					<c:otherwise>
					</c:otherwise>	
				</c:choose>
			</c:forEach>
		>
		<c:out value="${checkItem.label}" escapeXml="false"/><br/>
</c:forEach>

