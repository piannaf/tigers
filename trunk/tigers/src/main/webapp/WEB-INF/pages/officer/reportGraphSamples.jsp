<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="reportSampler.title"/></title>
<meta name="menu" content="CourseMenu"/>
<link href="/styles/autocomplete.css" media="screen" rel="stylesheet" type="text/css" />
<!--[if IE]>
<script src="/scripts/excanvas.js" type="text/javascript"></script>
<![endif]-->
<script src="/scripts/ProtoChart.js" type="text/javascript"></script>

<style type="text/css">
	.mouseValHolder {
		padding: 5px;
		border: 1px solid #808080;
		background: #F0F0F0;
		opacity: 0.7;
		background-color: #000;
		color: #fff;
		height: 20px;
		width: 150px;
	}
</style>
</head>
<div id="breadcrumb"><a href="<c:url value='reportsamplers.html'/>"><fmt:message key="reportSamplers.title"/></a> &raquo; <a href="<c:url value='reportsamples.html'><c:param name='tag' value='${tag}'/></c:url>">Samples Report</a> &raquo;</div>
<h2>Samples Graph for Sampler <em><c:out value="${tag}" escapeXml="true"/></em></h2>

<form action="<c:url value='reportgraphsamples.html'/>" method="get" id="graphparamselect">
<div style="vertical-align: top; width: 100%;">
<input type="hidden" name="tag" value="<c:out value='${tag}' escapeXml='true'/>"/>
Selected Parameter: <select name="display_parameter" onchange="$('graphparamselect').submit();">
<c:forEach var="parameterNames" items="${params}">
<option value="<c:out value='${parameterNames.parameter_id}' escapeXml='true'/>"<c:if test='${parameterName.parameter_id == parameterNames.parameter_id}'> selected="selected"</c:if>><c:out value="${parameterNames.name}" escapeXml="true"/></option>
</c:forEach>
</select>
</div>
</form>

<table style="border: 0;">
<tr>
	<td><div style="width: 1em; white-space: nowrap; -webkit-transform: rotate(-90deg); -moz-transform: rotate(-90deg); text-align: center; font-size: smaller;"><c:out value="${parameterName.name}" escapeXml="true"/></div></td>
	<td><div id="chart" style="width:500px;height:300px;"></div></td>
</tr>
<tr>
	<td></td>
	<td><div style="text-align: center; font-size: smaller;">Date</div></td>
</tr>
</table>

<script type="text/javascript">
/* <!-- */
var dat = [ <c:forEach var="sample" items="${data}">[<c:out value="${sample.key}" escapeXml="true"/>,<c:out value="${sample.value}" escapeXml="true"/>], </c:forEach> [0,0]];
dat.pop();
dat.sort(function(a,b) {
	return a[0] - b[0];
});
dat = [{data: dat, label: "Data"}];

var startTime = <c:out value="${startTime}"/>, endTime = <c:out value="${endTime}"/>;
var makeLineArray = function(val) {
	return [[startTime, val],[endTime, val]];
};
<c:if test="${thresMin != null}">
dat.push({data: makeLineArray(<c:out value="${thresMin}"/>), label: "Min Thresh."});
</c:if>
<c:if test="${thresMax != null}">
dat.push({data: makeLineArray(<c:out value="${thresMax}"/>), label: "Max Thresh."});
</c:if>

var minValue = <c:out value="${minValue}"/>, maxValue = <c:out value="${maxValue}"/>;
var padding = (maxValue-minValue) * 0.1;
if(!padding) padding = 1;

minValue -= padding;
maxValue += padding;

new Proto.Chart($('chart'), dat,
	{
		// new Date(2000,1,1).getTime()
		xaxis: {min: startTime, max: endTime, tickSize: [1, "day"], mode: "time"},
		yaxis: {min: minValue, max: maxValue},
		mouse: {
			track: true,
			fixedPosition: false,
			trackFormatter: function(v) {
				return "[" + new Date(parseInt(v.x)).toDateString() + ", " + v.y + "]";
			}
		},
		legend: {show: true},
		colors: ["#00A8F0", "#ff0000", "#ff8000"],
		lines: {show: true},
		points: {show: true}
	});
	
/* --> */
</script>

