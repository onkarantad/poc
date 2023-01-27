package yamlConfigReader.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Kafka {
    private String kafkaConfigProps;
    private String topics;
    private String grpId;
    private String kafkaOffset;
}