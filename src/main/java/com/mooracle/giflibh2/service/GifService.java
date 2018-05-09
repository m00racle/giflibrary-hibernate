package com.mooracle.giflibh2.service;

import com.mooracle.giflibh2.model.Gif;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/** ENTRY 36: CODING THE GIF SERVICE
 *  1.  Let’s add methods like what we did in the CategoryService interface
 *  2.  Remember the methods are only declared without any body inside
 *  3.  First we make findAll() which return a List<Gif> object
 *  4.  Then we make findById method which accepts (long id) returns Gif object
 *  5.  Then save method which accepts two parameters (Gif gif, MultipartFile file) returns void
 *  6.  Lastly, delete method accepts (Gif gif) returns void
 *  Note that we include MultipartFile in the parameter because the Gif @Entity does not include the MultipartFile data
 *      in the field declaration which our GifController will receive.
 *  Pre-req: make implementation of the GifService interface inside the service package and name it GifServiceImpl.java
 *          class.
 * */

public interface GifService {
    //36-3;
    List<Gif> findAll();
    //36-4;
    Gif findById(Long id);
    //36-5;
    void save(Gif gif, MultipartFile file);
    //36-6;
    void delete(Gif gif);
}
