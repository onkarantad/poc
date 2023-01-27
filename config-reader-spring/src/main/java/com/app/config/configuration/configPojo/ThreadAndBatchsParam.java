package com.app.config.configuration.configPojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
public class ThreadAndBatchsParam {
    int thread;
    int batch;
}
