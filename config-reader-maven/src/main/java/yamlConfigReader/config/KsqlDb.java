package yamlConfigReader.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KsqlDb {
    private String host;
    private int port;
}