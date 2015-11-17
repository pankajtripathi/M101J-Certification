package com.mongod;

/**
 * Created by pankajtripathi on 11/16/15.
 */

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
*The query to be executed for aggregation given below.
*We need to find states with population greater than 10,000,000
*db.zips.aggregate([
*        {$group:{_id:"$state","totalPop":{"$sum":"$pop"}}},
*        {$match:{"totalPop":{"gte":10000000}}}
*        ])
*/

public class AggregationExample {
    public static void main(String[] args) {
        MongoClient client=new MongoClient();
        MongoDatabase database=client.getDatabase("agg");
        MongoCollection collection=database.getCollection("zips");

        List<Document> pipeline;
        pipeline=asList(new Document("$group",
                        new Document("_id","$state")
                                .append("totalPop",
                                        new Document("$sum", "$pop"))),
                        new Document("$match",new Document("totalPop",new Document("$gte",10000000)))
                        );

        /** Alternative approach
         * simply put the query pipeline stages in Document.parse()
         */

        List<Document> pipeline1;
        pipeline1=asList(Document.parse("{$group:{_id:\"$state\",\"totalPop\":{\"$sum\":\"$pop\"}}}"),
                         Document.parse("{$match:{\"totalPop\":{\"gte\":10000000}}}"));

        List<Document> results = (List<Document>) collection.aggregate(pipeline).into(new ArrayList<Document>());
        //List<Document> results1 = (List<Document>) collection.aggregate(pipeline1).into(new ArrayList<Document>());

        /**
        for(Document cur:results1){
            System.out.println(cur.toJson());
        }
        */

        for(Document cur:results){
            System.out.println(cur.toJson());
        }

    }
}
