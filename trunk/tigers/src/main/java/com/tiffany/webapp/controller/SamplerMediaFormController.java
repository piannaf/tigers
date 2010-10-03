package com.tiffany.webapp.controller;

import java.io.File;
import java.lang.String;
import org.apache.commons.lang.StringUtils;

import com.tiffany.service.GenericManager;
import com.tiffany.model.SamplerMedia;
import com.tiffany.webapp.controller.BaseFormController;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
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
			// TODO: 10 file limit?
			MultipartHttpServletRequest multipartRequest;
			CommonsMultipartFile file = null;
			String fileName;
			File newFile = null; // shuddup Java compiler about possibly not being initialised
			if(isNew) {
				// handle upload
				multipartRequest = (MultipartHttpServletRequest) request;
				file = (CommonsMultipartFile) multipartRequest.getFile("file");
				if(file == null) {
					throw new Exception("No file received.");
				}
				
				// dummy value
				samplerMedia.setFileName(file.getOriginalFilename());
			}
            samplerMedia = samplerMediaManager.save(samplerMedia);
            String key = (isNew) ? "samplerMedia.added" : "samplerMedia.updated";
            saveMessage(request, getText(key, samplerMedia.getId().toString(), locale));
			
			if(isNew) {
				String fileNameBase = getServletContext().getRealPath("/samplermedia") + "/" + samplerMedia.getId() + "_";
				String fileNameWc;
				int p;
				fileNameWc = file.getOriginalFilename().replace("*", "");
				p = fileNameWc.lastIndexOf(".");
				if(p > 0) {
					fileNameWc = fileNameWc.substring(0, p) + "*" + fileNameWc.substring(p);
				} else {
					fileNameWc += "*";
				}
				fileName = file.getOriginalFilename();
				newFile = new File(fileNameBase + fileName);
				// avoid overwrite (this is somewhat redundant now that there's an ID prefix on files)
				int i=0;
				while(newFile.exists()) {
					fileName = fileNameWc.replace("*", " (" + String.valueOf(++i) + ")");
					newFile = new File(fileNameBase + fileName);
				}
				
				//String tag = request.getParameter("tag");
				//if(tag == null) {
				//	throw new Exception("Tag not specified");
				//}
				//samplerMedia.setTag(tag);
				file.transferTo(newFile);
				samplerMedia.setFileName(fileName);
				samplerMediaManager.save(samplerMedia);
			}

            /* if (!isNew) {
                success = "redirect:samples.html?id=" + samplerMedia.getId();
            } else {
            	success = "redirect:samples.html?id=" + samplerMedia.getId();
            } */
        }

        return new ModelAndView(success);
    }
}
