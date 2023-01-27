package com.test.log4j2.log4j2SPRING;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CLRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("test Spring");
        log.info("test error");
        log.error("test error");
    }
}
