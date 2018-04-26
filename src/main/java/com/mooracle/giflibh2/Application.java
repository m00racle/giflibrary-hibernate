package com.mooracle.giflibh2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//ENTRY 2;
/** ENTRY 2: BUILDING THE PROJECT STRUCTURE
 *  1.  This is the Application and Run file.
 *  2.  In the Spring Basic course this kind of file also stored the app configuration but in this course we use
 *      single responsibility principle and make this file only serve a single purpose: initiate main method and
 *      run app
 *  3.  Then we add @EnableAutoConfiguration to let Spring guess the appropriate and assited config. Also
 *      add @ComponenetScan to let Spring detect components, configurations, and services in this package.
 *  NEXT: ENTRY 3: HIBERNATE CONFIGURATION - list of pre-requisite steps:
 *      1.  Create new directory under main and name it resources
 *      2.  inside the resources directory create new xml file name it (MANDATORY): hibernate,cfg.xml
 *  GOTO: hibernate.cfg.xml for ENTRY 3
 *  */

//1-3: Add Spring annotations:
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) {
        //1-1:
        SpringApplication.run(Application.class, args);
    }
}
