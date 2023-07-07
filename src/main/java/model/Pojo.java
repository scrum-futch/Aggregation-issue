package model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "pojo")
@Data
public class Pojo {

    @Id
    String id;

    List<PojoLine> lines;

    @Field("pojo_header")
    PojoHeader header;

    Integer total;
}
