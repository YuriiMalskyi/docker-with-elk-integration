package com.malskyi.kafka;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Log4j2
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class AuthorServiceApp implements CommandLineRunner {

    @Value("${logging.file.name}")
    private String loggingFileName;
    @Value("${logging.file.pattern}")
    private String loggingFilePattern;

    public static void main(String[] args) {
        SpringApplication.run(AuthorServiceApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("loggingFileName: {}", loggingFileName);
        log.debug("loggingFilePattern: {}", loggingFilePattern);
    }
}
