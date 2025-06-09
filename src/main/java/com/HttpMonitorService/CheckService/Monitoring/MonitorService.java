package com.HttpMonitorService.CheckService.Monitoring;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.HttpMonitorService.CheckService.Rabbit.RabbitMQConfig;

import jakarta.annotation.PostConstruct;

@Service
public class MonitorService {

	@Autowired
	FileContent file_data;
	
	@Autowired
	RabbitTemplate rabbit;
	

	private List<String> lines;

	@PostConstruct  //  after file-data
	public void init() {
	    lines = file_data.getcontent();
	}
	private final RestTemplate rest_template=new RestTemplate();
	
	
	private ResponseEntity<String> response;
	
    private final Map<String, Integer> failureCounts = new ConcurrentHashMap<>();
    
    
    
    

	
	
	
    @Scheduled(fixedRate = 10000)
    private void Send_URL() {
        List<String> lines = file_data.getcontent(); 

        for (String url : lines) {
            boolean isUp = URLCheck(url);
            if (isUp) {
                failureCounts.remove(url);
            } else {
                int count = failureCounts.getOrDefault(url, 0) + 1;
                if (count >= 5) {
                	
                	response=rest_template.getForEntity(url, String.class);
                    sendToRabbitMQ(url+ "  status: " +response.getStatusCode());
                    failureCounts.remove(url);
                } else {
                    failureCounts.put(url, count);
                }
            }
        }
    }

	
	
	
	
    public boolean URLCheck(String url) {
    	
        try {
            response = rest_template.getForEntity(url, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch ( Exception ex) {
            System.out.println("HTTP error for URL : " + url);
            return false;
        }
    }

	
    private void sendToRabbitMQ(String message) {
        rabbit.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, message);
        System.out.println("Sent : " + message);
    }
}
	
	
	
	
	
	
	
	
	

