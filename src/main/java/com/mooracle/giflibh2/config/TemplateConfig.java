package com.mooracle.giflibh2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

//ENTRY 8;
/** ENTRY 8: TEMPLATE CONFIGURATION
 *  1.  This is the Thymeleaf Template Engine Configuration file to demonstrate how to configure template engine in
 *      Spring framework
 *  2.  This configuration is similar for other template engine
 *  3.  First as always we put @Configuration at the class declaration to make Spring pick it up in boot
 *  4.  Here we will make three @Beans:
 *          -Resource Template Resolver which defines the location of intended template
 *          -Template Engine : which render the selected template resolver
 *          -Template View Resolver: which sepcific from Thymeleaf to view the rendered result of the template and
 *           it's resources
 *  5.  We begin by creating @Bean for SpringResourcesTemplateResolver method which will return templateResolver
 *      a.  We set the prefix as location of the templates we will use in this case resources directory is in class path
 *          thus we just add /templates/ and we can concatenate with the template name later on
 *      b.  next we set the suffix which consist of the template file extension in this case it is .html
 *  6.  Next @Bean for SpringTemplateEngine which add the templateResolver method to the Template Engine
 *  7.  Finally @Bean for ThymeleafViewResolvver which we will use the newly instantiated but final template engine to
 *      form a view resolver.
 *      NOTE: we will set the order where the resolver will be queued in Spring MVC as number 1
 *  NOTE: More notes on this please refer to the Spring with Hibernate course summary
 *  NEXT: ENTRY 9: CREATING CATEGORY POJO MODEL
 *  PRE-REQ:
 *      1.  In the model package we create new Java Class and name it Category
 *  GOTO: Category.java for ENTRY 9
 *  */

//8-3: add @Configuration
@Configuration
public class TemplateConfig {
    //8-5: SpringResourcesTemplateResolver method @Bean
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setCacheable(false);

        //8-5a: set prefix to the template location
        templateResolver.setPrefix("classpath:/templates/");

        //8-5b: set the template file extension as suffix
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    //8-6: Set the template engine
    @Bean
    public SpringTemplateEngine templateEngine() {
        final SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.addTemplateResolver(templateResolver());
        return springTemplateEngine;
    }

    //8-7: set the view resolver
    @Bean
    public ThymeleafViewResolver viewResolver() {
        final ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(templateEngine());
        thymeleafViewResolver.setOrder(1);
        return thymeleafViewResolver;
    }
}
