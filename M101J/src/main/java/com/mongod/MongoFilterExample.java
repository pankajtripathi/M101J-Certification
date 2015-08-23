package com.mongod;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;
/**
 * Created by pankajtripathi on 8/17/15.
 */
public class MongoFilterExample {
    public static void main(String[] args) {
        MongoClient client=new MongoClient();
        MongoDatabase db=client.getDatabase("course");
        MongoCollection<Document> collection=db.getCollection("findFilterTest");

        collection.drop();

        for (int i = 1; i <= 10; i++)
            collection.insertOne(new Document().append("x",i).append("y",i*10));

        Bson filter = and(gt("x", 15), lt("x", 100));
        Bson projection= fields(include("x"), exclude("y"), excludeId());

        //Bson sort= new Document("x",1).append("y",-1); // -1 descending;

        Bson sort = orderBy(ascending("x"),descending("y"));

        List<Document> list=collection.find(filter)
                                       .projection(projection)
                                       .sort(sort)
                                       .skip(2)
                                       .limit(6)
                                       .into(new ArrayList<Document>());


        collection.updateOne(eq("x",1),new Document("$set",new Document("x",11)), new UpdateOptions().upsert(true));
        collection.updateMany(gte("x",2), new Document("$inc",new Document("x",1)));

        // deletion
        collection.deleteOne(eq("x",4));
        collection.deleteMany(gt("x",10));

        long count=collection.count(filter);
        System.out.println(count);
    }
}
