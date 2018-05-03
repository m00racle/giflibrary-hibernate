package com.mooracle.giflibh2.web.controller;

import com.mooracle.giflibh2.model.Category;
import com.mooracle.giflibh2.service.CategoryService;
import com.mooracle.giflibh2.web.Color;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

//ENTRY 14; ENTRY 19; ENTRY 23; ENTRY 26;
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
 *  NEXT: ENTRY 15: BUILDING DAO LAYER
 *  PRE-REQ:
 *      1.  We will create a new package under the com.mooracle.giflibh2 package and name it dao.
 *      2.  We create a New Java class inside the dao package, name it CategoryDao and choose option as Interface
 *      3.  We make new Java Class inside the DAO package and name it CategoryDaoImpl and we will say this one
 *          implements the CategoryDao in the CategoryDaoImpl class declaration.
 *  GOTO: CategoryDao.java interface fo ENTRY 15!
 *
 *  ENTRY 19: MAKING SERVICE CALLS FROM A CONTROLLER
 *  1.  First we need to remove all Hibernate related code since our DAO layer handles all of that:
 *      a.  We won’t need to @Autowired a sessionFactory here
 *      b.  We won’t need to open a session factory
 *      c.  We won’t need the criteria object
 *  2.  The controller is going to connect to the service layer not to the DAO layer or in the previous case directly
 *      to Hibernate. We need to @Autowired to that service layer specifically to the CategoryService, we name it
 *      categoryService.
 *  3.  Then we can use that categoryService to fetch the list of category objects in our ListCategory method. We’ll
 *      code categoryService.findAll and that closes the gap for us.
 *  NEXT: ENTRY 20: TESTING OUR SERVICE AND DAO
 *  GOTO: templates/category/index.html for ENTRY 20 FOR LITTLE MODIFICATION
 *
 *  ENTRY 23: CONTROLLER METHOD FOR ADDING CATEGORY
 *  1.  We’ll need to call the @Autowired CategoryService save method and pass the category object that was submitted:
 *      categoryService.save(category);
 *  2.  The validation of this category object passed is the subject of the next stage. As for this category variable
 *      we also don’t have one yet since it will be assembled via a form data that is submitted which results in a call
 *      to this controller method.
 *  3.  Since it can be assumed that the data submitted will include all necessary fields for a Category POJO to be
 *      constructed (all necessary field is not null), we can have Spring reassemble that form data into a Category
 *      POJO by sticking a Category parameter in the method.
 *  4.  After the POST-ed Category POJO is saved we want to redirect back to the /category/index view.
 *      Here is our implementation of the POST redirect GET pattern:
 *      a.  We will redirect to /categories as URI in the return section of the POST method: “redirect:/categories”;
 *      b.  When Spring sees a view name beginning with redirect: , it will send a 302 response code along with the
 *          location header to /categories.
 *      NOTE: whatever you specify after the colon redirect: is what the browser will redirect to and not the name of a
 *          view to render.
 *  5.  add this Model Map to the formNewCategory(Model) method. Remember not the addCategory method!
 *  6.  This formNewCategory method is responsible for rendering the view of the new category submission page we were
 *      looking earlier. Please also NOTE that it already passed in Model object called model.
 *  7.  We’ll add model attributes needed for the new form: model.addAttribute(“category”, new Category( ));
 *  8.  Add the colors to the model from the controller method in the formNewCategory method:
 *      model.addAttribute(“colors”, Color.values()); the Color.values is a built in method in Java enum that returns
 *      value or array of values from that enum. We just need to make sure we import the right Color enum!
 *  NEXT: ENTRY 24: HTML FORM FOR ADDING CATEGORY
 *  GOTO: /resources/templates/category/form.html for ENTRY 24
 *
 *  ENTRY 26: CAPTURING FORM VALIDATION ERRORS CONTROLLER
 *  1.  Here, we’ll focus on the addCategory method where first we’ll want it to trigger validation according to the
 *      constraints we just added in the @Entity Category class.
 *  2.  We add @Valid to the addCategory parameter which consist of Category object :
 *      addCategory(@Valid Category category)
 *  3.  Next we’ll need a way to capture the errors from this validation. The easiest way is by including the
 *      BindingResult parameter inside the addCategory method parameters. We’ll name it result thus:
 *      addCategory(@Valid Category category, BindingResult result)
 *  4.  We’ll need to specify what is the alternate actions if errors are found. In this case the most logical actions
 *      is to prevent any save action of the category and redirect back to the form.
 *  5.  Thus to avoid the save we need to add if(result.hasErrors) before the categoryService.save(category) to prevent
 *      the save action if there are indeed errors invoked.
 *  6.  Inside the if statement we will make the redirect if errors do occur which: return “redirect:/categories/add”
 *      which will take us back to the form to add new category but this time is back to empty.
 *  TODO MOO NEXT: ENTRY 27: REPOPULATING DATA WHEN VA;IDATION FAILS
 *  */
@Controller
public class CategoryController {
    //14-2: 19-2;
    @Autowired
    /*private SessionFactory sessionFactory;<--19-1a: deleted**/
    CategoryService categoryService;

    // Index of all categories
    @RequestMapping("/categories")
    public String listCategories(Model model) {

        //14-4; 19-3;
        /*Session session = sessionFactory.openSession();<-- 19-1b: deleted*/
        //14-5 NOTE: THIS IS THE NEW WAY OF DOING THINGS!;

        /*19-1c: all codes below are deleted
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Category> categoryCriteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = categoryCriteriaQuery.from(Category.class);
        categoryCriteriaQuery.select(categoryRoot);
        List<Category> categories = session.createQuery(categoryCriteriaQuery).getResultList();*/

        List<Category> categories = categoryService.findAll();

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
        // Add model attributes needed for new form; 23-7;
        model.addAttribute("category", new Category());
        //23-8;
        model.addAttribute("colors", Color.values());
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

    // Add a category; 23-3; 26-2; 26-3;
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public String addCategory(@Valid Category category, BindingResult result) {
        // TODO: Add category if valid data was received 23-1; 26-5;
        if(result.hasErrors()){
            //26-6;
            return "redirect:/categories/add";
        }
        categoryService.save(category);
        // TODO: Redirect browser to /categories 23-4b;
        return "redirect:/categories";
    }

    // Delete an existing category
    @RequestMapping(value = "/categories/{categoryId}/delete", method = RequestMethod.POST)
    public String deleteCategory(@PathVariable Long categoryId) {
        // TODO: Delete category if it contains no GIFs

        // TODO: Redirect browser to /categories
        return null;
    }
}
