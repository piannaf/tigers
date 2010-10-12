<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="cssHorizontalMenu.vm" permissions="rolesAdapter">
<ul id="primary-nav" class="menuList">
    <li class="pad">&nbsp;</li>
    <c:if test="${empty pageContext.request.remoteUser}"><li><a href="<c:url value="/login.jsp"/>" class="current"><fmt:message key="login.title"/></a></li></c:if>
    <menu:displayMenu name="MainMenu"/>
    
    <menu:displayMenu name="AdminMenu"/>
    <menu:displayMenu name="SystemReportMenu"/>
    
    <menu:displayMenu name="OfficerReportMenu"/>
    <menu:displayMenu name="OfficerAdministrationMenu"/>
    <menu:displayMenu name="OfficerExecutionMenu"/>
    <menu:displayMenu name="OfficerMap"/>
    <menu:displayMenu name="OfficerHelp"/>
    
    <menu:displayMenu name="LabSampleMenu"/>
    <menu:displayMenu name="LabContractorMenu"/>
    <menu:displayMenu name="LabSamplerMenu"/>
    
    <menu:displayMenu name="ContractorLabMenu"/>
    <menu:displayMenu name="ContractorSamplerMenu"/>
    <menu:displayMenu name="ContractorSendSample"/>
    <menu:displayMenu name="ContractorMap"/>
</ul>
</menu:useMenuDisplayer>