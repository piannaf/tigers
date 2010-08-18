package com.tiffany.webapp.controller;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.tiffany.model.Sampler;
import com.tiffany.service.UserManager;
import com.tiffany.service.SamplerManager;
import com.tiffany.service.WaterbodyManager;
/**
 * This class controls the "sampler" maintenance form
 *
 * @author Jane
 */
public class SamplerFormController extends BaseFormController {
	private SamplerManager samplerManager = null;
	private WaterbodyManager waterbodyManager;
	private UserManager userManager;

    public void setSamplerManager(SamplerManager samplerManager) {
        this.samplerManager = samplerManager;
    }
    public void setWaterbodyManager(WaterbodyManager waterbodyManager) {
        this.waterbodyManager = waterbodyManager;
    }
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public SamplerFormController() {   
        setCommandClass(Sampler.class);
        setCommandName("sampler");
        // This stops the creation of a new command object each time the screen is refreshed
        setSessionForm(true);
    }

    protected Object formBackingObject(HttpServletRequest request)
    throws Exception {
        String tag = request.getParameter("tag");
       
        if (!StringUtils.isBlank(tag)) {
            return samplerManager.findOneByTag(tag);
        }
        return new Sampler();
    }
    @Override
    protected Map referenceData(HttpServletRequest request, Object command,
            Errors errors) throws Exception {
    	
    	Map referenceData = new HashMap();
        referenceData.put("waterbodyList", waterbodyManager.getAll());
    	referenceData.put("contractorList", userManager.getContractors());
    	Sampler sampler = (Sampler) command;
    	log.debug(sampler.toString());
    	
    	return referenceData;
    }

    public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response, Object command,
                                 BindException errors)
    throws Exception {

        Sampler sampler = (Sampler) command;
                
        boolean isNew = (sampler.getId() == null);
        boolean isValid;
        
        String success = getSuccessView();
        Locale locale = request.getLocale();
        
        isValid = validateGroundWaterFields(sampler, errors);
        
        if (isValid) {
        
        	if (request.getParameter("delete") != null) {
        		samplerManager.remove(sampler.getId());
        		saveMessage(request, getText("sampler.deleted", locale));
        	} else {
        		sampler = samplerManager.save(sampler);
        		String key = (isNew) ? "sampler.added" : "sampler.updated";
        		saveMessage(request, getText(key, sampler.getTag(), locale));

        		//    success = "redirect:samplerform.html?tag=" + sampler.getTag();
        	}
        }
        else 
        	return showForm(request, errors, getFormView());

        return new ModelAndView(success);
    }
	private boolean validateGroundWaterFields(Sampler sampler, BindException errors) {
		boolean okeydokey = true;
		String license = sampler.getLicense();
		
		BigDecimal collarHeight = sampler.getCollar_height();
		String depthToCollarScreeningFreq = sampler.getDepth_to_collar_screening_freq();
		char waterbodyType = sampler.getWaterbody().getType();
		log.debug(sampler.getWaterbody().toString());

		if (waterbodyType == 'G') {
			if (license.equals("")) {
				okeydokey = false;
				errors.rejectValue("license", "errors.groundWaterRequired", new Object[] {license}, null);	
			}
			if (depthToCollarScreeningFreq.equals("Select")) {
				okeydokey = false;
				errors.rejectValue("depth_to_collar_screening_freq", "errors.groundWaterRequired", new Object[] {depthToCollarScreeningFreq}, null);	
			}
			if (collarHeight == null || collarHeight.compareTo(new BigDecimal(0)) == 0) {
				okeydokey = false;
				errors.rejectValue("collar_height", "errors.groundWaterRequired", new Object[] {collarHeight}, null);	
			}
			else if (collarHeight.scale() > 0) {
				okeydokey = false;
				errors.rejectValue("collar_height", "errors.trueInteger", new Object[] {collarHeight}, null);	
			}
		}
		else {
			if (!license.equals("")) {
				okeydokey = false;
				errors.rejectValue("license", "errors.surfaceWaterNotRequired", new Object[] {license}, null);	
			}
			if (!depthToCollarScreeningFreq.equals("Select")) {
				okeydokey = false;
				errors.rejectValue("depth_to_collar_screening_freq", "errors.surfaceWaterNotRequired", new Object[] {depthToCollarScreeningFreq}, null);	
			}
			if (!(collarHeight == null)) {
				okeydokey = false;
				errors.rejectValue("collar_height", "errors.surfaceWaterNotRequired", new Object[] {collarHeight}, null);	
			}
		}

		return okeydokey;
	}
}