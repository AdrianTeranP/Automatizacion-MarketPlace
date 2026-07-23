package Utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try {
            FileInputStream file = new FileInputStream("config.properties");
            properties = new Properties();   // <- esta línea faltaba
            properties.load(file);

        }catch (IOException e){
            throw new RuntimeException("No puedo leer config.properties", e);

        }
    }
    public static String get(String key) {
        return properties.getProperty(key);
    }
}