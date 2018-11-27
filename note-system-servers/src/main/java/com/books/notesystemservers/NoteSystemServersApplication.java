package com.books.notesystemservers;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.books.notesystemservers.mapper")
public class NoteSystemServersApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteSystemServersApplication.class, args);
	}
}
