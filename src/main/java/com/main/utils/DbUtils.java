package com.main.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class DbUtils {
	static Logger log = Logger.getLogger(DbUtils.class.getName());
	static Connection con;
	static String connectionString = "jdbc:hsqldb:file:target/db-data/assignmentdb";
	public static void getDbConnection() throws Exception{
		try{
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		log.info("Attempting to connect hsqldb ........");
		con = DriverManager.getConnection(connectionString,"SA","");
		log.info("Connecting to hsqldb successfull !!");
	}
	
	public static String readToString(String filename) throws Exception{
		return  FileUtils.readFileToString(new File(filename) ,"utf-8");
		
	} 
	
	public static void executeStatement(String statement) throws Exception{
		con.createStatement().executeUpdate(statement);
		Thread.sleep(500);
		log.info("Statement is executed successfully !!");
		
	} 
}

