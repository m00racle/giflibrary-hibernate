package com.mooracle.giflibh2.service;

import com.mooracle.giflibh2.dao.GifDao;
import com.mooracle.giflibh2.model.Gif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/** ENTRY 37: CODING THE GIF SERVICE
 *  1.  We first add @Service on the top of the class declaration to designate as service layer
 *  2.  We add implements GifService interface then it will highlight an error
 *  3.  Alt+enter on the error to produce all available methods
 *  4.  Next we @Autowired a field: private GifDAo called gifDao; (doesn’t exist yet)
 *  5.  In the findAll() method simply return gifDao.findAll() method;
 *  6.  In findById method also the same gifDao.findById and pass the (Long id) parameter
 *  7.  Let’s jump to delete method and add gifDao.delete(Gif gif).
 *  8.  As for the save method we need the service layer to put together this multi-part file data and the Gif @Entity
 *      before persisting the Gif entity.
 *  9.  We’ll use the Gif setBytes method and pass the bytes from the getBytes method of that multipart file parameter.
 *      Thus: gif.setBytes(file.getBytes).
 *  10. NOTE: we need to throw IOException on this one thus we use try catch mechanism.
 *  11. In the catch part we system.err.println(“unable to get byte array from upload file”);
 *  12. Then the Gif gif object will have included the bytes from the file thus: gifDao.save(gif);
 *  PRE-REQ: Create a GifDao interface inside the dao package.
 *
 *  ENTRY 53: Using a Custom Validator on the Uploaded GIF
 *  Go to: /service/GifServiceImpl class
 *  1.  Implements newly added void update method
 *  2.  Inside we need to ensure whether the MultipartFile file is present or not
 *  3.  If present process the file and gif as in the save method
 *  4.  If not (else) make new Gif called oldGif = gifDao.findById(gif.getId());
 *  5.  Then set gif.setBytes(oldGif.getBytes());
 *  6.  Out from if statement then gifDao.save(gif);
 * */

//37-1;
@Service
//37-2;
public class GifServiceImpl implements GifService {
    //37-3;37-4;
    @Autowired
    private GifDao gifDao;

    @Override
    public List<Gif> findAll() {
        //37-5;
        return gifDao.findAll();
    }

    @Override
    public Gif findById(Long id) {
        //37-6;
        return gifDao.findById(id);
    }

    @Override
    public void save(Gif gif, MultipartFile file) {
        //37-10;
        try {
            //37-9;
            gif.setBytes(file.getBytes());

        }catch (IOException ioe){
            //37-11;
            System.err.println("unable to get byte array from upload file");
        }
        //37-12;
        gifDao.save(gif);
    }

    @Override
    public void update(Gif gif, MultipartFile file) {
        //53-2.
        if(gif.getFile()!= null && !file.isEmpty()){
            //53-3.
            try {
                gif.setBytes(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            //53-4.
            Gif oldGif = gifDao.findById(gif.getId());
            //53-6.
            gif.setBytes(oldGif.getBytes());
        }
        //53-7.
        gifDao.save(gif);
    }

    @Override
    public void delete(Gif gif) {
        //37-7;
        gifDao.delete(gif);
    }
}
