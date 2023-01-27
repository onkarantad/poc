import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.yaml.snakeyaml.Yaml;

public class CronJob {
	

	public static void main(String[] args) {
		
		Yaml yaml = new Yaml();
		Reader yamlFile = null;
		try {
			yamlFile = new FileReader("bat_config.yaml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Map<String, Object> batConfigMap = yaml.load(yamlFile);
	
		Timer timer = new Timer();
		TimerTask timerTask = new ExecBatFile(batConfigMap.get("batFilePath").toString());
		timer.schedule(timerTask,0,(int) batConfigMap.get("time"));
		

	}

}
