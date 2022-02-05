package com.tests.junits;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import com.main.handler.EventHandler;

@RunWith(MockitoJUnitRunner.class)
public class ValidateEventHandler {
	
	Logger log = Logger.getLogger(ValidateEventHandler.class.getName());
	public String path = System.getProperty("user.dir") + "\\src\\test\\resources\\com\\test\\resources\\logFileForUnitTest.txt";
	Map<Object, Map<Object,Object>> expectedTestMap = new LinkedHashMap<>();
	
    @Test
    public void validateDurationOfEvent() throws Throwable{
    	log.info("Test validateDurationOfEvent() Starting...");
    	expectedTestMap = EventHandler.getEventDuration(path);
    	long expectedDuration =5;
    	Assert.assertEquals(expectedTestMap.get("scsmbstgra").get("timestamp"), expectedDuration);
    	log.info("Test validateDurationOfEvent() Passed"); 
    	expectedTestMap.clear();
    }
    
    @Test
    public void validateHost() throws Throwable{
    	log.info("Test validateHost() Starting...");
    	expectedTestMap = EventHandler.getEventDuration(path);
    	String expectedHost ="12345";
    	Assert.assertEquals(expectedTestMap.get("scsmbstgra").get("host"), expectedHost);
    	log.info("Test validateHost() Passed"); 
    	expectedTestMap.clear();
    }
    
    @Test
    public void validateLogType() throws Throwable{
    	log.info("Test validateLogType() Starting...");
    	expectedTestMap = EventHandler.getEventDuration(path);
    	String expectedLogType ="APPLICATION_LOG";
    	Assert.assertEquals(expectedTestMap.get("scsmbstgra").get("type"), expectedLogType);
    	log.info("Test validateLogType() Passed"); 
    	expectedTestMap.clear();
    }
    
    @Test
    public void validateAlertStatus() throws Throwable{
    	log.info("Test validateAlertStatus() Starting..."); 
    	expectedTestMap = EventHandler.getEventStats(path);
    	String expectedAlertStatus ="True";
    	Assert.assertEquals(expectedTestMap.get("scsmbstgra").get("Alert"), expectedAlertStatus);
    	log.info("Test validateAlertStatus() Passed"); 
    	expectedTestMap.clear();
    }
}
