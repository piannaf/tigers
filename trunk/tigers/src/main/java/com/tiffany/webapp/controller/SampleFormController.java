package com.tiffany.webapp.controller;

import org.apache.commons.lang.StringUtils;

import com.tiffany.service.GenericManager;
import com.tiffany.model.Sample;
import com.tiffany.webapp.controller.BaseFormController;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class SampleFormController extends BaseFormController {
    private GenericManager<Sample, Long> sampleManager = null;

    public void setSampleManager(GenericManager<Sample, Long> sampleManager) {
        this.sampleManager = sampleManager;
    }

    public SampleFormController() {
        setCommandClass(Sample.class);
        setCommandName("sample");
    }

    protected Object formBackingObject(HttpServletRequest request)
    throws Exception {
        String id = request.getParameter("id");

        if (!StringUtils.isBlank(id)) {
            return sampleManager.get(new Long(id));
        }
        
        return new Sample();
    }

    public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response, Object command,
                                 BindException errors)
    throws Exception {
        log.debug("entering 'onSubmit' method...");

        Sample sample = (Sample) command;
        boolean isNew = (sample.getId() == null);
        String success = getSuccessView();
        Locale locale = request.getLocale();
        
        if (request.getParameter("delete") != null) {
            sampleManager.remove(sample.getId());
            saveMessage(request, getText("sample.deleted", locale));
        } else {
            sampleManager.save(sample);
            String key = (isNew) ? "sample.added" : "sample.updated";
            saveMessage(request, getText(key, locale));

            if (!isNew) {
                success = "redirect:sampleform.html?id=" + sample.getId();
            }
        }

        return new ModelAndView(success);
    }
}
