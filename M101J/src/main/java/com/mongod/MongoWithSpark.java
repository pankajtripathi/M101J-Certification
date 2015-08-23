package com.mongod;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;


import static spark.Spark.halt;

/**
 * Created by pankajtripathi on 8/18/15.
 */
public class MongoWithSpark {
    public static void main(String[] args) {
        final Configuration configuration=new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldFreemarker.class, "/");

        MongoClient client=new MongoClient();
        MongoDatabase db=client.getDatabase("course");
        final MongoCollection collection=db.getCollection("sparkMongo");

        collection.drop();

        collection.insertOne(new Document("name","MongoDB"));

        Spark.get("/", new Route() {

            @Override
            public Object handle(Request request, Response response) throws Exception {
                StringWriter writer = new StringWriter();
                try {
                    Template helloTemp = configuration.getTemplate("template.ftl");

                    Object document = collection.find().first();
                    helloTemp.process(document, writer);

                    System.out.println(writer);
                } catch (Exception e) {
                    halt(500);
                    e.printStackTrace();
                }
                return writer;
            }
        });
    }
}
