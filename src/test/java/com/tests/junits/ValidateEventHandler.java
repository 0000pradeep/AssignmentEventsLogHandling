package com.tests.junits;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.main.handler.EventHandler;

@RunWith(MockitoJUnitRunner.class)
public class ValidateEventHandler {
    
    @Test
    public void validateDurationOfEvent() throws Throwable{
    	Map<Object, Map<Object,Object>> expectedTestMap = new LinkedHashMap<>();
    	String path = System.getProperty("user.dir") + "\\src\\test\\resources\\com\\test\\resources\\logFileForUnitTest.txt";
    	expectedTestMap = EventHandler.getEventDuration(path);
    	long expectedDuration =5;
    	Assert.assertEquals(expectedTestMap.get("scsmbstgra").get("timestamp"), expectedDuration);
    }
    
    @Test
    public void validateHost() throws Throwable{
    	Map<Object, Map<Object,Object>> expectedTestMap = new LinkedHashMap<>();
    	String path = System.getProperty("user.dir") + "\\src\\test\\resources\\com\\test\\resources\\logFileForUnitTest.txt";
    	expectedTestMap = EventHandler.getEventDuration(path);
    	String expectedHost ="12345";
    	Assert.assertEquals(expectedTestMap.get("scsmbstgra").get("host"), expectedHost);
    }
    
    @Test
    public void validateLogType() throws Throwable{
    	Map<Object, Map<Object,Object>> expectedTestMap = new LinkedHashMap<>();
    	String path = System.getProperty("user.dir") + "\\src\\test\\resources\\com\\test\\resources\\logFileForUnitTest.txt";
    	expectedTestMap = EventHandler.getEventDuration(path);
    	String expectedLogType ="APPLICATION_LOG";
    	Assert.assertEquals(expectedTestMap.get("scsmbstgra").get("type"), expectedLogType);
    }
    
    @Test
    public void validateAlertStatus() throws Throwable{
    	Map<Object, Map<Object,Object>> expectedTestMap = new LinkedHashMap<>();
    	String path = System.getProperty("user.dir") + "\\src\\test\\resources\\com\\test\\resources\\logFileForUnitTest.txt";
    	expectedTestMap = EventHandler.getEventStats(path);
    	String expectedAlertStatus ="True";
    	Assert.assertEquals(expectedTestMap.get("scsmbstgra").get("Alert"), expectedAlertStatus);
    }
}
