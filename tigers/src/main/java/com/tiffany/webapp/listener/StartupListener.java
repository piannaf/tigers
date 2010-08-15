package com.tiffany.webapp.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tiffany.Constants;
import com.tiffany.model.Sampler;
import com.tiffany.service.LookupManager;
import com.tiffany.service.SamplerManager;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.providers.ProviderManager;
import org.springframework.security.providers.dao.DaoAuthenticationProvider;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.providers.rememberme.RememberMeAuthenticationProvider;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.micromata.opengis.kml.v_2_2_0.*;

/**
 * <p>StartupListener class used to initialize and database settings
 * and populate any application-wide drop-downs.
 * <p/>
 * <p>Keep in mind that this listener is executed outside of OpenSessionInViewFilter,
 * so if you're using Hibernate you'll have to explicitly initialize all loaded data at the
 * GenericDao or service level to avoid LazyInitializationException. Hibernate.initialize() works
 * well for doing this.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class StartupListener implements ServletContextListener {
    private static final Log log = LogFactory.getLog(StartupListener.class);

    @SuppressWarnings({"unchecked"})
    public void contextInitialized(ServletContextEvent event) {
        log.debug("Initializing context...");

        ServletContext context = event.getServletContext();

        // Orion starts Servlets before Listeners, so check if the config
        // object already exists
        Map<String, Object> config = (HashMap<String, Object>) context.getAttribute(Constants.CONFIG);

        if (config == null) {
            config = new HashMap<String, Object>();
        }
        
        if (context.getInitParameter(Constants.CSS_THEME) != null) {
            config.put(Constants.CSS_THEME, context.getInitParameter(Constants.CSS_THEME));
        }

        ApplicationContext ctx =
            WebApplicationContextUtils.getRequiredWebApplicationContext(context);

        /*String[] beans = ctx.getBeanDefinitionNames();
        for (String bean : beans) {
            log.debug(bean);
        }*/
        
        PasswordEncoder passwordEncoder = null;
        try {
            ProviderManager provider = (ProviderManager) ctx.getBean("_authenticationManager");
            for (Object o : provider.getProviders()) {
                AuthenticationProvider p = (AuthenticationProvider) o;
                if (p instanceof RememberMeAuthenticationProvider) {
                    config.put("rememberMeEnabled", Boolean.TRUE);
                } else if (ctx.getBean("passwordEncoder") != null) {
                    passwordEncoder = (PasswordEncoder) ctx.getBean("passwordEncoder");
                }
            }
        } catch (NoSuchBeanDefinitionException n) {
            log.debug("authenticationManager bean not found, assuming test and ignoring...");
            // ignore, should only happen when testing
        }

        context.setAttribute(Constants.CONFIG, config);

        // output the retrieved values for the Init and Context Parameters
        if (log.isDebugEnabled()) {
            log.debug("Remember Me Enabled? " + config.get("rememberMeEnabled"));
            if (passwordEncoder != null) {
                log.debug("Password Encoder: " + passwordEncoder.getClass().getName());
            }
            log.debug("Populating drop-downs...");
        }

        setupContext(context);
    }

    /**
     * This method starts-up initialization of the application context:
     * 1. Uses LookupManager to lookup available roles from the data layer.
     * 2. Uses SamplerManager to lookup all samplers from the data layer.
     * 3. TODO Generates and saves a KML file with all samplers accounted for.
     * @param context The servlet context
     */
    public static void setupContext(ServletContext context) {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        LookupManager mgr = (LookupManager) ctx.getBean("lookupManager");

        // get list of possible roles
        context.setAttribute(Constants.AVAILABLE_ROLES, mgr.getAllRoles());
        log.debug("Drop-down initialization complete [OK]");
        
        //Generate KML file from list of samplers
        SamplerManager smgr = (SamplerManager) ctx.getBean("samplerManager");
        generateKml(context, smgr.getAll());
    }
    
    /**
     * This method generates a KML file from a list of samplers.
     * @param context The servlet context
     * @param samplers The list of sampler objects
     */
    public static void generateKml(ServletContext context, List<Sampler> samplers) {
	String kmlPath = context.getRealPath("/resources") + "/";
	// Create the directory if it doesn't exist
        File kmlDir = new File(kmlPath);
        if (!kmlDir.exists()) {
            kmlDir.mkdirs();
        }
	log.debug("\n\n\tCreating initial kml in: " + kmlDir);
	
	final Kml kml = new Kml();
	final Document document = kml.createAndSetDocument()
		.withName("TiGERS.kml").withDescription("Location of samplers " +
				"at the Tiffany Gold Mine").withOpen(true);
	document.createAndAddPlacemark()	// Main placemark
	   .withName("Tiffany Gold Mine, AU").withOpen(Boolean.TRUE)
	   .createAndSetPoint().addToCoordinates(151.761274, -25.265189);
	
	for(Sampler sampler : samplers) {	// All current samplers
	    document.createAndAddPlacemark()
	    	.withName(sampler.getTag()).withOpen(Boolean.TRUE)
	    	.createAndSetPoint().addToCoordinates(
	    		sampler.getLongitude().doubleValue(), 
	    		sampler.getLatitude().doubleValue());
	}
	try {
	    kmlPath = kmlPath + "/" + "TiGERS.kml";
	    kml.marshal(new File(kmlPath));
	    context.setAttribute("kmlPath", Constants.KML_PATH);
	    log.debug("\n\tKML has been created in: " + kmlPath + "\n");
	} catch (FileNotFoundException e) {
	    log.debug(e.toString());
	}
    }

    /**
     * Shutdown servlet context (currently a no-op method).
     *
     * @param servletContextEvent The servlet context event
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //LogFactory.release(Thread.currentThread().getContextClassLoader());
        //Commented out the above call to avoid warning when SLF4J in classpath.
        //WARN: The method class org.apache.commons.logging.impl.SLF4JLogFactory#release() was invoked.
        //WARN: Please see http://www.slf4j.org/codes.html for an explanation.
    }
}
