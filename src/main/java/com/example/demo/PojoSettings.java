package com.example.demo;

import com.mongodb.ConnectionString;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

public class PojoSettings {

    @Value("localhost:27017")
    private String appMongoUri;

    @Bean
    public Mongo appsMongoClient() {
        return new Mongo(appMongoUri);
    }

    @Bean
    public MongoTemplate appsMongoTemplate(
            @Autowired Mongo appsMongoClient) {
        return new MongoTemplate(appsMongoClient,"pojo-database");
    }





}
