package com.mooracle.giflibh2.dao;


import com.mooracle.giflibh2.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

//ENTRY 16; ENTRY 21;
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
 *  NEXT: ENTRY 17 ADDING SERVICES
 *  PRE-REQ:
 *  1.  make new package indicating service layer. We make it under the com,mooracle.giflibh2 package and name it
 *      service.
 *  2.  inside the service package create new interface name it CategoryService
 *  3.  inside the service package creaete new class name it CategoryServiceImpl and implements CategoryService
 *  GOTO: CategoryService.java for ENTRY 17
 *
 *  ENTRY 21: SAVING ENTITIES DAO LAYER
 *  1.  Because our DAO interface in implementing class already have a save method, our work here focus more on adding
 *      Hibernate code to perform (implement) those tasks.
 *  2.  Remember on the sequence of the Hibernate code: (We already declared SessionFactory object called
 *      sessionFactory) thus:
 *      a.  Open a session: Session session = sessionFactory.openSession();
 *      b.  Begin transaction : session.beginTransaction( );
 *      c.  Saving the new Category: session.save(category);
 *      d.  Commit the transaction: session.getTransaction( ).commit( );
 *      e.  Close the hibernate session: session.close( );
 *      NOTE: transactions ensure that related edits all complete before changes are confirmed or committed to the
 *      database.
 *  NEXT: ENTRY 22: SAVING ENTITIES SERVICE LAYER
 *  GOTO: service/CategoryServiceImpl.java
 *
 *  ENTRY 40: CODING THE GIF DAO
 *  1.  In findById we add Session session = sessionFactory.openSession();
 *  2.  Then we add session.close so it can be copy and pasted in the delete method
 *  3.  Back in the findById method we add Category category = session.get(Category.class, id); and return category
 *  4.  In the delete we start by adding sessin.beginTransaction();
 *  5.  Then we do the business: session.delete(category);
 *  6.  Then session.getTransaction().commit();
 *
 *  */

//16-3:
@Repository
public class CategoryDaoImpl implements CategoryDao {
    //16-2a:
    @Autowired
    private SessionFactory sessionFactory;

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
        //40-1.
        Session session = sessionFactory.openSession();
        //40-3.
        Category category = session.get(Category.class,id);
        //40-2.
        session.close();
        return category;
    }

    @Override
    public void save(Category category) {
        //21-2a:
        Session session = sessionFactory.openSession();
        //21-2b:
        session.beginTransaction();
        //-21-2c:
        session.save(category);
        //21-2d:
        session.getTransaction().commit();
        //21-2e:
        session.close();
    }

    @Override
    public void delete(Category category) {
        //40-1.
        Session session = sessionFactory.openSession();
        //40-4.
        session.beginTransaction();
        //40-5.
        session.delete(category);
        //40-6.
        session.getTransaction().commit();
        //40-2.
        session.close();
    }
}
