package com.tiffany.webapp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tiffany.Constants;
import com.tiffany.model.Sample;

import org.springframework.validation.BindException;
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

    public BulkUploadController() {
        setCommandName("fileUpload");
        setCommandClass(FileUpload.class);
    }
    
    public ModelAndView processFormSubmission(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Object command,
                                              BindException errors)
    throws Exception {
        if (request.getParameter("cancel") != null) {
            return new ModelAndView(getCancelView());
        }

        return super.processFormSubmission(request, response, command, errors);
    }

    public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response, Object command,
                                 BindException errors)
    throws Exception {
        FileUpload fileUpload = (FileUpload) command;

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
        File csv = new File(csvPath);
        BufferedReader text = new BufferedReader(new FileReader(csv));
        StringBuffer result = new StringBuffer();
        String line;
        while ((line = text.readLine()) != null) {
        	result.append("<strong>" + line + "</strong><br/>");
        }
        text.close();
        csv.delete();
        //===================================================
        // place the data into the request for retrieval on next page
        request.setAttribute("message", result.toString());

        return new ModelAndView(getSuccessView());
    }
}
