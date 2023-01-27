package yamlConfigReader.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConfigData {
	private KsqlDb ksqlDb;
	private Kafka kafka;
}