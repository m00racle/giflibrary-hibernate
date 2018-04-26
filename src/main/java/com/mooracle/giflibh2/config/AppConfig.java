package com.mooracle.giflibh2.config;


import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

//ENTRY 7;
/**
 *  ENTRY 7: APPLICATION CONFIGURATION AND SALT
 *  1.  This is Application Config file. Back in the Spring Basic course we had all of the content of this file inside
 *      Application.java file. Now we have our own AppConfig.java for application config
 *  2.  This file consist only a bean that initialize out the Hash IDs functionality
 *  3.  First the same as the DataConfig we need to add @Configuration in the class declaration so that Spring will pick
 *      it up during boot as configuration.
 *  4.  Then we need to @PropertySource to connect this config file to the app.properties file
 *  5.  The same we need to declare @Autowired Environment field object we name it env
 *      WARNING: CHOOSE THE ONE FROM org.springframework.core.env.
 *  6.  Then we make a method for @Bean that manage Hashids object
 *  7.  Inside we just will return the new instantiated Hashids object that uses property from env "giflibh2.hash.salt"
 *  NEXT: ENTRY 8: TEMPLATE CONFIGURATIONS
 *  PRE-REQ:
 *      1.  Create a new Java Class inside the config package name it TemplateConfig
 *  GOTO: TemplateConfig.java for ENTRY 8!
 *  */

//7-3: put config annotation; 7-4: put property source from app.properties
@Configuration
@PropertySource("app.properties")
public class AppConfig {
    //7-5: declare Environement field
    @Autowired
    private Environment env;

    //7-6: make a @Bean method
    @Bean
    public Hashids hashids() {
        //7-7: return the new instantiated Hashids object
        return new Hashids(env.getProperty("giflibh2.hash.salt"),8);
    }
}
