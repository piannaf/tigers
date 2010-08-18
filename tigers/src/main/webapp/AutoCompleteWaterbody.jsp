<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul>
<c:forEach var="waterbody" items="${waterbodies}">
<li>${waterbody}</li>
</c:forEach> 
</ul>


