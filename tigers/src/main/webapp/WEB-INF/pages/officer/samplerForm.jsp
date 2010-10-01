<%@ include file="/common/taglibs.jsp"%>  
<head>     
	<title><fmt:message key="samplerForm.title"/></title>     
	<meta name="heading" content="<fmt:message key='samplerList.heading'/>"/>
	<script type="text/javascript"> 

	var previousWindowOnLoadFn = window.onload

	window.onload = function() {
		init();
		previousWindowOnLoadFn();
	}

	function init() {
		document.getElementById("tag").focus();		 
	}
        
</script> 
</head>  

<form:form commandName="sampler" method="post" action="samplerform.html" id="samplerForm"> 
<form:errors path="*" cssClass="error" element="div"/> 
 
<table>  
	<tr>   
		<td>         
			<appfuse:label styleClass="desc" key="sampler.tag"/>
		</td>
		<td>         
			<form:errors path="tag" cssClass="fieldError"/>         
			<c:choose>
  				<c:when test="${empty sampler.id}">
  					<form:input path="tag" id="tag" cssClass="text medium"/>
  				</c:when>
  				<c:otherwise>			
  					<form:input path="tag" id="tag" disabled="true" cssClass="text medium"/>
  				</c:otherwise>
 			</c:choose>
			<form:hidden path="id" id="id" />
		</td>      

		<td>         
			<appfuse:label styleClass="desc" key="sampler.latitude"/>
		</td>
		<td>         
			<form:errors path="latitude" cssClass="fieldError"/>         
			<form:input path="latitude" id="latitude" cssClass="text medium"/>     
		</td>     
	
		<td>         
			<appfuse:label styleClass="desc" key="sampler.longitude"/> 
		</td>
		<td>         
			<form:errors path="longitude" cssClass="fieldError"/>         
			<form:input path="longitude" id="longitude" cssClass="text medium"/>    
		</td>     
	</tr>
	<tr>   
		<td>         
			<appfuse:label styleClass="desc" key="sampler.waterbody"/>
		</td>
		<td>         
			<form:errors path="waterbody" cssClass="fieldError"/>         
			<form:select path="waterbody.id" id="waterbody" cssClass="text medium">
    	        <form:option value="" label="Select" />
            	<form:options items="${waterbodyList}" itemValue="id" itemLabel="name"/>
            </form:select>
		</td>      

		<td>         
			<appfuse:label styleClass="desc" key="sampler.purpose"/>
		</td>
		<td>         
			<form:errors path="purpose" cssClass="fieldError"/>         
			<form:input path="purpose" id="purpose" cssClass="text medium"/>     
		</td>     
	
		<td>         
			<appfuse:label styleClass="desc" key="sampler.comp_screening_freq"/> 
		</td>
		<td>
			<form:errors path="comp_screening_freq" cssClass="fieldError"/>
            <form:select path="comp_screening_freq">
				<form:option value="" label="Select" />
            	<form:option value="daily" />
        	    <form:option value="weekly" />
            	<form:option value="fortnightly" />
            	<form:option value="monthly" />
            	<form:option value="half yearly" />
            	<form:option value="yearly" />
            </form:select>         
			
	    </td>         
	</tr>
	
	<tr>   
		<td>         
			<appfuse:label styleClass="desc" key="sampler.laboratory"/>
		</td>
		<td>         
			<form:errors path="laboratory" cssClass="fieldError"/>         
			<form:input path="laboratory.companyName" id="laboratory" cssClass="text medium" disabled="true"/>
		</td>      

		<td>         
			<appfuse:label styleClass="desc" key="sampler.contractor"/>
		</td>
		<td>         
			<form:errors path="contractor" cssClass="fieldError"/>
            <c:choose>
  				<c:when test="${empty sampler.id}">
  					<form:select path="contractor.id" id="contractor" cssClass="text medium">
                          <form:option value="" label="Select" />
	                      <form:options items="${contractorList}" itemValue="id" itemLabel="companyName" />
	                </form:select>
  				</c:when>
  				<c:otherwise>
  					<form:select path="contractor.id" id="contractor" cssClass="text medium"
                         onchange="if (confirm('Are you sure you want to assign a new contractor?')){
                                        selIdx = this.selectedIndex;
                                    } else {
                                        this.selectedIndex = selIdx;
                                    }">
                          <form:option value="" label="Select" />
	                      <form:options items="${contractorList}" itemValue="id" itemLabel="companyName" />
	                </form:select>
  				</c:otherwise>
 			</c:choose>


            <script type="text/javascript">
                // Set selIdx to defalut selected index on page load
                var selIdx = document.getElementById("contractor").selectedIndex;
            </script>
		</td>
		<td>         
			<appfuse:label styleClass="desc" key="sampler.license"/> 
		</td>
		<td>         
			<form:errors path="license" cssClass="fieldError"/>         
			<form:input path="license" id="license" cssClass="text medium"/>    
		</td>     
	</tr>	
	<tr>   
		<td>         
			<appfuse:label styleClass="desc" key="sampler.collar_height"/>
		</td>
		<td>         
			<form:errors path="collar_height" cssClass="fieldError"/>         
			<form:input path="collar_height" id="collar_height" cssClass="text medium"/>
		</td>       
	
		<td>         
			<appfuse:label styleClass="desc" key="sampler.depth_to_collar_screening_freq"/> 
		</td>
	    <td>
	    	<form:errors path="depth_to_collar_screening_freq" cssClass="fieldError"/>
            <form:select path="depth_to_collar_screening_freq">
            	<form:option value="" label="Select" />
            	<form:option value="daily" />
        	    <form:option value="weekly" />
            	<form:option value="fortnightly" />
            	<form:option value="monthly" />
            	<form:option value="half yearly" />
            	<form:option value="yearly" />
            </form:select>
        </td>          	    
	    <td>         
		</td>
		<td>            
		</td>        
	</tr>   
	<tr>
		<td colspan="6">
            <table>
                <tr>
		            <td>
			            <input type="submit" class="button" name="save" value="<fmt:message key="button.save"/>"/>
		            </td>
		            <td>
			            <c:if test="${not empty sampler.id}">
				            <input type="submit" class="button" name="delete" onclick="return confirmDelete('sampler')"
				            value="<fmt:message key="button.delete"/>" />
			            </c:if>
		            </td>
		            <td>
		            	<input type="submit" class="button" name="cancel" value="<fmt:message key="button.cancel"/>"/>
		            </td>
                    <td>
                        <c:if test="${not empty sampler.id}">
			                <input type="button" onclick="location.href='<c:url value="/officer/screeningfrequencies.html"><c:param name="id" value="${sampler.id}"/></c:url >'"
                            value="<fmt:message key="button.frequencies"/>"/>
                        </c:if>
		            </td>
                </tr>
            </table>
		</td>
	</tr>
</table> 

</form:form> 


