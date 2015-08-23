package com.mongod;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;

/**
 * Created by pankajtripathi on 8/17/15.
 */
public class MongoInsertExample {

    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();

        MongoDatabase db=client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("insertTest");

        coll.drop();

        Document doc=new Document("name", "Pankaj")
                .append("age",24)
                .append("profession","Software Engineer");

        coll.insertOne(doc);
    }
}
