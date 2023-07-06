package com.example.demo;

import model.Pojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import service.PojoService;

@SpringBootApplication
@Import(PojoSettings.class)
@ComponentScan({"model","service"})
public class DemoApplication {

	@Autowired
	private PojoService pojoService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void afterStartup() {
		pojoService.showCase();
	}
}
