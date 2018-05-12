package com.mooracle.giflibh2.service;


import com.mooracle.giflibh2.model.Category;

import java.util.List;

//ENTRY 17;
/** ENTRY 17 ADDING SERVICES
 *  1.  This service will have the same four method as the CategoryDao interface so we can just copy it.
 *      NOTE: the focus of doing these steps is not to lose the main rule of DRY (Don’t Repeat Yourself) by making
 *      needless duplication but rather to create neatly separated layers that can be maintained and utilized as
 *      independently from one another as possible.
 *      Thus even if our service and DAO for category contain the same method this will not be true for Gif object and
 *      does not have to be true for categories either. It just happens to work out that way in this project.
 *  2.  Make sure all of the imports are presented just select all options of imports given
 *  NEXT: ENTRY 18: ADDING SERVICES IMPLEMENTATION
 *  GOTO: CategoryServiceImpl.java for ENTRY 18
 *  */

public interface CategoryService {
    //15-1a:
    List<Category> findAll();

    //15-1b:
    Category findById(Long id);

    //15-1c:
    void save(Category category);

    //15-1d:
    void delete(Category category) throws CategoryNotEmptyException;
}
