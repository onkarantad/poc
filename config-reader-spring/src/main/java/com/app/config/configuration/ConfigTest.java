package com.app.config.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.app.config.configuration.configPojo.Application;
import com.app.config.configuration.configPojo.MultiCache;

@Component
public class ConfigTest implements CommandLineRunner{
	//hi onkar
	@Autowired
	MultiCache multiCache;
	
	@Autowired
	Application application;

	@Override
	public void run(String... args) throws Exception {
		
		// reading configuration
		
		multiCache.getLookupCache().getLookupCacheList().forEach(i->{
			System.out.println("lookup-> "+i.getName());
		});
		
		System.out.println("dedup-> "+multiCache.getDedupCache().getPreLoad());
		
		System.out.println("dedupTestList-> "+multiCache.getDedupCache().getTestList());

		System.out.println("getEnableInd-> "+multiCache.getLookupCache().getEnableInd());
		
		System.out.println("application-> "+application.getThread().getLookup());
		
		System.out.println("param-> "+application.getParams().getDedup().getThread());
		
	}

}
