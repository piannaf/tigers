<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userInfo.title"/></title>
    <meta name="heading" content="<fmt:message key='userInfo.heading'/>"/>
    <meta name="menu" content="UserMenu"/>
</head>


<!-- ======================================================================================== -->

<table class="detail" cellpadding="5">
	<tr>
		<td>
			<strong><fmt:message key="user.username"/>:</strong>
		</td>
		<td>
			${user.username}
		</td>
	</tr>
	<tr>
		<td>
			<strong><fmt:message key="user.companyName"/>:</strong>
		</td>
		<td>
			${user.companyName}
		</td>
	</tr>
	<tr>
		<td>
			<strong><fmt:message key="user.phoneNumber"/>:</strong>			
		</td>
		<td>
			${user.phoneNumber}
		</td>
	</tr>
	<tr>
		<td>
			<strong><fmt:message key="user.email"/>:</strong>
		</td>
		<td>
			${user.email}
		</td>
	</tr>
	<tr>
		 <td><strong><fmt:message key="user.address.address"/>:</strong></td>
         <td>${user.address.address}</td>
    </tr>
    <tr>
		 <td><strong><fmt:message key="user.address.city"/>:</strong></td>
         <td>${user.address.city}</td>
    </tr>
	<tr>
		 <td><strong><fmt:message key="user.address.postalCode"/>:</strong></td>
         <td>${user.address.postalCode}</td>
    </tr>
    <tr>
		 <td><strong><fmt:message key="user.address.province"/>:</strong></td>
         <td>${user.address.province}</td>
    </tr>
    <tr>
		 <td><strong><fmt:message key="user.address.country"/>:</strong></td>
         <td>${user.address.country}</td>
    </tr>
	<tr>	
		<td class="buttonBar">
			<form method="get" action="email.html">
				<input type="hidden" name="id" value="${user.id}" id="id"/>
	   			<input type="submit" class="button" name="sendEmail" value="<fmt:message key="button.sendEmail"/>"/>
	   		</form>
		</td>
	</tr>
</table>

	
	
	

<!-- ======================================================================================== -->
