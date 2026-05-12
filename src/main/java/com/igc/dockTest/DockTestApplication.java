package com.igc.dockTest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController

public class DockTestApplication {
@Value("${APP_ENV:dev}")
private String env;

	@GetMapping("/welcome")
	public String test(){
		return "Welcome to docker--"+env+"--environment";
	}


	public static void main(String[] args) {
		SpringApplication.run(DockTestApplication.class, args);
	}

}
