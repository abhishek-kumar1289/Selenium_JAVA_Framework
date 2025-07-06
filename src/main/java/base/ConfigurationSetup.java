package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeSuite;

public class ConfigurationSetup {

	public static Properties prop;
	
	@BeforeSuite
	public void configSetup() {
		prop = new Properties();
		try {
		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"/src/main/resources/config.properties" );
		prop.load(file);
		}catch(IOException e) {
			e.printStackTrace();
		}					
		//ExtentReportManager.getReporter(); --This has been implemented in TestListener
	}
}
