package service;

import com.example.demo.DemoApplication;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import model.Pojo;
import model.PojoHeader;
import model.PojoLine;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    private static final Log LOGGER = LogFactory.getLog(PojoService.class);

    @Autowired
    private MongoTemplate appsMongoTemplate;

    public void showCase() {
        Pojo pojo = new Pojo();
        pojo.setTotal(1);
        PojoLine line1 = new PojoLine();
        line1.setDescription("Create");
        line1.setStatus("Created");
        PojoLine line2 = new PojoLine();;
        line2.setDescription("Delete");
        line2.setStatus("Deleted");
        pojo.setLines(Arrays.asList(line1,line2));
        PojoHeader header = new PojoHeader();
        header.setName("Test");
        header.setAmount(1);
        pojo.setHeader(header);
        pojo.setId("1");
        appsMongoTemplate.save(pojo);
        List<AggregationOperation> pipeline = Arrays.asList(
                Aggregation.match(new Criteria("lines.status").is("Deleted")
                        .and("header.name").is("Test")),
                Aggregation.unwind("$lines"),
                Aggregation.match(new Criteria("lines.status").is("Deleted")
                        .and("header.name").is("Test"))
        );
        TypedAggregation<Pojo> aggregation = Aggregation.newAggregation(Pojo.class, pipeline);
        LOGGER.info("----Check the pipeline log after this-----");
        try (CloseableIterator<Document> cursor = appsMongoTemplate.aggregateStream(aggregation,Document.class)) {
            while (cursor.hasNext() ) {
                Document result = cursor.next();
                System.out.println(result);
            }
        }
    }

}
