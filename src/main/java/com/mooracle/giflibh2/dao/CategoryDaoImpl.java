package com.mooracle.giflibh2.dao;


import com.mooracle.giflibh2.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

//ENTRY 16;
/** ENTRY 16: BUILDING DAO LAYER INTERFACE IMPLEMENTATION
 *  1.  We can use IDEA feature to add directly all methods from CategoryDao interface to CategoryDaoImpl class  because
 *      we declared that the class implements the interface. NOTE: you will see red highlight in the class declaration
 *      section which if you use alt+enter you can call help to add all methods into the class. Now we can start
 *      defining the implementation of each method.
 *  2.  Start with findAll method:
 *      a.  We need a SessionFactory that we can @Autowired and we need it for all of our DAO thus we need to do it up
 *          there in field declaration Of the class field declare SessionFatory sessionFactory
 *      b.  Inside the method we open a session with the @Autowired SessionFactory
 *      c.  Then to make a List of categories we need to utilize the updated method
 *      d.  Close the session
 *      e.  Return the List of Category object called categories
 *  3.  WARNING: for Spring to picks up this class as an autowireable implementation of the CategoryDao, we add
 *      the @Repository up top to this class. Since it was for DAO it has @Repository and for Service @Service
 *      NOTE: we will always annotate the implementation and not the interface as a Spring component.
 *  TODO MOO NEXT: ENTRY 17 ADDING SERVICES
 *  */

//16-3:
@Repository
public class CategoryDaoImpl implements CategoryDao {
    //16-2a:
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Category> findAll() {
        //16-2b;
        Session session = sessionFactory.openSession();

        //16-2c: start with create criteria builder
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        //create criteria Query
        CriteriaQuery<Category> categoryCriteriaQuery = criteriaBuilder.createQuery(Category.class);

        //Specify criteria root
        categoryCriteriaQuery.from(Category.class);

        //Execute querry:
        List<Category> categories = session.createQuery(categoryCriteriaQuery).getResultList();

        //16-2d;
        session.close();

        //16-2e:
        return categories;
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
