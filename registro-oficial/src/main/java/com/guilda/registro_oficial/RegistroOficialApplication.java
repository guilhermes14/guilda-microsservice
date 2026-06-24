package com.guilda.registro_oficial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RegistroOficialApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistroOficialApplication.class, args);
	}

}
