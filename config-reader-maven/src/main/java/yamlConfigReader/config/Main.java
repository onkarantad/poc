package yamlConfigReader.config;

public class Main {
	public static void main(String[] args) {
		ConfigData configData = GetConfig.getConfigData();
		
		System.out.println(configData);
	}

}
