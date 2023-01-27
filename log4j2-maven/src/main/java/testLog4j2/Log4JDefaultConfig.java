package testLog4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4JDefaultConfig {
	private static final Logger log = LogManager.getLogger(Log4JDefaultConfig.class);

	public static void main(String[] args) {

		log.info("This is an INFO level log message!");
		log.error("This is an ERROR level log message!");
		log.debug("This is an debug level log message!");
		log.trace("This is an trace level log message!");

	}
}