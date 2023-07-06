package com.example.demo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

public class PojoSettings {

    @Value("mongodb://localhost:27017/pojo-database")
    private String appMongoUri;

    @Bean
    public MongoClient appsMongoClient() {
        ConnectionString connectionString = new ConnectionString(appMongoUri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate appsMongoTemplate(
            @Autowired MongoClient appsMongoClient) {
        return new MongoTemplate(appsMongoClient,"pojo-database");
    }





}
