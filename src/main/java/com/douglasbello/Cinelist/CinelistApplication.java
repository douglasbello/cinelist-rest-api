package com.douglasbello.Cinelist;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinelistApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(CinelistApplication.class);
		springApplication.setBannerMode(Banner.Mode.OFF);
		springApplication.run(args);
	}
}
