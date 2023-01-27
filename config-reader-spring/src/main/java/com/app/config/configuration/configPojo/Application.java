package com.app.config.configuration.configPojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
@Getter
@Setter
@ToString
public class Application {
    CacheParam cache;
    DateFormatParam dateFormat;
    String keyConcatChar;
    ThreadParam thread;
    ThreadParam batch;
    ParamsParam params;
}
