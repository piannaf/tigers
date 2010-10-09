<%@ include file="/common/taglibs.jsp"%>
<c:set var="title_key" value="samplerList.title"/>
<c:set var="page_description" value="Select a sampler to view its associated media files."/>
<c:set var="target_url" value="samplermedia.html"/>

<head>
<title><fmt:message key="${title_key}"/></title>
<meta name="menu" content="CourseMenu"/>
<link href="/styles/autocomplete.css" media="screen" rel="stylesheet" type="text/css" />
<style type="text/css"> 
    #map_container {
        height: 50em;
        width: 75em;
        margin-left: -5em;
        
        border: 1px solid black;
    }

    #map_canvas {
        height: 50em;
        width: 50em;
        float: left;
    }

    #the_side_bar, #info_area {
        height: 25em;
        width: 25em;
        float: left;
        
        overflow:auto;
        white-space:nowrap;
    }
    
    #the_side_bar li { list-style: none; margin:0px;padding:0px }
    #the_side_bar ul { margin-left:1em; padding:.5em; }
    
    /* indent contents of folders */
    #the_side_bar ul div {
        padding-left: 1em;
    }
    
    #info_area h1 {
        display: none;
    }
    
    #description {
        padding: 1em;
    }
    
    #description h1 {
        display: inline;
        font-size: 1.5em;
        border-bottom: 1px solid #4F8CC9;
    }
    
    #description dl dt {
        margin: 0;
        padding: 0;
        font-weight: bold;
    }
    
    #description dl {
        padding-top: 1em;
    }
    
    #description dl dd {
        margin: 0, 0, 1em, 1em;
        padding 0:  
    }
        
    #test {
        display: none;
    }
    
    .labelstyle {
        background-color:#ffffff;
        font-weight:bold;
    }
</style> 

<script type="text/javascript" src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAAxKNSmreHpX8AESttKh0VwBTwM0brOpm-All5BF6PoaKBxRWWERS8jM1JywDsPc13gAgPMsMvUVJrZw"></script> 
<script type="text/javascript" src="<c:url value='/scripts/geoxml.js'/>"></script>
<script type="text/javascript">
        window.onload = initialize;
        var gml, mmap;
        function initialize() {    
            mmap=new GMap2(document.getElementById("map_canvas"),{draggableCursor: 'crosshair', draggingCursor: 'move'});
            mmap.setCenter(new GLatLng(-25.265189, 151.761274),14); // This is a fallback in case the KML file doesn't render
            mmap.addControl(new GLargeMapControl3D());
            mmap.addControl(new GMapTypeControl());
            mmap.addControl(new GScaleControl());
            mmap.addControl(new RefreshKmlControl());   // Custom control to refresh markers/reload KML from server

            
            mmap.addMapType(G_PHYSICAL_MAP);
            mmap.setMapType(G_SATELLITE_MAP);
            mmap.enableScrollWheelZoom();
            mmap.enableDoubleClickZoom();
            mmap.enableContinuousZoom();
            
            gml = new GeoXml("gml", mmap, "<fmt:message key="map.kml"/>", // where the maps is coming from and where it is going
                {   //GeoXML options
                    sidebarid:"the_side_bar", // Let GeoXML generate a nice sidebar
                    domouseover:true, // Allow messagebox to be updated on mouseover of markers and sidebar items
                    messagebox:document.getElementById('info_area'),    // Let GeoXML populate a messagebox with descriptions
                    clickablemarkers:false  // Suppress GeoXML infowindows
                });
                
            // Go to exceedance report
            GEvent.addListener(mmap, 'click', function(marker) {
                if(marker && marker.getIcon) {    // check whether it's really a marker
                        var html = "samplermedia.html?tag=" + marker.title;
                        document.location = html
                }
            });
            
            refreshKml();
        }
        
        function refreshKml() {
            gml.clear();
            gml.parse();
        }
        
        ////////////////////////////////
        // Custom Control to refresh KML
        
        // We define the function first
        function RefreshKmlControl() {
        }

        // To "subclass" the GControl, we set the prototype object to
        // an instance of the GControl object
        RefreshKmlControl.prototype = new GControl();

        // Creates a one DIV for each of the buttons and places them in a container
        // DIV which is returned as our control element. We add the control to
        // to the map container and return the element for the map class to
        // position properly.
        RefreshKmlControl.prototype.initialize = function(map) {
          var container = document.createElement("div");

          var refreshKmlDiv = document.createElement("div");
          this.setButtonStyle_(refreshKmlDiv);
          container.appendChild(refreshKmlDiv);
          refreshKmlDiv.appendChild(document.createTextNode("Refresh Markers"));
          GEvent.addDomListener(refreshKmlDiv, "click", function() {
            refreshKml();
          });

          mmap.getContainer().appendChild(container);
          return container;
        }

        // By default, the control will appear in the top left corner of the
        // map with 7 pixels of padding.
        RefreshKmlControl.prototype.getDefaultPosition = function() {
          return new GControlPosition(G_ANCHOR_BOTTOM_RIGHT, new GSize(7, 7));
        }

        // Sets the proper CSS for the given button element.
        RefreshKmlControl.prototype.setButtonStyle_ = function(button) {
          button.style.textDecoration = "underline";
          button.style.color = "#0000cc";
          button.style.backgroundColor = "white";
          button.style.font = "small Arial";
          button.style.border = "1px solid black";
          button.style.padding = "2px";
          button.style.marginBottom = "3px";
          button.style.textAlign = "center";
          button.style.width = "6em";
          button.style.cursor = "pointer";
        }

        
        /////////////////////////
        // Marker Infowindow form
        var markerInfoWindowForm;
        
        //////////////////////////////
</script> 
</head>
<div id="breadcrumb"></div>
<h2><fmt:message key="${title_key}"/></h2>
<p><c:out value="${page_description}" escapeXml="false"/></p>

    <div id="map_container">
        <div id="map_canvas"></div>
        <div id="the_side_bar"></div>
        <div id="info_area"></div>
    </div>

