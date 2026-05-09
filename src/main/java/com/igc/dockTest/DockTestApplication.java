package com.igc.dockTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DockTestApplication {

	@GetMapping("/welcome")
	public String test(){
		return "Welcome to docker";
	}


	public static void main(String[] args) {
		SpringApplication.run(DockTestApplication.class, args);
	}

}
