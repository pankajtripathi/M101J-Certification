package com.mongod;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by pankajtripathi on 8/10/15.
 */
public class HelloWorldFreemarker {
    public static void main(String[] args){
        Configuration configuration=new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldFreemarker.class,"/");

        try {
            Template helloTemp = configuration.getTemplate("template.ftl");
            StringWriter writer=new StringWriter();
            Map<String,Object>  helloMap = new HashMap<>();

            helloMap.put("name","Freemarker");
            helloTemp.process(helloMap, writer);

            System.out.println(writer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
