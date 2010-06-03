<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="sampleList.title"/></title>
<meta name="heading" content="<fmt:message key='sampleList.heading'/>"/>
<meta name="menu" content="CourseMenu"/>

	<script type="text/javascript" src="<c:url value='/scripts/calendar/jscal2.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/calendar/lang/en.js'/>"></script>
    <link rel="stylesheet" type="text/css" href="/styles/jscal2/jscal2.css" />
    <link rel="stylesheet" type="text/css" href="/styles/jscal2/border-radius.css" />
    <link rel="stylesheet" type="text/css" href="/styles/jscal2/gold/gold.css" />
</head>

<c:set var="buttons">
<input type="button" style="margin-right: 5px"
onclick="location.href='<c:url value="/sampleform.html"/>'"
value="<fmt:message key="button.add"/>"/>

<input type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'"
value="<fmt:message key="button.done"/>"/>
</c:set>
<hr />
<form method="get" action="searchServlet">


<label for"from">From:</label>
<input type="text" id="from" name="from" />
<input type="button" id="fromB" class="button"  value="..."/> 

<label for"from">To:</label>
<input type="text" id="to" name="to" />
<input type="button" id="toB" class="button" value="..."/> 

<input type="submit" id="submit"  value="Search" />
</form>
<hr />

<c:out value="${buttons}" escapeXml="false"/>
<display:table name="sampleList" cellspacing="0" cellpadding="0" requestURI=""
id="sampleList" pagesize="25" class="table sampleList" export="true">

<display:column property="id" escapeXml="true" sortable="true"
url="/sampleform.html" paramId="id" paramProperty="id" titleKey="sample.id"/>
<display:column property="date_taken" escapeXml="true" sortable="true" titleKey="sample.date_taken"/>
<display:column property="ph" escapeXml="true" sortable="true" titleKey="sample.ph"/>
<display:column property="ec" escapeXml="true" sortable="true" titleKey="sample.ec"/>
<display:column property="temperature" escapeXml="true" sortable="true" titleKey="sample.temperature"/>
<display:column property="collar_depth" escapeXml="true" sortable="true" titleKey="sample.collar_depth"/>
<display:column property="arsenic" escapeXml="true" sortable="true" titleKey="sample.arsenic"/>
<display:column property="grease" escapeXml="true" sortable="true" titleKey="sample.grease"/>
<display:column property="fluoride" escapeXml="true" sortable="true" titleKey="sample.fluoride"/>
<display:column property="chromium" escapeXml="true" sortable="true" titleKey="sample.chromium"/>
<display:column property="username" escapeXml="true" sortable="true" titleKey="sample.username"/>
<display:column property="tag" escapeXml="true" sortable="true" titleKey="sample.tag"/>

<display:setProperty name="paging.banner.item_name" value="sample"/>
<display:setProperty name="paging.banner.items_name" value="samples"/>

<display:setProperty name="export.excel.filename" value="Course List.xls"/>
<display:setProperty name="export.csv.filename" value="Course List.csv"/>
<display:setProperty name="export.pdf.filename" value="Course List.pdf"/>
</display:table>

<c:out value="${buttons}" escapeXml="false"/>

<script type="text/javascript">
highlightTableRows("sampleList");

var cal1=Calendar.setup(
    	{
    	    inputField  : "from",      // id of the input field
    	    //dateFormat    : "%d/%m/%Y %H:%M",      // the date format
    	    trigger      : "fromB",    // id of the button
        	

    	}
	);


    function updateFields1(cal) {
        
//	    var date = cal.selection.get();
//	    if (date) {
//	            date = Calendar.intToDate(date);
//	            document.getElementById("date_taken").value = Calendar.printDate(date, "%d/%m/%Y");
//	    }
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
        
//	    var date = cal.selection.get();
//	    if (date) {
//	            date = Calendar.intToDate(date);
//	            document.getElementById("date_taken").value = Calendar.printDate(date, "%d/%m/%Y");
//	    }
	    var hour=cal.getHours();
	    var min=cal.getMinutes();
	    date=document.getElementById("from").value.split(" ")[0];
	    document.getElementById("from").value=date+" "+hour+":"+min;
};


</script>

