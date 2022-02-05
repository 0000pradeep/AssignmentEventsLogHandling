package com.main.execute;

import org.apache.log4j.Logger;

import com.main.handler.EventHandler;


// To Execute mvn clean install exec:java -Dexec.mainClass=com.main.execute.Executor -Dtest=ValidateEventHandler test
public class Executor {
	static Logger log = Logger.getLogger(Executor.class.getName());
	public static void main(String[] args) throws Throwable {
		log.info("Starting Analysing the Events logs.......");
		EventHandler.getEventStats("");
		EventHandler.createRecordInDb();
		log.info("Completed Analysing the Events logs.......");
	}
}
