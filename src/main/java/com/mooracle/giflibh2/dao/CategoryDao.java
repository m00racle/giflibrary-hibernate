package com.mooracle.giflibh2.dao;


import com.mooracle.giflibh2.model.Category;

import java.util.List;

//ENTRY 15;
/** ENTRY 15: BUILDING DAO LAYER INTERFACE
 *  1.  We need to create four method here:
 *      a   findAll method which will be used to fetch all category in a List. It will return a List of Category
 *          objects.
 *      b   findById which will find one category using the id specified. It will return a single Category object and
 *          expect a Long id value as parameter
 *      c   Next is save method, which will save a category object whether it is newly created or updated. This method
 *          will return void (for now), expect Category object as parameter.
 *      d   Next is delete method, which will delete specified Category object. Thus it will return void and accepts
 *          Category object as parameter.
 *  NEXT: ENTRY 16: BUILDING DAO LAYER INTERFACE IMPLEMENTATION
 *  GOTO: CategoryDaoImpl.java for ENTRY 16
 *  */

public interface CategoryDao {
    //15-1a:
    List<Category> findAll();

    //15-1b:
    Category findById(Long id);

    //15-1c:
    void save(Category category);

    //15-1d:
    void delete(Category category);
}
