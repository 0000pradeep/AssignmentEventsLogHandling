package com.main.execute;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.main.handler.EventHandler;


// To Execute mvn clean install exec:java -Dexec.mainClass=com.main.execute.Executor -Dtest=ValidateEventHandler test
public class Executor {
	static Logger log = Logger.getLogger(Executor.class.getName());
	static Properties prop = null;
	public static void main(String[] args) throws Throwable {
		FileInputStream istream = new FileInputStream(new File(System.getProperty("user.dir") + "\\Resources\\Config\\Environment.properties"));
		prop = new Properties();
		prop.load(istream);
		String logFilePath = prop.getProperty("LOG_FILE_LOCATION");
		log.info("Starting Analysing the Events logs.......");
		EventHandler.getEventStats(logFilePath);
		EventHandler.createRecordInDb(logFilePath);
		log.info("Completed Analysing the Events logs.......");
	}
}
