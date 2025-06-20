package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;

@ComponentScan
@SpringBootApplication
@EnableWebSecurity
public class Websitev3Application {

	public static void main(String[] args) {
		SpringApplication.run(Websitev3Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Mapped Endpoints:");
			Arrays.stream(ctx.getBean(RequestMappingHandlerMapping.class)
							.getHandlerMethods().keySet().toArray())
					.forEach(System.out::println);
		};
	}
}
