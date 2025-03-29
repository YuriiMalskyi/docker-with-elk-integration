package com.malskyi.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@EnableAutoConfiguration
@SpringBootApplication
//@EntityScan({"com.malskyi.*", "com.malskyi.kafka.common-entities.*"})
//@EnableJpaRepositories("com.malskyi.*")
//@ComponentScan(basePackages = { "com.malskyi.*" })
public class BookServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(BookServiceApp.class, args);
    }

}
