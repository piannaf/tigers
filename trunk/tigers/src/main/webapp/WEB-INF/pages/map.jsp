<%@ include file="/common/taglibs.jsp"%>

<head> 
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" /> 
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/> 
<style type="text/css"> 
    #map_container {
        height: 600px;
        width: 200%;
        margin-left: -35%;
        border: 1px solid red;
    }

    #map_canvas {
        height: 100%;
        width: 70%;
        float: left;
    }

    #the_side_bar {
        height: 50%;
        width: 29%;
        float: left;
        
        overflow:auto;
        white-space:nowrap;
        
        border: 1px solid green;
    }
    
    #info_area {
        height: 50%;
        width: 29%;
        float: left;
        
        border: 1px solid blue;
    }
</style> 
<title>Google Maps JavaScript API v3 Example: Map Simple</title> 
<script type="text/javascript" src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAAxKNSmreHpX8AESttKh0VwBTwM0brOpm-All5BF6PoaKBxRWWERS8jM1JywDsPc13gAgPMsMvUVJrZw"></script> 
<script type="text/javascript" src="<c:url value='/scripts/geoxml.js'/>"></script>
<script type="text/javascript">   
    //<![CDATA[
        window.onload = initialize;
        var gml, mmap;
        function initialize() {    
            mmap=new GMap2(document.getElementById("map_canvas"),{draggableCursor: 'crosshair', draggingCursor: 'move'});
            mmap.setCenter(new GLatLng(-25.265189, 151.761274),14);
            mmap.addControl(new GLargeMapControl3D());
            mmap.addControl(new GMapTypeControl());
            mmap.addControl(new GScaleControl());
            mmap.addMapType(G_PHYSICAL_MAP);
            mmap.setMapType(G_SATELLITE_MAP);
            mmap.enableScrollWheelZoom();
            mmap.enableDoubleClickZoom();
            mmap.enableContinuousZoom();
            gml = new GeoXml("gml", mmap, "${kmlPath}", {sidebarid:"the_side_bar",iwwidth:250, domouseover:true, messagebox:document.getElementById('info_area') });
            gml.parse();
        }
    //]]>
</script> 
</head> 
<body> 
    <div id="map_container">
        <div id="map_canvas"></div>
        <div id="the_side_bar"></div>
        <div id="info_area"></div>
    </div>
</body> 