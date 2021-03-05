package by.educ.ivan.education.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    public Properties getProperties(String propertiesFile) {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream(propertiesFile));
            return properties;
        } catch (IOException ex) {
            return null;
        }
    }
}
