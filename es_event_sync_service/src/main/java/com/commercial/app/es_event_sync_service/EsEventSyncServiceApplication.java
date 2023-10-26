package com.commercial.app.es_event_sync_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class EsEventSyncServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsEventSyncServiceApplication.class, args);
	}

}
