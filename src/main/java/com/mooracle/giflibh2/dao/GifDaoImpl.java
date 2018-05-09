package com.mooracle.giflibh2.dao;

import com.mooracle.giflibh2.model.Gif;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/** ENTRY 39: CODING THE GIF DAO
 *  1.  First we need to add @Repository in the class declaration
 *  2.  Then we need to implements the GifDao interface
 *  3.  Responding to the error (alt+enter) we add all the methods from GifDao interface
 *  4.  Since this is the closest layer to the database we’ll invoke that Hibernate code
 *  5.  First we @Autowired the private SessionFactory and name it sessionFactory
 *  6.  In findAll method we’ll open the session: Session session = sessionFactory.openSession;
 *  7.  Then before all of that we will do session.close; so we can copy paste it to every method.
 *  8.  Remember to use the latest Criteria (as showed in the CategoryDao) to return a List<Gif>
 *  9.  Next the findById method this should be done for the CategoryDaoImpl findById also
 *  10. We use Gif gif = session.get(Gif.class, id); since the entity we want is a Gif class and we use Gif id as the
 *      identifier to fetch the Gif.
 *  11. Next the save method since it was a write operation we need to make transaction and commits
 *  12. We use session.beginTransaction; to start the transaction
 *  13. We use session.save(gif); as the main activity
 *  14. Then we use session.getTransaction.commit; to commit the transaction before closing
 *  15. For the delete just repeat the save method except use session.delete(gif) instead save.
 *  GOTO: com/mooracle/giflibh2/dao/CategoryDaoImpl.java to add findById and delete method
 *
 * */

//39-1.39-2.
@Repository
public class GifDaoImpl implements GifDao {
    //39-3.39-5.
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Gif> findAll() {
        //39-6.
        Session session = sessionFactory.openSession();
        //39-8.
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Gif> gifCriteriaQuery = criteriaBuilder.createQuery(Gif.class);
        gifCriteriaQuery.from(Gif.class);
        List<Gif> gifs = session.createQuery(gifCriteriaQuery).getResultList();
        //39-7.
        session.close();
        return gifs;
    }

    @Override
    public Gif findById(Long id) {
        //39-6.
        Session session = sessionFactory.openSession();
        //39-10
        Gif gif = session.get(Gif.class, id);
        //39-7.
        session.close();
        return gif;
    }

    @Override
    public void save(Gif gif) {
        //39-6.
        Session session = sessionFactory.openSession();
        //39-12.
        session.beginTransaction();
        //39-13.
        session.save(gif);
        //39-14.
        session.getTransaction().commit();
        //39-7.
        session.close();
    }

    @Override
    public void delete(Gif gif) {
        //39-6.
        Session session = sessionFactory.openSession();
        //39-15.
        session.beginTransaction();
        session.delete(gif);
        session.getTransaction().commit();
        //39-7.
        session.close();
    }
}
