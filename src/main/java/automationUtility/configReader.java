package automationUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class configReader {

	public static Properties readFromProperties() throws IOException{
	
		Properties prop= new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/Configurations/config.properties");
		prop.load(fis);
		return prop;				
    }
}

