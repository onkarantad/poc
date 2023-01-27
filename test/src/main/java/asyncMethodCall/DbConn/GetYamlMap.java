package asyncMethodCall.DbConn;

import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

public class GetYamlMap {
    public static Map<String, Object> getYamlmap(){
        Yaml yaml = new Yaml();
        Reader yamlFile = null;
        try {
            yamlFile = new FileReader("config.yaml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, Object> yamlMap = yaml.load(yamlFile);

        return yamlMap;
    }
}
