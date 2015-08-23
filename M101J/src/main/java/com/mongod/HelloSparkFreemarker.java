package com.mongod;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * Created by pankajtripathi on 8/10/15.
 */
public class HelloSparkFreemarker {
    public static void main(String[] args) {
        final Configuration configuration=new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldFreemarker.class, "/");

        Spark.get("/", new Route() {

            @Override
            public Object handle(Request request, Response response) throws Exception {
                StringWriter writer=new StringWriter();
                try {
                    Template helloTemp = configuration.getTemplate("template.ftl");
                    Map<String,Object> helloMap = new HashMap<>();

                    helloMap.put("name","Freemarker");
                    helloTemp.process(helloMap, writer);

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
