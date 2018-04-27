package com.mooracle.giflibh2.web.controller;

import com.mooracle.giflibh2.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

//ENTRY 14;
/** ENTRY 14: FETCHING DATA WITH HIBERNATE IN SPRING
 *  1.  Here we are ready to use Hibernate features SessionFActory to:
 *          - create a session
 *          - grab a category object (in this case)
 *          - close the session
 *          - send the returned category object to Thymeleaf for processing
 *  2.  First step is to fetch data from our database, we'll start by @Autowired-ing a SessionFactory in the Category
 *      controller. Since we defined sessionFactory method as @Bean it will be valid for @Autowired-ing.
 *      NOTE: The object is Hibernate SessionFactory and we name it sessionFactory
 *  3.  next we move to listCategories method and drop some Hibernate code thus we can visit the view in our app that
 *      shows a list of categories. Therefore we need to:
 *          - open session
 *          - get all categories from the database
 *          - store the list into this categories variable
 *          - close the session
 *  4.  first call method: sessionFactory.openSession() and intiate it into Session object (hibernate) called session
 *  5.  Instead of storing this brand new array list into the categories variable we will use this session to create a
 *      CRITERIA that will be based on the category entity.
 *      WARNING: THE METHODS IN THE COURSE USING DIRECTLY CRITERIA IN THE ARRAYLIST IS DEPRECATED!
 *      THE GOOD THING IS the latest way of creating criteria seems resulting parameterized ArrayList thus the use
 *      of @SupressWarnings is not required!
 *  RUN: SAVE ALL MAKE SURE DATABASE IS RUNNING AND HOT BOOT RUN!
 *  NEXT GOTO: H2 DATABASE CONSOLE OPENED IN THE BROWSER!
 *  6.  Check if really no category is available in the Category table by using SQL command : SELECT * FROM CATEGORY
 *  7.  Insert new category: INSERT INTO CATEGORY (NAME, COLORCODE) VALUES ('Technology', '#59b3b3')
 *  8.  Then we refresh the web browser to see that new category was added named Technology with corresponding color as
 *      specified.
 *  TODO MOO: WE GO TO THE NEXT STAGE OF THE COURSE LATER
 *      */
@Controller
public class CategoryController {
    //14-2:
    @Autowired
    private SessionFactory sessionFactory;

    // Index of all categories
    @RequestMapping("/categories")
    public String listCategories(Model model) {

        //14-4;
        Session session = sessionFactory.openSession();
        //14-5 NOTE: THIS IS THE NEW WAY OF DOING THINGS!;
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Category> categoryCriteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = categoryCriteriaQuery.from(Category.class);
        categoryCriteriaQuery.select(categoryRoot);
        List<Category> categories = session.createQuery(categoryCriteriaQuery).getResultList();

        model.addAttribute("categories",categories);
        return "category/index";
    }

    // Single category page
    @RequestMapping("/categories/{categoryId}")
    public String category(@PathVariable Long categoryId, Model model) {
        // TODO: Get the category given by categoryId
        Category category = null;

        model.addAttribute("category", category);
        return "category/details";
    }

    // Form for adding a new category
    @RequestMapping("categories/add")
    public String formNewCategory(Model model) {
        // TODO: Add model attributes needed for new form

        return "category/form";
    }

    // Form for editing an existing category
    @RequestMapping("categories/{categoryId}/edit")
    public String formEditCategory(@PathVariable Long categoryId, Model model) {
        // TODO: Add model attributes needed for edit form

        return "category/form";
    }

    // Update an existing category
    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.POST)
    public String updateCategory() {
        // TODO: Update category if valid data was received

        // TODO: Redirect browser to /categories
        return null;
    }

    // Add a category
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public String addCategory() {
        // TODO: Add category if valid data was received

        // TODO: Redirect browser to /categories
        return null;
    }

    // Delete an existing category
    @RequestMapping(value = "/categories/{categoryId}/delete", method = RequestMethod.POST)
    public String deleteCategory(@PathVariable Long categoryId) {
        // TODO: Delete category if it contains no GIFs

        // TODO: Redirect browser to /categories
        return null;
    }
}
