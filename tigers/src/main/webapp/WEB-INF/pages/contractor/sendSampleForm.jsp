<%@ include file="/common/taglibs.jsp"%>  
<head>     
    <title><fmt:message key="Send Sample to Laboratory"/></title>     
    <meta name="heading" content="<fmt:message key='Send Sample to Laboratory'/>"/> 

    <script type="text/javascript" src="<c:url value='/scripts/calendar/jscal2.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/calendar/lang/en.js'/>"></script>
    <link rel="stylesheet" type="text/css" href="/styles/jscal2/jscal2.css" />
    <link rel="stylesheet" type="text/css" href="/styles/jscal2/border-radius.css" />
    <link rel="stylesheet" type="text/css" href="/styles/jscal2/gold/gold.css" />
    
</head>  

<form:form commandName="sample" method="post" action="sendsampleform.html" id="sampleForm"> 
<form:errors path="*" cssClass="error" element="div"/> 
<form:hidden path="id"/> 
<ul>  
    
    
   
    <li>         
        <appfuse:label styleClass="desc" key="sample.date_taken"/>         
        <form:errors path="date_taken" cssClass="fieldError"/>         
        <form:input  path="date_taken" id="date_taken" cssClass="text medium"/>
        <button id="dateButton" type="button" class="button"> ... </button> 
    </li>      
    
    <script>
       var dt=new Date();
       var year=dt.getFullYear();
       var month=dt.getMonth() < 10 ? '0' + dt.getMonth() : '' + dt.getMonth();
       var date=dt.getDate() < 10 ? '0' + dt.getDate() : '' + dt.getDate();
       var hour=dt.getHours() < 10 ? '0' + dt.getHours() : '' + dt.getHours();
       var min=dt.getMinutes() < 10 ? '0' + dt.getMinutes() : '' + dt.getMinutes();
       var datetime=year+"-"+month+"-"+date+" "+hour+" "+min;
      
       document.getElementById("date_taken").value=datetime;
      
    </script>

        <form:hidden path="ph" id="ph" />     
        <form:hidden path="ec" id="ec" />  
        <form:hidden path="temperature" id="temperature" />
        <form:hidden path="collar_depth" id="collar_depth" /> 
        <form:hidden path="arsenic" id="arsenic" />    
        <form:hidden path="grease" id="grease" />      
        <form:hidden path="fluoride" id="fluoride" />    
        <form:hidden path="chromium" id="chromium" />  
    
    <li>         
        <appfuse:label styleClass="desc" key="sample.laboratoryName"/>         
        <form:errors path="laboratory.id" cssClass="fieldError"/>         
        <form:select path="laboratory.id" id="laboratory" cssClass="text medium">
            <form:option value="" label="Select" />
            <form:options items="${laboratoryList}" itemValue="id" itemLabel="companyName"/>
        </form:select>      
    </li>     
    
    <li>         
        <appfuse:label styleClass="desc" key="sample.sampler"/>         
        <form:errors path="sampler.id" cssClass="fieldError"/>         
        <form:select path="sampler.id" id="sampler" cssClass="text medium">
            <form:option value="" label="Select" />
            <form:options items="${samplerList}" itemValue="id" itemLabel="tag"/>
        </form:select>     
    </li>      
    
    <li class="buttonBar bottom">         
        <input type="submit" class="button" name="save" value="<fmt:message key="button.save"/>"/>         
        <c:if test="${not empty sample.id}">         
        <input type="submit" class="button" name="delete" onclick="return confirmDelete('sample')"              
            value="<fmt:message key="button.delete"/>" />         
        </c:if>         
        <input type="submit" class="button" name="cancel" value="<fmt:message key="button.cancel"/>"/>     
    </li> 
</ul> 
</form:form>  

<script type="text/javascript">     
    Form.focusFirstElement($('sampleForm'));
    
    Calendar.setup(
        {
            inputField  : "date_taken",      // id of the input field
            //dateFormat    : "%d/%m/%Y %H:%M",      // the date format
            trigger      : "dateButton",    // id of the button
            showTime: 12,
            onTimeChange : updateFields

        }
    );


    function updateFields(cal) {
        
      var date = cal.selection.get();
      if (date) {
              date = Calendar.intToDate(date);
              document.getElementById("date_taken").value = Calendar.printDate(date, "%Y-%m-%d");
      }
        var hour=cal.getHours() < 10 ? '0' + cal.getHours() : cal.getHours();
        var min=cal.getMinutes() < 10 ? '0' + cal.getMinutes() : cal.getMinutes();
        date=document.getElementById("date_taken").value.split(" ")[0];
        document.getElementById("date_taken").value=date+" "+hour+":"+min;
    };
</script> 