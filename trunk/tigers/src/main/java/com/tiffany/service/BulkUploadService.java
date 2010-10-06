package com.tiffany.service;

import java.math.BigDecimal;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.tiffany.model.Sample;
import com.tiffany.model.Sampler;
import com.tiffany.model.User;

public class BulkUploadService {
	protected final transient Log log = LogFactory.getLog(getClass());
	private String type;
	private User laboratory;
	private Sampler tag;
	private List<String> fields;
	private SampleManager sampleManager;
	public Map<Integer, String> fieldsMap;
	
	public void setSampleManager(SampleManager sampleManager) {
		this.sampleManager = sampleManager;
	}
	
	public boolean initService(String type,String header, Sampler sampler, User laboratory) {
		this.type = type;
		this.laboratory = laboratory;
		this.tag = sampler;
		if (setFieldsMap(type, header) == null) { return false; }
		return true;
	}
	
	public String update(String line) {
		String result = "";
		Sample sample = new Sample();
		sample.setLaboratory(laboratory);
		sample.setSampler(tag);
		String items[] = line.split(",");
		if (items.length != fieldsMap.size()+1) { 
			return "Fields number mismatch with the header. ("+ items.length + ")"; 
		}
		
		result = setDate(sample, items[0], result);
		for (int i=1; i<items.length; i++) {
			result = setSample(sample,i, items[i], result);
		}
		// generate sample and upload it
		
		if (result.equals("")) {
			try {
				log.debug("======upload sample with:=======");
				log.debug("date:"+sample.getDate_taken());
				log.debug("ph:"+sample.getPh());
				log.debug("ec:"+sample.getEc());
				log.debug("temp:"+sample.getTemperature());
				log.debug("DTC:"+sample.getCollar_depth());
				log.debug("As:"+sample.getArsenic());
				log.debug("O&G:"+sample.getGrease());
				
				sampleManager.save(sample);
			} catch (Exception e) {
				result = "SQL Insertion Error: code-4432";
			}
		}
		
		
		return result;
	}	
	
	
	
	//===========================================================================
	private String setSample(Sample sample, int key, String field, String result) {
		String column = fieldsMap.get(key);
		field = field.trim();
		log.debug("column:"+key+"("+column+")"+" value:"+field);
		if (field.equalsIgnoreCase("NaN")) return result;
		if (column.equals("PH")) {
			result = setPh(sample, field, result);
		} else if (column.equals("EC")) {
			result = setEc(sample, field, result);
		} else if (column.equals("Temp")) {
			result = setTemp(sample, field, result);
		} else if (column.equals("As")) {
			result = setAs(sample, field, result);
		} else if (column.equals("O&G")) {
			result = setOnG(sample, field, result);
		} else if (column.equals("DTC")) {
			result = setDTC(sample, field, result);
		} else if (column.equals("Flouride")) {
			result = setFluoride(sample, field, result);
		} else if (column.equals("Chromium")) {
			result = setChromium(sample, field, result);
		} else {
			log.debug("field lost...!@#$%^");
		}
		return result;
	}
	private Map<Integer, String> setFieldsMap(String type, String header) {
		fieldsMap = new HashMap<Integer, String>();
		Set<String> fields = getFields(type);
		String items[] = header.split(",");
		
		for (int i=1; i<items.length; i++) {
			String item = items[i];
			Iterator it = fields.iterator();
			boolean gotIt = false;
			while (it.hasNext()) {
				String field = (String)it.next();
				if (item.regionMatches(true, 0, field, 0, field.length())) {
					log.debug("match: "+item);
					it.remove();
					fieldsMap.put(i, field);
					gotIt = true;
				}
			}
			if (!gotIt) { return null; }
		}
		if (fields.size() != 0) return null;
		
		return fieldsMap;
	}
	
