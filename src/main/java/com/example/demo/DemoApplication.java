package com.example.demo;

import model.Pojo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import service.PojoService;

@SpringBootApplication
@Import(PojoSettings.class)
@ComponentScan({"model","service"})
public class DemoApplication {

	private static final Log LOGGER = LogFactory.getLog(DemoApplication.class);

	@Autowired
	private PojoService pojoService;

	public static void main(String[] args) {

		LOGGER.info("Application to demo issue in aggregation when due to change in context\n " +
				"due to $unwind operation the @Field annotation is not working as expected");
		SpringApplication.run(DemoApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void afterStartup() {
		pojoService.showCase();
	}
}
