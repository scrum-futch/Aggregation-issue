package service;

import com.mongodb.DBObject;
import model.Pojo;
import model.PojoHeader;
import model.PojoLine;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Aggregation agg = Aggregation.newAggregation(pipeline);
        TypedAggregation<Pojo> aggregation = Aggregation.newAggregation(Pojo.class, pipeline);
        LOGGER.info("----Check the pipeline log after this-----");
        AggregationResults<DBObject> results = appsMongoTemplate.aggregate(agg,Pojo.class, DBObject.class);
        Iterator cursor = results.iterator();
        while (cursor.hasNext() ) {
                DBObject result = (DBObject) cursor.next();
                System.out.println(result);
            }
    }
}