	private Set<String> getFields(String type) {
		Set<String> fields = new HashSet<String>();
		fields.add("PH");
		fields.add("EC");
		fields.add("Temp");
		fields.add("As");
		fields.add("O&G");
		if (type.equals("G")) {
			fields.add("DTC");
		} else if (type.equals("S")) {
			fields.add("Fluoride");
			fields.add("Chromium");
		}
		return fields;
	}
	//====================================================
	private String setDate(Sample sample, String dateStr, String result) {
		if (dateStr.equals("") || dateStr == null) return result += "Date(missing)/";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = dateFormat.parse(dateStr);
		} catch (Exception e) {
			return result += "Date/";
		}
		sample.setDate_taken(date);
		return result;
	}
	private String setPh(Sample sample, String phStr, String result) {
		log.debug("setPh:"+phStr+":");
		log.debug("phStr == null? "+phStr==null);
		log.debug("phStr == empty? "+phStr.equals(""));
		if (phStr.equals("") || phStr == null) return result += "Ph(missing)/";
		int factor = 1;
		if (phStr.charAt(0) == '<') {
			factor = -1;
			phStr = phStr.substring(1);
		}
		DecimalFormat format = new DecimalFormat("##.##");
		float ph;
		try { ph = Float.parseFloat(phStr); } 
		catch (Exception e) { return result += "Ph(invalid format)/"; }
		if (ph > 14 || ph < 0) { return result += "Ph(out of range)/"; }
		ph = ph * factor;
		sample.setPh(new BigDecimal(format.format(ph)));
		return result;
	}
	private String setEc(Sample sample, String ecStr, String result) {
		if (ecStr.equals("") || ecStr == null) return result += "EC(missing)/";
		int factor = 1;
		DecimalFormat format = new DecimalFormat("#####");
		if (ecStr.charAt(0) == '<') {
			factor = -1;
			ecStr = ecStr.substring(1);
		}
		float ec;
		try { ec = Float.parseFloat(ecStr); } 
		catch (Exception e) { return result += "EC(invalid format)/"; }
		if (ec > 1000 || ec < 0) { return result += "EC(out of range)/"; }
		ec = ec * factor;
		sample.setEc(new BigDecimal(format.format(ec)));
		return result;
	}
	private String setTemp(Sample sample, String tempStr, String result) {
		if (tempStr.equals("") || tempStr == null) return result += "Temp(missing)/";
		int factor = 1;
		if (tempStr.charAt(0) == '<') {
			factor = -1;
			tempStr = tempStr.substring(1);
		}
		DecimalFormat format = new DecimalFormat("###.##");
		float temp;
		try { temp = Float.parseFloat(tempStr); } 
		catch (Exception e) { return result += "Temp(invalid format)/"; }
		if (temp > 50 || temp < 0) { return result += "Temp(out of range)/"; }
		temp = temp * factor;
		sample.setTemperature(new BigDecimal(format.format(temp)));
		return result;
	}
	private String setAs(Sample sample, String asStr, String result) {
		if (asStr.equals("") || asStr == null) return result += "As(missing)/";
		int factor = 1;
		if (asStr.charAt(0) == '<') {
			factor = -1;
			asStr = asStr.substring(1);
		}
		DecimalFormat format = new DecimalFormat("##.###");
		float as;
		try { as = Float.parseFloat(asStr); } 
		catch (Exception e) { return result += "As(invalid format)/"; }
		if (as > 0.02 || as < 0) { return result += "As(out of range)/"; }
		as = as * factor;
		sample.setArsenic(new BigDecimal(format.format(as)));
		return result;
	}
	private String setOnG(Sample sample, String ongStr, String result) {
		if (ongStr.equals("") || ongStr == null) return result += "O&G(missing)/";
		int factor = 1;
		if (ongStr.charAt(0) == '<') {
			factor = -1;
			ongStr = ongStr.substring(1);
		}
		DecimalFormat format = new DecimalFormat("###");
		float ong;
		try { ong = Float.parseFloat(ongStr); } 
		catch (Exception e) { return result += "O&G(invalid format)/"; }
		if (ong > 15 || ong < 0) { return result += "O&G(out of range)/"; }
		ong = ong * factor;
		sample.setGrease(new BigDecimal(format.format(ong)));
		return result;
	}
	private String setDTC(Sample sample, String dtcStr, String result) {
		if (dtcStr.equals("") || dtcStr == null) return result += "DTC(missing)/";
		int factor = 1;
		if (dtcStr.charAt(0) == '<') {
			factor = -1;
			dtcStr = dtcStr.substring(1);
		}
		DecimalFormat format = new DecimalFormat("#####");
		float dtc;
		try { dtc = Float.parseFloat(dtcStr); } 
		catch (Exception e) { return result += "DTC(invalid format)/"; }
		if (dtc > 300 || dtc < 100) { return result += "DTC(out of range)/"; }
		dtc = dtc * factor;
		sample.setCollar_depth(new BigDecimal(format.format(dtc)));
		return result;
	}
	private String setFluoride(Sample sample, String fluorideStr, String result) {
		if (fluorideStr.equals("") || fluorideStr == null) return result += "Fluoride(missing)/";
		int factor = 1;
		if (fluorideStr.charAt(0) == '<') {
			factor = -1;
			fluorideStr = fluorideStr.substring(1);
		}
		DecimalFormat format = new DecimalFormat("##.###");
		float fluoride;
		try { fluoride = Float.parseFloat(fluorideStr); } 
		catch (Exception e) { return result += "Fluoride(invalid format)/"; }
		if (fluoride > 300 || fluoride < 100) { return result += "Fluoride(out of range)/"; }
		fluoride = fluoride * factor;
		sample.setFluoride(new BigDecimal(format.format(fluoride)));
		return result;
	}
	private String setChromium(Sample sample, String chromiumStr, String result) {
		if (chromiumStr.equals("") || chromiumStr == null) return result += "Chromium(missing)/";
		int factor = 1;
		if (chromiumStr.charAt(0) == '<') {
			factor = -1;
			chromiumStr = chromiumStr.substring(1);
		}
		DecimalFormat format = new DecimalFormat("##.###");
		float chromium;
		try { chromium = Float.parseFloat(chromiumStr); } 
		catch (Exception e) { return result += "Chromium(invalid format)/"; }
		if (chromium > 300 || chromium < 100) { return result += "Chromium(out of range)/"; }
		chromium = chromium * factor;
		sample.setCollar_depth(new BigDecimal(format.format(chromium)));
		return result;
	}
}
