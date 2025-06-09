package com.HttpMonitorService.CheckService.Monitoring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class FileContent {
	
	public List<String>   getcontent() {
	
		
		List<String> list_urls=new ArrayList<String>();
		
	try {
		
		 // ClassPathResource resource = new ClassPathResource("MonitorUrls.txt");
          //File file = resource.getFile();
         // String config_path = file.getAbsolutePath();
   String config_path=new File(".").getCanonicalPath()+ File.separator +"MonitorUrls.txt";
   
   try (BufferedReader br = new BufferedReader(new FileReader(config_path))) {
       String line;
       while ((line = br.readLine()) != null) {
           line = line.trim();

    	   list_urls.add(line);
    	   System.out.println(line);
    	   
       }
       
       return list_urls;
       
       
       
   } catch (IOException e) {
	   System.out.println("failed to fetch from file");
	   return null;
   }
   
   
   
   
   
	
	}
	catch(Exception c) {
		   System.out.println("failed to fetch from file");

		return null;
		
	}
	
	}
}
