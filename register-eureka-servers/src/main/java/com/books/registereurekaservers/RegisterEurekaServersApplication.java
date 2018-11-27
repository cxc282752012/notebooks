package com.books.registereurekaservers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegisterEurekaServersApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegisterEurekaServersApplication.class, args);
    }
}
