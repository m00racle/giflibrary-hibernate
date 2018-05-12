package com.mooracle.giflibh2.service;

import com.mooracle.giflibh2.dao.CategoryDao;
import com.mooracle.giflibh2.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//ENTRY 18;
/** ENTRY 18: ADDING SERVICES IMPLEMENTATION
 *  1.  Use IDEA to correct the highlighted error of implementations from the CategoryService interface.
 *  2.  Since the service layer will call upon the DAO layer we need to @Autowired a CategoryDao here. We do this by
 *      add @Autowired to private field of CategoryDao object named catagoryDao.
 *      NOTE: This is the implementation of loose coupled since the service layer and DAO layer interact through
 *      interface @Autowired field CategoryDao which is an interface type. Do not worry Spring will find the only
 *      implementation of this interface that exist and that is in this case CategoryDaoImpl.
 *  3.  Thus an actual CategoryDaoImpl object is actually will be @Autowired to this newly created field thus we can
 *      just focus on passing the object from that CategoryDaoImpl. For instance we go to the findAll method, we can
 *      just return what the categoryDao (remeber this is the name of the field) findAll method has.
 *      So return categoryDao.findAll( ).
 *  4.  Next we need to add @Service in the class declaration so that Spring will pick it up as a bean that can itself
 *      be autowired as a CategoryService elsewhere, which we will need it in our controller.
 *      WARNING: these annotations @Repository and @Service are Spring components and were attached to the
 *      implementation classes not the interfaces.
 *  NEXT: ENTRY 19: MAKING SERVICE CALLS FROM A CONTROLLER
 *  GOTO: CategoryController.java for ENTRY 19
 *
 *  ENTRY 22: SAVING ENTITIES SERVICE LAYER
 *  1.  Now we just go to the service package and like the DAO package we straight to go to the
 *      CategoryServiceImpl.java class.
 *  2.  We already @Autowired categoryDao thus in this save method we just call that same save method but inside the
 *      categoryDao implementation: categoryDao.save(category);
 *  ENTRY 23: CONTROLLER METHOD FOR ADDING CATEGORY
 *  GOTO: web/controller/CategoryController.java
 *
 *  ENTRY 58: DELETING CATEGORIES
 *  GOTO: delete method
 *  */

//18-4;
@Service
public class CategoryServiceImpl implements CategoryService {
    //18-2:
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> findAll() {
        //18-3:
        return categoryDao.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryDao.findById(id);
    }

    @Override
    public void save(Category category) {
        //22-2;
        categoryDao.save(category);
    }

    /** ENTRY 58: DELETING CATEGORIES
     * 1.   we are here to implements the method body of the delete method in this CategoryService layer
     * 2.   we just need to call the categoryDao.delete method and pass the category
     * in real life it will be best practice if the category is empty validation is being done in this service layer
     * We can check if it is empty and if it is not we can just throw categoryNotEmptyException. Then in our controller
     * when we called on this service layer's delete method we can add try catch.
     * We will experiment with this later. Right now we tested the new code.
     * */
    @Override
    public void delete(Category category) {
       categoryDao.delete(category);
    }
}
