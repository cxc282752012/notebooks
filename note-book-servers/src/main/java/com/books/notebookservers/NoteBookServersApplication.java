package com.books.notebookservers;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.books.notebookservers")
public class NoteBookServersApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteBookServersApplication.class, args);
    }
}
