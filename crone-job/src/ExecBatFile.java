import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.TimerTask;

public class ExecBatFile extends TimerTask{
	
	static int loopInd = 1;

	String batFilePath;
	
	public ExecBatFile(String batFilePath) {
		this.batFilePath = batFilePath;
	}

	@Override
	public void run() {
		ProcessBuilder processBuilder = new ProcessBuilder(batFilePath);
			
		try {
			
			Process process = processBuilder.start();
			System.out.println("loopInd --> "+ (loopInd++) +" : "+ new Date());
			
			StringBuilder output = new StringBuilder();
			BufferedReader reader = new BufferedReader(
			        new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
			    output.append(line + "\n");
			}
			System.out.println(output);
//			int exitVal = process.waitFor();
//			if (exitVal == 0) {
//			    System.out.println(output);
//			    System.exit(0);
//			} else {
//			    //abnormal...
//			}
		} catch (IOException e) {
			e.printStackTrace();		
	}

}}
