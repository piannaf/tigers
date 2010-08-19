package com.tiffany.webapp.controller;

import java.io.*;

import java.util.*;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tiffany.Constants;
import com.tiffany.model.Sample;
import com.tiffany.model.User;
import com.tiffany.service.SamplerManager;
import com.tiffany.service.UserManager;
import com.tiffany.service.WaterbodyManager;
import com.tiffany.service.BulkUploadService;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller class to upload Files.
 *
 * <p>
 * <a href="FileUploadFormController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BulkUploadController extends BaseFormController {
	private SamplerManager samplerManager;
	private WaterbodyManager waterbodyManager;
	private BulkUploadService bulkUploadService;
	private UserManager userManager;
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	public void setWaterbodyManager(WaterbodyManager waterbodyManager) {
		this.waterbodyManager = waterbodyManager;
	}
	public void setSamplerManager(SamplerManager samplerManager) {
		this.samplerManager = samplerManager;
	}	
	public void setBulkUploadService(BulkUploadService bulkUploadService) {
		this.bulkUploadService = bulkUploadService;
	}
    public BulkUploadController() {
        setCommandName("fileUpload");
        setCommandClass(FileUpload.class);
    }
    //===========================================================================
    public ModelAndView processFormSubmission(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Object command,
                                              BindException errors)
    throws Exception {
    	log.debug("processFormSubmission");
        if (request.getParameter("cancel") != null) {
            return new ModelAndView(getCancelView());
        }

        return super.processFormSubmission(request, response, command, errors);
    }
    
    //===========================================================================
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
		log.debug("entering \"referenceData\" method in BulkUploadController...");
		Locale locale = request.getLocale();
		Map<String, Object> refData = new HashMap();
		//List<String> samplerIdList = new ArrayList<String>();
		User remoteUser = userManager.getUserByUsername((request.getRemoteUser()));
		List<String> samplerIdList = samplerManager.getTagListForLaboratory(remoteUser);
		refData.put("samplerIdList", samplerIdList);
		if (samplerIdList.size() == 0)	saveMessage(request, getText("bulkUpload.form.noTag", locale));
		return refData;
	}
    
  //===========================================================================
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response,
            BindException errors) throws Exception {
    	log.debug("showForm");


    	return super.showForm(request, response, errors);
	}
    //==================================================================
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
    			BindException errors) throws Exception {
        FileUpload fileUpload = (FileUpload) command;
        Locale locale = request.getLocale();
        // validate a file was entered
        if (fileUpload.getFile().length == 0) {
            Object[] args = 
                new Object[] { getText("uploadForm.file", request.getLocale()) };
            errors.rejectValue("file", "errors.required", args, "File");
            
            return showForm(request, response, errors);
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");

        // the directory to upload to
        String uploadDir = getServletContext().getRealPath("/resources") + "/bulk_upload/";

        // Create the directory if it doesn't exist
        File dirPath = new File(uploadDir);

        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        //retrieve the file data
        InputStream stream = file.getInputStream();
        
        String csvPath = uploadDir + file.getOriginalFilename();
        //write the file to the file specified
        OutputStream bos = new FileOutputStream(csvPath);
        int bytesRead;
        byte[] buffer = new byte[8192];

        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }

        bos.close();
        
        //close the stream
        stream.close();
        
        //===================================================
        String samplerId = fileUpload.getName();
        String type = samplerManager.getWaterBodyTypeByTag(samplerId);  
        log.debug("watebody type: " + type);
        File csv = new File(csvPath);
        BufferedReader text = new BufferedReader(new FileReader(csv));
        String line;
        // empty file
        if ((line = text.readLine()) == null) {
        	saveError(request, getText("errors.bulkUpload.empty", locale));
        	text.close();
            csv.delete();
        	return showForm(request, response, errors);
        } 
        // invalid header
        if (!bulkUploadService.initService(type, line, samplerId, request.getRemoteUser())) {
        	if (type.equals("G")) type = "Ground Water";
        	else type = "Surface Water";
        	saveError(request, getText("errors.bulkUpload.header", type, locale));
        	text.close();
            csv.delete();
        	return showForm(request, response, errors);
        }
        //======================
        log.debug("\n==================== header test =======================");
        Set<Integer> keys = bulkUploadService.fieldsMap.keySet();
        Iterator it = keys.iterator();
        while (it.hasNext()) {
        	int key = (Integer)it.next();
        	log.debug("item "+key+" : "+bulkUploadService.fieldsMap.get(key));
        }
        //=======================
        int count = 1;
        List<Data> dataList = new ArrayList();
        while ((line = text.readLine()) != null) {
        	Data data = new Data();
        	data.line = ++count;
        	data.errorFields = bulkUploadService.update(line);
        	//=========
        	log.debug("\n==================== data test ===========");
        	log.debug("data : "+data.errorFields);
        	//=========
        	if (data.errorFields.equals("")) { data.complete = "Y"; }
        	else { data.complete = "Faild"; }
        	dataList.add(data);
        }
        text.close();
        csv.delete();
        // no data
        if (count == 1) {
        	saveError(request, getText("errors.bulkUpload.noData", locale));
        	return showForm(request, response, errors);
        }
        
        
        //===================================================
        // place the data into the request for retrieval on next page

        return new ModelAndView(getSuccessView()).addObject("dataList", dataList);
    }
    //============================================================================
    public class  Data {
    	public int line;
    	public String errorFields;
    	public String complete;
    	
    	public int getLine() { return line; }
    	public String getComplete() { return complete; }
    	public String getErrorFields() { return errorFields; }
    }
}
