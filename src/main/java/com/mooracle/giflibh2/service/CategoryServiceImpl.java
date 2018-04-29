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
 *  TODO MOO NEXT: ENTRY 19: MAKING SERVICE CALLS FROM A CONTROLLER
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
        return null;
    }

    @Override
    public void save(Category category) {

    }

    @Override
    public void delete(Category category) {

    }
}
