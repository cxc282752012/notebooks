package com.books.noteh5servers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(exclude={ DataSourceAutoConfiguration.class})
public class NoteH5ServersApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteH5ServersApplication.class, args);
    }
}
