# AssignmentEventsLogHandling
This handles events logs and raises alerts accordingly

**PREREQUISITES** - Java 8 , Maven should be installed .

**INPUT** - Go to Resources >> Config >> Environment.properties and give the desired path of **LOG_FILE_LOCATION**;

**EXECUTE** - It can be executed in three ways 
1) From Cmd - 
Go to working directory and paste **mvn clean install exec:java -Dexec.mainClass=com.main.execute.Executor -Dtest=ValidateEventHandler test**

2) From Run > Run Configuration > New Maven Build > Enter the below command in Goals and click on Run .
 **clean install exec:java -Dexec.mainClass=com.main.execute.Executor -Dtest=ValidateEventHandler test**
 
3) Clean the project and directly run the main class ( com.main.execute.Executor) Right Click > Run As > Java Application

**OUTPUT** - Once the Build is completed , Go to **target/db-data/assignmentdb txt file** to analysis the result . Also Result can be referred from **Logs > ConsoleLogs > testlog.log** and **Logs > ConsoleLogs > testlog1.log.**

**UNIT TESTS** -- Unit tests can be found inside ValidateEventHandler.java
