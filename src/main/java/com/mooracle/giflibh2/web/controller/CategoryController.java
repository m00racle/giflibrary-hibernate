package com.mooracle.giflibh2.web.controller;

import com.mooracle.giflibh2.model.Category;
import com.mooracle.giflibh2.service.CategoryNotEmptyException;
import com.mooracle.giflibh2.service.CategoryService;
import com.mooracle.giflibh2.web.Color;
import com.mooracle.giflibh2.web.FlashMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

//ENTRY 14; ENTRY 19; ENTRY 23; ENTRY 26; ENTRY 27; ENTRY 28; ENTRY 31; ENTRY 42
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
 *
 *  ENTRY 27: RE-POPULATING DATA WHEN VALIDATION FAILS
 *  1.  In the addCategory method, we add RedirectAttributes as parameter and name it redirectAttributes so that we can
 *      use it to make any flash attributes added will survive exactly one redirect.
 *  2.  Now, if we do detect errors, let’s include the category object that was submitted so that it’s available upon
 *      redirect by adding inside the if statement: redirectAttributes.addFlashAttribute(“category”, category)
 *      NOTE: Category object named category already be part of the addCategory method and passed from POST request
 *  3.  Next we need to modify the method that handles the rendering of the add form which is the formNewCategory to
 *      make it shows the category object in the flash attribute.
 *  4.  To make sure we do not overwrite the new Category( ) to the model which already existed we will add
 *      if(!model.containsAttribute(“category”)) then we move the model.addAttribute for category inside this
 *      if statement.
 *  5.  Else if there is already has model attributes named category then we won’t add another one because that mean
 *      a user had submitted invalid data. Thus we just let it roll and do nothing.
 *
 *  ENTRY 28: DISPLAYING VALIDATION MESSAGE
 *  1.  Once again we’ll utilize the addCategory method’s redirectAttributes object to pass on validation results to
 *      the form upon redirection.
 *      NOTE: this is where in teacher’s opinion that Spring framework lacks a bit in data validation. Although this
 *      method works it is not the most recommended way of informing the errors.
 *  2.  If the BindingResult result reveals that we do have errors upon validation, we want to include those validation
 *      errors for redirects. We use :
 *      redirectAttributes.addFlashAttribute(“org.springframework.validation.BindingResult.category”, result);
 *      This will combine this validation data to category attribute in the same method for Thymeleaf to use.
 *  NEXT: ENTRY 29: DISPLAYING VALIDATION MESSAGE
 *  GOTO: templates/category/form.html
 *
 *  ENTRY 31: MAKING FLASH MESSAGE SURVIVES A REDIRECT
 *  1.  We need to add a flashMessage object inside the addCategory method model map
 *  2.  The positioning of the code is important thus make sure the flashMessage attribute follow the process that
 *      we want to denote.
 *  3.  Here we want to give flash message when a Category is successfully added.
 *  4.  Thus below the save procedure we’re going to add
 *      redirectAttribute.addFlashAttribute(“flash”, new FlashMessag(...) fill it with constructor spec
 *
 *  ENTRY 42: Updating Categories Part 1
 *  1.  The code needed in formEditCategory  basically similar to the formNewCategory method except instead of adding
 *      new category it will edit existing one.
 *  2.  So we make the same code as formNewCategory except we then fetch an existing category using the id:
 *      model.addAttribute(“category”, categoryService.findById(categoryId);
 *  3.  NOTE: we already modify the CategoryDaoImpl on findById method thus this will work
 *  4.  Also we just made patch the CategoryServiceImpl class to findById method.
 *
 *  ENTRY 44: UPDATING CATEGORIES PART 2
 *  We need to go to web/controller/CategoryController to do this:
 *  1.  First we need to modify the existing formNewCategory method to add 3 more attributes
 *  2.  First model.addAttribute(“action”, “/categories”);
 *  3.  Second model.addAttribute(“heading”, “New Category”);
 *  4.  Third model.addAttribute(“submit”, “Add”);
 *  5.  Next we do the same for the formEditCategory method
 *  6.  First model.addAttribute(“action”, String.format(“/categories/%s”, categoryId);
 *  7.  NOTE: categoryId here will have come from the URI itself
 *  8.  Second, model.addAttribute(“heading”, “Edit Category”);
 *  9.  Lastly, model.addAttribute(“submit”, “Update”);
 *  10. Next we go to updateCategory method, here we want to catch any validation errors.
 *  11. If errors do occur the redirect back to the edit form with a flash message.
 *  12. This is similar as addCategory method thus we use that method as reference.
 *  13. First let’s make up the parameters, add @Valid Category category
 *  14. Second parameter will be BindingResult result
 *  15. Third parameter will be RedirectAttributes redirectAttributes
 *  16. Next same as addCategory method we validate the BindingResult
 *  17. Then if there are errors add redirectAttributes.addFlashAttribute same as addCategory method.
 *  18. Redirect back to return String.format(“redirect:/categories/%s/edit”, categoryId);
 *  19. But before step 18 we need add one more parameter: @PathVariable Long categoryId
 *  20. NOTE: @PathVariable takes categoryId from the path of the url submitted when calling edit
 *  21. When it is not error just send a flash message like addCategory method
 *  22. Call categoryService.save(category);
 *  23. Finally return “redirect:/categories”
 *
 *  ENTRY 57: DELETING CATEGORIES
 *  GOTO: deleteCategory method
 *
 *  ENTRY 62: Custom Exception in the Service Layer
 *  GOTO: deleteCategory method
 *  */
@Controller
public class CategoryController {
    //14-2: 19-2;
    @Autowired
    /*private SessionFactory sessionFactory;<--19-1a: deleted**/
    private CategoryService categoryService;

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

    /* PATCH: Single category page
    * BECAUSE we already fix the lazy loading error we can then build this section of the code
    * it will be able to initialize the Gifs collection thus it should be okay now
    * **/
    @RequestMapping("/categories/{categoryId}")
    public String category(@PathVariable Long categoryId, Model model) {
        // PATCH: Get the category given by categoryId
        Category category = categoryService.findById(categoryId);

        model.addAttribute("category", category);
        return "category/details";
    }

    // Form for adding a new category
    @RequestMapping("categories/add")
    public String formNewCategory(Model model) {
        //27-4;
        if(!model.containsAttribute("category")) {
            // Add model attributes needed for new form; 23-7;
            model.addAttribute("category", new Category());
        }
        //23-8;
        model.addAttribute("colors", Color.values());
        //44-2.
        model.addAttribute("action", "/categories");
        //44-3.
        model.addAttribute("heading", "New Category");
        //44-4.
        model.addAttribute("submit", "Add");

        return "category/form";
    }

    // Form for editing an existing category
    @RequestMapping("categories/{categoryId}/edit")
    public String formEditCategory(@PathVariable Long categoryId, Model model) {
        // 42-1. 42-2: Add model attributes needed for edit form
        if(!model.containsAttribute("category")) {
            model.addAttribute("category", categoryService.findById(categoryId));
        }
        model.addAttribute("colors", Color.values());
        //44-6.
        model.addAttribute("action", String.format("/categories/%s", categoryId));
        //44-8.
        model.addAttribute("heading", "Edit Category");
        //44-9.
        model.addAttribute("submit", "Update");
        return "category/form";
    }

    // Update an existing category: 44-13. 44-14. 44-15. 44-19.
    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.POST)
    public String updateCategory(@Valid Category category, BindingResult result,
                                 RedirectAttributes redirectAttributes, @PathVariable Long categoryId) {
        // 44-16. 44-17: Update category if valid data was received
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category",
                    result);
            redirectAttributes.addFlashAttribute("category", category);
            //44-18.
            return String.format("redirect:/categories/%s/edit", categoryId);
        }
        //44-22.
        categoryService.save(category);
        //44-21.
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Category Updated",
                FlashMessage.Status.SUCCESS));
        // 44-23: Redirect browser to /categories
        return "redirect:/categories";
    }

    // Add a category; 23-3; 26-2; 26-3; 27-1
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public String addCategory(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        // Add category if valid data was received 23-1; 26-5;
        if(result.hasErrors()){
            //28-2;
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category",
                    result);
            //27-2;
            redirectAttributes.addFlashAttribute("category", category);
            //26-6;
            return "redirect:/categories/add";
        }
        categoryService.save(category);
        //30-4;
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Category added",
                FlashMessage.Status.SUCCESS));
        // 23-4b: Redirect browser to /categories 23-4b;
        return "redirect:/categories";
    }

    /** ENTRY 57: Delete an existing category
     * 1. we need to ensure if the category is really empty and if it is so we can proceed to delete
     * 2. make sure to give appropriate flash messages and redirects even if it is not able to delete
     *      -if it is not empty then redirect back to categories/{categoryId}/edit
     *      -if it is empty just redirect back to "/categories"
     * 3. to test if the category is empty we need tograb the category using categoryId but store it in other name: cat
     *
     *
     * */
    @RequestMapping(value = "/categories/{categoryId}/delete", method = RequestMethod.POST)
    public String deleteCategory(@PathVariable Long categoryId, RedirectAttributes redirectAttributes) {
        // 57; 62: Delete category if it contains no GIFs (Patched for forget to add attributeName)
        try {
            Category cat = categoryService.findById(categoryId);
            categoryService.delete(cat);
        } catch (CategoryNotEmptyException e) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Category is not empty, Unable to delete",
                    FlashMessage.Status.FAILURE));
            return String.format("redirect:/categories/%s/edit", categoryId);
        }
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Category deleted!",
                FlashMessage.Status.SUCCESS));//<--add flash message that category has been deleted

        // 57: Redirect browser to /categories
        return "redirect:/categories";
    }
}
