package com.mongod;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;

/**
 * Created by pankajtripathi on 10/26/15.
 */
public class Homework2_3 {
    public static void main(String args[]){

        MongoClient client=new MongoClient();
        MongoDatabase db=client.getDatabase("students");
        MongoCollection<Document> collection=db.getCollection("grades");

        Bson filter_cond=eq("type","homework");
        Bson sort_cond=orderBy(ascending("student_id"),ascending("score"));

        MongoCursor<Document> cursor=collection.find(filter_cond).sort(sort_cond).iterator();

        Object student_id=-1;

        try{
            while(cursor.hasNext()){
                Document current =cursor.next();
                if(!current.get("student_id").equals(student_id)){
                    collection.deleteOne(eq("_id",current.get("_id")));
                }
                student_id = current.get("student_id");
            }
        }
        finally {
            cursor.close();
        }

    }
}
