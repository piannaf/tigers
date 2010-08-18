package com.tiffany.webapp.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.io.HTMLWriter;
import org.dom4j.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.tiffany.model.Sampler;
import com.tiffany.service.SamplerManager;

import de.micromata.opengis.kml.v_2_2_0.*;
import de.micromata.opengis.kml.v_2_2_0.Document;

public class KmlController extends AbstractController {
    private transient final Log log = LogFactory.getLog(UserController.class);
    private SamplerManager samplerManager = null;

    public void setSamplerManager(SamplerManager samplerManager) {
	this.samplerManager = samplerManager;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	log.debug("Generating dynamic KML for " + request.getRequestURI());

	// Find or create kml resources directory
	String kmlPath = getServletContext().getRealPath("/resources/kml")
		+ "/";
	File kmlDir = new File(kmlPath);
	if (!kmlDir.exists()) {
	    kmlDir.mkdirs();
	}

	// New kml document
	final Kml kml = new Kml();
	final Document document = kml
		.createAndSetDocument()
		.withName("TiGERS.kml")
		.withDescription(
			"Location of samplers at the Tiffany Gold Mine")
		.withOpen(true);

	// Main placemark
	document.createAndAddPlacemark().withName("Tiffany Gold Mine, AU")
		.withDescription("Central Point of Tiffany Gold Mine Land")
		.withOpen(Boolean.TRUE).createAndSetPoint()
		.addToCoordinates(151.761274, -25.265189);

	// Other placemarks
	for (Sampler sampler : samplerManager.getAll()) {
	    // Generate HTML with dom4j (and assume it works)
	    StringWriter sw = new StringWriter();
	    generateDescription(sw, sampler);

	    // Generate Placemark with JAK
	    document.createAndAddPlacemark()
		    .withName(sampler.getTag())
		    .withOpen(Boolean.TRUE)
		    .withDescription(sw.toString())
		    .createAndSetPoint()
		    .addToCoordinates(sampler.getLongitude().doubleValue(),
			    sampler.getLatitude().doubleValue());
	}

	// Write kml to response
	PrintWriter out = response.getWriter();
	kml.marshal(out);
	log.debug("Kml successfully sent to client");

	return null;
    }

    private Boolean generateDescription(StringWriter sw, Sampler sampler) {
	HTMLWriter writer = new HTMLWriter(sw);
	org.dom4j.Document desc = DocumentHelper.createDocument();
	Element descRoot = desc.addElement("div");
	descRoot.addAttribute("id", "description");
	    
	// Description header is sampler's unique Tag
	descRoot.addElement("h1").addText(sampler.getTag());
	  
	// Sampler parameters as definition list
	Element params = descRoot.addElement("dl");
	for (Map.Entry<String, Object> entry : 
	    	sampler.list_all_params().entrySet()) {
	    if(!entry.getValue().toString().isEmpty()) {
		params.addElement("dt").addText(entry.getKey());
		params.addElement("dd").addText(entry.getValue().toString());
	    }
	}
	    
	    
	try {
    	    writer.write(desc);
    	    writer.flush();
    	    return true;
	} catch (IOException e) {
	    log.debug(e);
	    return false;
	}
    }
}
