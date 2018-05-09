package com.mooracle.giflibh2.dao;

import com.mooracle.giflibh2.model.Gif;

import java.util.List;

/** ENTRY 38: CODING THE GIF DAO
 *  1.  We’ll include similar methods as we did in the CategoryDao. In fact these methods will be the same as the
 *      GifService interface except we wont include multi-part file parameter for the save method.
 *  2.  So we add List<Gif> findAll();
 *  3.  We add Gif findById(Long id);
 *  4.  We add void save(Gif gif); since multi-part file already combined to gif in GifService
 *  5.  And lastly we add void delete(Gif gif);
 *  Pre-req: make the GifDaoImpl class inside the dao package and go there
 *
 * */

public interface GifDao {
    //38-2;
    List<Gif> findAll();
    //38-3;
    Gif findById(Long id);
    //38-4;
    void save(Gif gif);
    //38-5;
    void delete(Gif gif);
}
