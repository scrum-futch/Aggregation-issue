package service;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import model.Pojo;
import model.PojoHeader;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationPipeline;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.util.CloseableIterator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PojoService {

    @Autowired
    private MongoTemplate appsMongoTemplate;

    public void showCase() {
        Pojo pojo = new Pojo();
        pojo.setTotal(1);
        PojoHeader header = new PojoHeader();
        header.setName("Test");
        header.setAmount(1);
        pojo.setHeader(header);
        appsMongoTemplate.save(pojo);
        List<AggregationOperation> pipeline = Arrays.asList(
                Aggregation.match(new Criteria("header.name").is("Test")),
                Aggregation.unwind("$header"),
                Aggregation.match(new Criteria("header.amount").is(1))
        );
        TypedAggregation<Pojo> aggregation = Aggregation.newAggregation(Pojo.class, pipeline);


        try (CloseableIterator<Document> cursor = appsMongoTemplate.aggregateStream(aggregation,Document.class)) {
            while (cursor.hasNext() ) {
                Document result = cursor.next();
                System.out.println(result);
            }
        }
    }

}
