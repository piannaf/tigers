package com.tiffany.webapp.controller;

import java.io.File;
import org.apache.commons.lang.StringUtils;

import com.tiffany.service.GenericManager;
import com.tiffany.model.SamplerMedia;
import com.tiffany.webapp.controller.BaseFormController;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class SamplerMediaFormController extends BaseFormController {
    private GenericManager<SamplerMedia, Long> samplerMediaManager = null;

    public void setSamplerMediaManager(GenericManager<SamplerMedia, Long> samplerMediaManager) {
        this.samplerMediaManager = samplerMediaManager;
    }

    public SamplerMediaFormController() {
        setCommandClass(SamplerMedia.class);
        setCommandName("samplerMedia");
    }

    protected Object formBackingObject(HttpServletRequest request)
    throws Exception {
        String id = request.getParameter("id");

        if (!StringUtils.isBlank(id)) {
            return samplerMediaManager.get(new Long(id));
        }
        
        return new SamplerMedia();
    }

    public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response, Object command,
                                 BindException errors)
    throws Exception {

        SamplerMedia samplerMedia = (SamplerMedia) command;
        boolean isNew = (samplerMedia.getId() == null);
        String success = getSuccessView();
        Locale locale = request.getLocale();
        
		success = "redirect:samplermedia.html?tag=" + samplerMedia.getTag();
		
        if (request.getParameter("delete") != null) {
			String fileName = getServletContext().getRealPath("/samplermedia") + "/" + samplerMedia.getId() + "_" + samplerMedia.getFileName();
            samplerMediaManager.remove(samplerMedia.getId());
			try {
				(new File(fileName)).delete();
			} catch(Exception e) {};
            saveMessage(request, getText("samplerMedia.deleted", locale));
        } else {
			if(isNew) {
				// handle upload
				
			}
            samplerMedia = samplerMediaManager.save(samplerMedia);
            String key = (isNew) ? "samplerMedia.added" : "samplerMedia.updated";
            saveMessage(request, getText(key, samplerMedia.getId().toString(), locale));

            /* if (!isNew) {
                success = "redirect:samples.html?id=" + samplerMedia.getId();
            } else {
            	success = "redirect:samples.html?id=" + samplerMedia.getId();
            } */
        }

        return new ModelAndView(success);
    }
}
