<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="bulkUpload.display.title"/></title>
    <meta name="heading" content="<fmt:message key='bulkUpload.display.heading'/>"/>
    <meta name="menu" content="LaboratoryMenu"/>
</head>

<p><fmt:message key="bulkUpload.display.message"/></p>

<div class="separator"></div>

<table class="detail" cellpadding="5">
    <tr>
       <c:out value="${message}" escapeXml="false"/>
    </tr>
    <tr>
        <td></td>
        <td class="buttonBar">
            <input type="button" name="done" id="done" value="Done"
                onclick="location.href='mainMenu.html'" />
            <input type="button" style="width: 120px" value="Upload Another"
                onclick="location.href='/laboratory/bulkupload.html'" />
        </td>
    </tr>
</table>


