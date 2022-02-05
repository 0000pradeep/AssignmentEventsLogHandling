package com.main.handler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONException;
import org.json.JSONObject;
import com.main.utils.DbUtils;

public class EventHandler {
	static BufferedReader breader = null;
	static  InputStream inputStream = null;
	static Logger log;

	public static void initialiseReports() throws Exception {
		log = Logger.getLogger(EventHandler.class.getName());
		PropertyConfigurator.configure("Resources\\Properties\\log4j.properties");
	}

	public static Map<Object, Map<Object, Object>> getEventDuration(String pathOfLogFile) {
		
		
		try {
			initialiseReports();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			  inputStream = new FileInputStream(pathOfLogFile);
		} catch (FileNotFoundException e) {
			log.info("The input log file is not found");
			log.debug("Exception found ", e);
		}
		   breader = new BufferedReader(new InputStreamReader(inputStream));
		Map<Object, Map<Object, Object>> map = new LinkedHashMap<>();
		Map<Object, Object> innerMap = new LinkedHashMap<>();
		String jsonLog;
		try {
			StringBuilder sbuilder = new StringBuilder();
			String line = "";

			try {
				while ((line = breader.readLine()) != null) {
					sbuilder.setLength(0);
					sbuilder.append(line);
					sbuilder.append("\n");
					jsonLog = sbuilder.toString();
					JSONObject object = new JSONObject(jsonLog);
					innerMap.clear();
					for (Object key : object.keySet()) {
						innerMap.put(key, object.get((String) key));
					}
					LinkedHashMap<Object, Object> innerMap2 = new LinkedHashMap<>();
					innerMap2.clear();
					for (Map.Entry<Object, Object> entry : innerMap.entrySet()) {
						if (!entry.getKey().equals("id")) {
							innerMap2.put(entry.getKey(), entry.getValue());
						}
					}
					innerMap2.put("Alert", "False");
					if (!map.containsKey(innerMap.get("id"))) {
						map.put(innerMap.get("id"), innerMap2);
					} else if (map.containsKey(innerMap.get("id"))) {

						long oldTimeStamp = (Long) map.get(innerMap.get("id")).get("timestamp");
						long newTimeStamp = (Long) innerMap2.get("timestamp");
						if (oldTimeStamp < newTimeStamp) {
							long difference = newTimeStamp - oldTimeStamp;
							innerMap2.put("timestamp", difference);
						} else {
							long difference = oldTimeStamp - newTimeStamp;
							innerMap2.put("timestamp", difference);
						}
						map.put(innerMap.get("id"), innerMap2);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				inputStream.close();
				log.info("File Reader is closed");
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				breader.close();
				log.info("Buffer Reader is closed");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public static Map<Object, Map<Object, Object>> getEventStats(String logFilePath) throws Throwable {
		Map<Object, Map<Object, Object>> map = getEventDuration(logFilePath);
		LinkedHashMap<Object, Object> checkAlert = new LinkedHashMap<>();
		for (Map.Entry<Object, Map<Object, Object>> entry : map.entrySet()) {
			Long timestamp = (Long) entry.getValue().get("timestamp");
			if (timestamp > 4) {
				String id = (String) entry.getKey();
				checkAlert = (LinkedHashMap<Object, Object>) entry.getValue();
				log.info("Event time is more than 4 ms for Event Id ======>" + id);
				checkAlert.put("Alert", "True");
				log.info("Alert is Raised for Event Id ====> " + id);
				map.put(id, checkAlert);
			} else if (timestamp < 4) {
				String id = (String) entry.getKey();
				checkAlert = (LinkedHashMap<Object, Object>) entry.getValue();
				log.info("Event time is less than 4 ms for Event Id ======>" + id);
				checkAlert.put("Alert", "False");
				map.put(id, checkAlert);
			}
		}
		return map;

	}

	public static void createRecordInDb(String logFilePath) throws Throwable {
		Map<Object, Map<Object, Object>> map = getEventStats(logFilePath);
		String createTable = DbUtils.readToString("Resources/Sql/create.sql");
		String insertData = DbUtils.readToString("Resources/Sql/insert.sql");
		DbUtils.getDbConnection();
		DbUtils.executeStatement(createTable);
		for(Map.Entry<Object, Map<Object,Object>> entry :map.entrySet()){
			StringBuilder insertDataBuilder = new StringBuilder(insertData);
			String eventDuration="NA";
			String host="NA";
			String logType = "NA";
			String Alert ="NA";
		  String key =(String) entry.getKey();
		  LinkedHashMap<Object, Object> getStatsValues = new LinkedHashMap<>();
		  getStatsValues = (LinkedHashMap<Object, Object>) entry.getValue();
		  insertDataBuilder.append("'").append(key).append("'").append(",");
		  if(getStatsValues.containsKey("timestamp")){
			   eventDuration = String.valueOf((Long) getStatsValues.get("timestamp"));
		}
		  if(getStatsValues.containsKey("host")){
			  host = (String) getStatsValues.get("host");
		}
		  if(getStatsValues.containsKey("type")){
			  logType = (String) getStatsValues.get("type");
		}
		  
		  if(getStatsValues.containsKey("Alert")){
			  Alert = (String) getStatsValues.get("Alert");
		}
		  insertDataBuilder.append("'").append(eventDuration).append("'").append(",");
		  insertDataBuilder.append("'").append(host).append("'").append(",");
		  insertDataBuilder.append("'").append(logType).append("'").append(",");
		  insertDataBuilder.append("'").append(Alert).append("'").append(");");
		  DbUtils.executeStatement(insertDataBuilder.toString());
		  log.info("Record Successfully Inserted Having Event Id as " +key);
		  insertDataBuilder.setLength(0);
	}

}
	
}
