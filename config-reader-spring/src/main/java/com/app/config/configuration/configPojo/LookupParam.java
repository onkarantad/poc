package com.app.config.configuration.configPojo;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@ToString
public class LookupParam {
	Boolean enableInd;
	List<LookupCacheList> lookupCacheList;
}
