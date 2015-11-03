package com.mongod;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.*;

import java.util.List;

/**
 * Created by pankajtripathi on 8/25/15.
 */
public class Homework3_1 {
    public static void main(String[] args) {
        MongoClient client=new MongoClient();
        MongoDatabase db=client.getDatabase("school");
        MongoCollection<Document> collection=db.getCollection("students");


        MongoCursor<Document> cursor=collection.find().iterator();

        try{
           while (cursor.hasNext()){

               // get a single document in collection
               Document student=cursor.next();
               //then fetch all the scores in the document...which will list of score documents
               List<Document> scores= (List<Document>) student.get("scores");

               Document minObj=null;
               double minScore=Double.MIN_VALUE;

               for(Document scoredoc:scores) {
                 double score=scoredoc.getDouble("score");
                 String type=scoredoc.getString("type");

                 if(type.equals("homework") && score < minScore)
                     minObj = scoredoc;
                     minScore = score;
               }

               if(minObj != null)
                   scores.remove(minObj);

               collection.updateOne(eq("_id", student.get("_id")),new Document("$set",new Document("scores",scores)));
           }

        }
        finally {
            cursor.close();
        }
        client.close();
    }
}
