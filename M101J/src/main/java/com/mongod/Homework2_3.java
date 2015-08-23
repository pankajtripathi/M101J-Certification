package com.mongod;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;

/**
 * Created by pankajtripathi on 8/18/15.
 */
public class Homework2_3 {
    public static void main(String[] args) {
        MongoClient client=new MongoClient();
        MongoDatabase db=client.getDatabase("students");
        MongoCollection<Document> collection=db.getCollection("grades");

        Bson filter = eq("type", "homework");
        Bson sort= orderBy(ascending("student_id"), ascending("score"));


        MongoCursor<Document> cursor = collection.find(eq("type", "homework"))
                .sort(ascending("student_id", "score")).iterator();

        Object studentid = -1;

        try{
            while (cursor.hasNext()){
                Document curr = cursor.next();
                if(!curr.get("student_id").equals(studentid)){
                    collection.deleteOne(eq("_id",curr.get("_id")));
                }
                studentid=curr.get("_id");
            }
        }
        finally {
            cursor.close();
        }

    }
}
