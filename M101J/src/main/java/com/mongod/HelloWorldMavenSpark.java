package com.mongod;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created by pankajtripathi on 8/9/15.
 */
public class HelloWorldMavenSpark {
    public static void main(String[] args) {
        Spark.get("/",new Route(){

            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "Hello World from Spark";
            }
        });

        Spark.get("/echo/:anim", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return request.params(":anim");
            }
        });
    }
}
