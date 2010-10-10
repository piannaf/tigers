<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="reportExceedance.title"/></title>
<meta name="menu" content="CourseMenu"/>
<link href="/styles/autocomplete.css" media="screen" rel="stylesheet" type="text/css" />
<!--[if IE]>
<script src="/scripts/excanvas.js" type="text/javascript"></script>
<![endif]-->
<script src="/scripts/ProtoChart.js" type="text/javascript"></script>

</head>
<div id="breadcrumb"><a href="<c:url value='reportexceedancesamplers.html'/>"><fmt:message key="reportSamplers.title"/></a> &raquo;</div>
<h2>Exceedance Report for Sampler <em><c:out value="${tag}" escapeXml="true"/></em></h2>

<c:if test="${not empty thresholds}">
<h3>Exceedance Thresholds Defined</h3>
<display:table name="thresholds" cellspacing="0" cellpadding="0" requestURI=""
    id="thresholds" class="table sampleList" export="true">

    <display:column property="parameter.name" escapeXml="true" sortable="true"
        url="waterbodyform.html" paramId="id" paramProperty="waterbody.id" titleKey="threshold.name"/>
    <display:column property="min" escapeXml="true" sortable="true" titleKey="threshold.min"/>
    <display:column property="max" escapeXml="true" sortable="true" titleKey="threshold.max"/>
</display:table>


<h3>Recorded Exceedance Samples</h3>
<c:if test="${not empty data}">
<display:table name="data" cellspacing="0" cellpadding="0" requestURI=""
    id="data" pagesize="25" class="table sampleList" export="true">

    <display:column property="sampleTaken" escapeXml="true" sortable="true"
        paramId="id" paramProperty="sampleId" titleKey="sample.date_taken"/>
    <display:column property="paramName" escapeXml="true" sortable="true" titleKey="exceedance.paramName"/>
    <display:column property="value" escapeXml="true" sortable="true" titleKey="exceedance.value"/>
    <display:column property="magnitude" escapeXml="true" sortable="true" titleKey="exceedance.magnitude"/>

    <display:setProperty name="paging.banner.item_name" value="sample"/>
    <display:setProperty name="paging.banner.items_name" value="sample"/>

    <display:setProperty name="export.excel.filename" value="Sample List.xls"/>
    <display:setProperty name="export.csv.filename" value="Sample List.csv"/>
    <display:setProperty name="export.pdf.filename" value="Sample List.pdf"/>
</display:table>

<br/>
<hr/>
<br/>
<h3>Exceedance Summary Graph</h3>
<center>
<table style="border: 0;">
<tr>
	<td><div style="width: 1em; white-space: nowrap; -webkit-transform: rotate(-90deg); -moz-transform: rotate(-90deg); text-align: center; font-size: smaller;">Number of Exceedances</div></td>
	<td><div id="chart" style="width:400px;height:300px;"></div></td>
</tr>
<tr>
	<td></td>
	<td><div style="text-align: center; font-size: smaller;">Parameter</div></td>
</tr>
</table>
</center>

<script type="text/javascript">
/* <!-- */
var dat = [ <c:forEach var="exc" items="${exceedCounts}">["<c:out value="${exc.key}" escapeXml="true"/>",<c:out value="${exc.value}" escapeXml="true"/>], </c:forEach> [0,0]];
dat.pop();

var num2thres = [], newDat = [];
for(var i=0;i<dat.length; i++) {
	var k = dat[i][0];
	switch(k) {
		case "ph": num2thres[i] = "PH"; break;
		case "ec": num2thres[i] = "EC"; break;
		case "temperature": num2thres[i] = "Temperature"; break;
		case "collar_depth": num2thres[i] = "Collar Depth"; break;
		case "arsenic": num2thres[i] = "Arsenic"; break;
		case "grease": num2thres[i] = "Grease"; break;
		case "fluoride": num2thres[i] = "Fluoride"; break;
		case "chromium": num2thres[i] = "Chromium"; break;
	}
	newDat.push([i, dat[i][1]]);
}
dat = [{data: newDat, label: "Exceedances"}];

new Proto.Chart($('chart'), dat,
	{
		xaxis: {min: -0.5, max: i-0.5, tickSize: 1, tickFormatter: function(v) {
			return num2thres[v];
		}},
		yaxis: {min: 0, autoscaleMargin: 0.1, minTickSize: 1, tickDecimals: 0},
		bars: {show: true},
		//points: {show: true}
	});
	
/* --> */
</script>


</c:if>
<c:if test="${empty data}">
No exceedance samples found.
</c:if>
</c:if>
<c:if test="${empty thresholds}">
No exceedance thresholds have been defined.
</c:if>