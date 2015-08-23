package com.mongod;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pankajtripathi on 8/17/15.
 */
public class MongoFindExample {

    public static void main(String[] args) {
        MongoClient client=new MongoClient();
        MongoDatabase db=client.getDatabase("course");
        MongoCollection<Document> coll=db.getCollection("findTest");

        coll.drop();

        for (int i = 1; i <= 10; i++)
            coll.insertOne(new Document("x", i * 15));

        System.out.println("Find one");
        Document doc = coll.find().first();

        System.out.println("Find all with into");
        List<Document> all = coll.find().into(new ArrayList<Document>());

        System.out.println("Find all with iteration");
        MongoCursor<Document> cursor = coll.find().iterator();

        try{
            while (cursor.hasNext()){
               Document cur=cursor.next();
           }
        }
        finally {
            cursor.close();
        }

        System.out.println("count");
        long c = coll.count();
        System.out.println(c);
    }
}
