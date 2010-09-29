<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="assignLaboratory.title"/></title>
<meta name="heading" content="<fmt:message key='assignLaboratory.heading'/>"/>
<meta name="menu" content="ContractorSamplerMenu"/>
<link rel="stylesheet" type="text/css" href="/styles/autocomplete.css" />

</head>


<div class="info">
	        <fmt:message key="assignLabPre.message"/>
</div>

<display:table name="samplerList" cellspacing="0" cellpadding="0" requestURI=""
id="samplerList" pagesize="25" class="table samplerList" export="false">
<display:column property="tag" escapeXml="true" sortable="true" title="Update Sampler"
url="/contractor/assignlabpost.html" paramId="tag" paramProperty="tag" titleKey="sampler.tag"/>
<display:column property="waterbody.name" escapeXml="true" sortable="true" titleKey="sampler.waterbody"/>
<display:column property="laboratory.username" escapeXml="true" sortable="true" titleKey="sampler.laboratory"/>


<display:setProperty name="paging.banner.item_name" value="sampler"/>
<display:setProperty name="paging.banner.items_name" value="samplers"/>

</display:table>

<script type="text/javascript">
    highlightTableRows("samplerList");
</script>




