package com.innvo.domain;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.esotericsoftware.yamlbeans.YamlReader;

public class CSVBeanWriter {
	
	private final Logger log = LoggerFactory.getLogger(CSVBeanWriter.class);
	
	public void writeCSVFile(String csvFileName, List<OutputCSVModel> listOfEvent) {
	   	 ICsvBeanWriter beanWriter = null;
	   	    CellProcessor[] processors = new CellProcessor[] {
	   	            new NotNull(), // alertId
	   	            new NotNull(), // eventId
	   	            new NotNull(), // Status
	   	            new NotNull(), // lastModifiedBy
	   	            new NotNull(), // lastModifiedDateTime
	   	            new NotNull() // domain
	   	    };
	   	 
	   	    try {
	   	    	CSVBeanWriter receiver=new CSVBeanWriter();
	   			String fullfilename =URLDecoder.decode(receiver.getClass().getResource("/config/application-dev.yml").getFile(), "UTF-8");
	   			
	   			YamlReader reader = new YamlReader(new FileReader(fullfilename));
	   			Object fileContent = reader.read();
	   			Map map = (Map) fileContent;
	   			
	   	    	String csvPath = map.get("csvpath").toString();
    	    	FileWriter fileWriter = new FileWriter(csvPath+File.separator+csvFileName);
	   	        beanWriter = new CsvBeanWriter(fileWriter,CsvPreference.STANDARD_PREFERENCE);
	   	        String[] header = {"alertId", "eventId", "Status", "lastModifiedBy", "lastModifiedDateTime","domain"};
	   	        beanWriter.writeHeader(header);
	   	   
	   	       for (OutputCSVModel event : listOfEvent) {
	   	            beanWriter.write(event, header, processors);
	   	        }
	   	    log.debug("CSV file sucessfully generated");
	   	    } catch (IOException ex) {
	   	        System.err.println("Error writing the CSV file: " + ex);
	   	    } finally {
	   	        if (beanWriter != null) {
	   	            try {
	   	                beanWriter.close();
	   	            } catch (IOException ex) {
	   	                System.err.println("Error closing the writer: " + ex);
	   	            }
	   	        }
	   	    }
	   }

}
