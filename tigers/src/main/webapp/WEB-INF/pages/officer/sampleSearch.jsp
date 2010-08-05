<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="sampleList.title"/></title>
    <meta name="heading" content="<fmt:message key='sampleList.heading'/>"/>
    <meta name="menu" content="SampleMenu"/>
    <script type="text/javascript" src="<c:url value='/scripts/calendar/jscal2.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/calendar/lang/en.js'/>"></script>
    <link rel="stylesheet" type="text/css" href="/styles/jscal2/jscal2.css" />
    <link rel="stylesheet" type="text/css" href="/styles/jscal2/border-radius.css" />
    <link rel="stylesheet" type="text/css" href="/styles/jscal2/gold/gold.css" />
</head>

<!-- ================================================================================================= -->
<form:form commandName="search" method="post" action="/officer/samplesearch.html" id="sampleSearch">
<form:errors path="*" cssClass="error" element="div"/>
<ul>
    <li>
        <appfuse:label styleClass="desc" key="sampleSearch.samplerId"/>
        <form:errors path="samplerId" cssClass="fieldError"/>
        <form:input path="samplerId" id="samplerId" cssClass="text medium"/>        
    </li>
    <li>
        <appfuse:label styleClass="desc" key="sampleSearch.from"/>
        <form:errors path="from" cssClass="fieldError"/>
        <form:input path="from" id="from" cssClass="text medium"/>
        <input type="button" id="fromB" class="button"  value="..."/> 
    </li>  
    <li>
    	<appfuse:label styleClass="desc" key="sampleSearch.to"/>
    	<form:errors path="to" cssClass="fieldError"/>
    	<form:input path="to" id="to" cssClass="text medium"/>
    	<input type="button" id="toB" class="button" value="..."/> 
    </li>
    <li class="buttonBar bottom">
        <input type="submit" class="button" name="search" value="<fmt:message key="button.search"/>"/>
    </li>
</ul>
</form:form>

<!-- ================================================================================================= -->
<c:if test="${not empty firstTime}">
	<display:table name="sampleList" cellspacing="0" cellpadding="0" requestURI=""
	    id="sampleList" pagesize="25" class="table sampleList" export="true">
	
	    <display:column property="id" escapeXml="true" sortable="true"
	        url="../sampleform.html" paramId="id" paramProperty="id" titleKey="sample.id"/>
	    <display:column property="tag" escapeXml="true" sortable="true" titleKey="sample.tag"/>
	    <display:column property="ph" escapeXml="true" sortable="true" titleKey="sample.ph"/>
	
	    <display:setProperty name="paging.banner.item_name" value="sample"/>
	    <display:setProperty name="paging.banner.items_name" value="sample"/>
	
	    <display:setProperty name="export.excel.filename" value="Sample List.xls"/>
	    <display:setProperty name="export.csv.filename" value="Sample List.csv"/>
	    <display:setProperty name="export.pdf.filename" value="Sample List.pdf"/>
	</display:table>
</c:if>
<!-- ================================================================================================= -->

<script type="text/javascript">
    Form.focusFirstElement($('sampleSearch'));

    var cal1=Calendar.setup(
        	{
        	    inputField  : "from",      // id of the input field
        	    //dateFormat    : "%d/%m/%Y %H:%M",      // the date format
        	    trigger      : "fromB",    // id of the button
            	

        	}
    	);


        function updateFields1(cal) {
            
//    	    var date = cal.selection.get();
//    	    if (date) {
//    	            date = Calendar.intToDate(date);
//    	            document.getElementById("date_taken").value = Calendar.printDate(date, "%d/%m/%Y");
//    	    }
    	    var hour=cal.getHours();
    	    var min=cal.getMinutes();
    	    date=document.getElementById("from").value.split(" ")[0];
    	    document.getElementById("from").value=date+" "+hour+":"+min;
    };

    var cal2=Calendar.setup(
        	{
        	    inputField  : "to",      // id of the input field
        	    //dateFormat    : "%d/%m/%Y %H:%M",      // the date format
        	    trigger      : "toB",    // id of the button
            	

        	}
    );


        function updateFields1(cal) {
            
//    	    var date = cal.selection.get();
//    	    if (date) {
//    	            date = Calendar.intToDate(date);
//    	            document.getElementById("date_taken").value = Calendar.printDate(date, "%d/%m/%Y");
//    	    }
    	    var hour=cal.getHours();
    	    var min=cal.getMinutes();
    	    date=document.getElementById("from").value.split(" ")[0];
    	    document.getElementById("from").value=date+" "+hour+":"+min;
    };
        
</script>