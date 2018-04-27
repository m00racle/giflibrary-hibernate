package com.mooracle.giflibh2.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

//ENTRY 4; ENTRY 6;
/** ENTRY 4: DATA CONFIGURATION
 *  1.  The main objective of this Java class is to make Spring @Bean which has similar definition with common Java bean
 *      but it is not serializable. The first @Bean will contains session-factory as equivalent with hibernate thus it
 *      will enable Spring to control hibernate session but inside Spring container instead of having configuring
 *      hibernate's own SessionFactory each time we want to manage database
 *  2.  The second bean we created will be data source bean
 *  3.  First we need to set some annotations first for the class scope: @Configuration so that Spring will pick it
 *      up during boot
 *  4.  Next we create our first @Bean (use this annotation for making Spring beans) to set Session Factory
 *      WARNING: ALL BEANS METHODS MUST BE PUBLIC!!!
 *      WARNING: make sure to choose LocalSessionFactoryBean object froma package hibernate5!
 *  5.  Create resource object to class path variable hibernate.cfg.xml. We can just mention the name without full
 *      path to the cfg.xml file since Spring and Hibernate will know where to looks since the naming and placing
 *      of the hibernate.cfg.xml is SPECIFIED MANDATORY
 *      CAUTION: MAKE SURE PICK THE ONE FROM springframework.core.io!!!
 *  6.  Creating new local session factory bean object. This is the equivalent for hibernate's SessionFactory
 *      CAUTION: CHOOSE THE ONE FROM hibernate5!!!
 *  7.  Set the configuration local file which is of course hibernate,cfg,xml however we need to refer it as
 *      Resource object not directly at the xml file. Thus we need to refer the config file location as Resoures
 *      config.
 *  8.  Next we will use Spring to scan JPA annotated entities instead of adding an xml mapping element in the
 *      hibernate.cfg.xml like we did in Hibernate Basic course. This will be more flexible if we have more than
 *      one model to be mapped
 *  9.  However, we don't want to code the package name in this class. We will use what is called externalization.
 *      We will declare our package name in other file (the properties file we just created) and then this class
 *      will only responsible on loading it. To do this we must switch to app.properties file
 *  NEXT: ENTRY 5: PROPERTIES CONFGURATION
 *  GOTO: app.properties for ENTRY 5!
 *
 *  ENTRY 6: FINISHING THE DATA CONFIG
 *  1.  We need to @Autowired an Environment field in order to complete step ENTRY 4-8. This field is used to
 *      gain entry to the property sources in the properties file.
 *      WARNING: CHOOSE THE ONE FORM org.springframework.core.env.
 *      --> Then make @PropertySource to app.properties on top of the class declaration!!
 *  2.  Then we need Spring to Scan the JPA annotated entities and configure data source that initially doesn't
 *      exist. We will make it in step 4 or get help from IntelliJ IDEA to make one
 *      WARNING: IF YOU USE HELP FROM INTELLIJ IDEA MAKE SURE TO CHANGE THE METHOD FROM private TO public!
 *  3.  After that we need to return the LocalSessionFactoryBean object called sessionFactory
 *  4.  Then we move to configuring the data source starting by creating a method to be a @Bean (Spring bean)
 *      that return DataSource object
 *  5.  Inside we instantiate the BasicDataSource object to be used to build the Database Connection Pool or
 *      DBCP. Thsi object comes from the apache package!
 *  6.  Then we call the properties set in the app.properties file and set each property into corresponding
 *      property in the app.properties file. Make sure to use env object we created in the field earlier to
 *      connect to each property listed in the app.properties file.
 *      NOTE: basically this is the same input we use when we set Hibernate in basic course. There are Driver, URL,
 *          but in this case since we will access a remote server database we will have to supply username and
 *          password to enter.
 *      In the end there is only one properties left untouch inside the app.properties file which is the hash salt
 * NEXT: ENTRY 7: APPLICATION CONFIGURATION AND SALT
 * PRE-REQ:
 *  1.  Since we only focus on Java processing and HTML and CSS we already have a glance understanding we will just
 *      import other resources in static and templates from the giflib-hibernate project.
 *  2.  Later on we will inspect the consistency of the codes for each tmeplates and static contents
 *  3.  Next we will make one more Java Class inside the config package and name it AppConfig
 *  GOTO: AppConfig.java for ENTRY 7!
 *  */

//4-3: set annotation as configuration; 6-1: @pPropertySource
@Configuration
@PropertySource("app.properties")
public class DataConfig {
    //6-1: build  Environment field
    @Autowired
    private Environment env;

    //4-4: build Session Factory bean
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        //4-5: creating resource object
        Resource config = new ClassPathResource("hibernate.cfg.xml");

        //4-6: creating local session factory
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        //4-7: set the config as resource path for sessionFactory
        sessionFactory.setConfigLocation(config);

        //6-2: set spring to scan JPA annotated entities and set datasource:
        sessionFactory.setPackagesToScan(env.getProperty("giflibh2.entity.package"));
        sessionFactory.setDataSource(dataSource());

        //6-3: return the LocalSessionFactoryBean:
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        //6-4: instantiate Basic data Source object
        BasicDataSource ds = new BasicDataSource();

        //6-6: set all the properties in the app.properties file
        ds.setDriverClassName(env.getProperty("giflibh2.db.driver"));
        ds.setUrl(env.getProperty("giflibh2.db.url"));
        ds.setUsername(env.getProperty("giflibh2.db.username"));
        ds.setPassword(env.getProperty("giflibh2.db.password"));

        return ds;
    }
}
