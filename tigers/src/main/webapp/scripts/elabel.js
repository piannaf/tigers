// Version 0.2      the .copy() parameters were wrong
// version 1.0      added .show() .hide() .setContents() .setPoint() .setOpacity() .overlap



      function ELabel(point, html, classname, pixelOffset, percentOpacity, overlap) {
        // Mandatory parameters
        this.point = point;
        this.html = html;
        
        // Optional parameters
        this.classname = classname||"";
        this.pixelOffset = pixelOffset||new GSize(0,0);
        if (percentOpacity) {
          if(percentOpacity<0){percentOpacity=0;}
          if(percentOpacity>100){percentOpacity=100;}
        }        
        this.percentOpacity = percentOpacity;
        this.overlap=overlap||false;
      } 
      
      ELabel.prototype = new GOverlay();

      ELabel.prototype.initialize = function(map) {
        var div = document.createElement("div");
        div.style.position = "absolute";
        div.innerHTML = '<div class="' + this.classname + '">' + this.html + '</div>' ;
        map.getPane(G_MAP_FLOAT_SHADOW_PANE).appendChild(div);
        this.map_ = map;
        this.div_ = div;
        if (this.percentOpacity) {        
          if(typeof(div.style.filter)=='string'){div.style.filter='alpha(opacity:'+this.percentOpacity+')';}
          if(typeof(div.style.KHTMLOpacity)=='string'){div.style.KHTMLOpacity=this.percentOpacity/100;}
          if(typeof(div.style.MozOpacity)=='string'){div.style.MozOpacity=this.percentOpacity/100;}
          if(typeof(div.style.opacity)=='string'){div.style.opacity=this.percentOpacity/100;}
        }
        if (this.overlap) {
          var z = GOverlay.getZIndex(this.point.lat());
          this.div_.style.zIndex = z;
        }
      }

      ELabel.prototype.remove = function() {
        this.div_.parentNode.removeChild(this.div_);
      }

      ELabel.prototype.copy = function() {
        return new ELabel(this.point, this.html, this.classname, this.pixelOffset, this.percentOpacity, this.overlap);
      }

      ELabel.prototype.redraw = function(force) {
        var p = this.map_.fromLatLngToDivPixel(this.point);
        var h = parseInt(this.div_.clientHeight);
        this.div_.style.left = (p.x + this.pixelOffset.width) + "px";
        this.div_.style.top = (p.y +this.pixelOffset.height - h) + "px";
      }

      ELabel.prototype.show = function() {
        this.div_.style.display="";
      }
      
      ELabel.prototype.hide = function() {
        this.div_.style.display="none";
      }
      
      ELabel.prototype.setContents = function(html) {
        this.html = html;
        this.div_.innerHTML = '<div class="' + this.classname + '">' + this.html + '</div>' ;
        this.redraw(true);
      }
      
      ELabel.prototype.setPoint = function(point) {
        this.point = point;
        if (this.overlap) {
          var z = GOverlay.getZIndex(this.point.lat());
          this.div_.style.zIndex = z;
        }
        this.redraw(true);
      }
      
      ELabel.prototype.setOpacity = function(percentOpacity) {
        if (percentOpacity) {
          if(percentOpacity<0){percentOpacity=0;}
          if(percentOpacity>100){percentOpacity=100;}
        }        
        this.percentOpacity = percentOpacity;
        if (this.percentOpacity) {        
          if(typeof(this.div_.style.filter)=='string'){this.div_.style.filter='alpha(opacity:'+this.percentOpacity+')';}
          if(typeof(this.div_.style.KHTMLOpacity)=='string'){this.div_.style.KHTMLOpacity=this.percentOpacity/100;}
          if(typeof(this.div_.style.MozOpacity)=='string'){this.div_.style.MozOpacity=this.percentOpacity/100;}
          if(typeof(this.div_.style.opacity)=='string'){this.div_.style.opacity=this.percentOpacity/100;}
        }
      }


        