package yamlConfigReader.config;

import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class GetConfig {

    private static GetConfig instance = null;
    private static ConfigData config;

    private GetConfig(){}

    private static GetConfig getInstance(){
        if (instance==null){
            synchronized (GetConfig.class){
                instance = new GetConfig();
                initializeConfig();
            }
        }
        return instance;
    }

    private static void initializeConfig(){
//        Constructor constructor = new Constructor(ConfigData.class);
//		TypeDescription configDescription = new TypeDescription(ConfigData.class);
//		configDescription.addPropertyParameters(ConfigConstants.configKsqlDb, KsqlDb.class);
//		configDescription.addPropertyParameters(ConfigConstants.configKafka, Kafka.class);
//		constructor.addTypeDescription(configDescription);
//		Yaml yaml = new Yaml(constructor);
        Yaml yaml = new Yaml(new Constructor(ConfigData.class));
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(ConfigConstants.configFileLocation);
        } catch (FileNotFoundException fe) {
        	System.err.println("Config File Not Found!");
        }
        config = yaml.load(inputStream);
    }

//    public static GetConfig getConfig(){
//        if (instance==null){
//            instance = GetConfig.getInstance();
//        }
//
//        return instance;
//    }

    public static ConfigData getConfigData(){
        if (instance==null){
            instance = GetConfig.getInstance();
        }
        return config;
    }


}
