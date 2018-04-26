package com.mooracle.giflibh2.model;


import java.util.ArrayList;
import java.util.List;

//ENTRY 9;
/** ENTRY 9: CREATING CATEGORY POJO MODEL
 *  1.  This is where we model what a category should looks like in terms of attributes and behavior as a POJO
 *  2.  The user will create their own category and also use it to filter the submitted gif images
 *  3.  Thus in this initial commit the construction method for this class is left empty of in default construction
 *  4.  First we add all fields related to a Category POJO. Some notes here:
 *      a.  String colorCode will be defined more on the ENUM color
 *      b.  for the List of Gif we can directly create Java Class of Gif.java from there by pressing alt+enter
 *  5.  Then we make default constructor for the Category POJO by selecting none in the alt+insert options
 *  6.  lastly we create all getters for all available fields and setters for all available fields except List gifs
 *      since we want it to be created empty ArrayList in the beginning and to set it it will using the add method of
 *      an ArrayList object
 *  NEXT: ENTRY 10: CREATING A GIF POJO MODEL
 *  PRE-REQ:
 *      1. IF YOU ALREADY MAKE CLASS FROM STEP 4b YOU DO NOT HAVE ANY PRE-REQ
 *      2.IF YOU HAVE NOT then make a new Java Class inside the model package and name it Gif
 *  GOTO: Gif.java for ENTRY 10
 *  */

public class Category {
    //9-4: list of all fields
    private Long id;
    private String name;
    private String colorCode;
    private List<Gif> gifs = new ArrayList<>();

    //9-5: creating default constructor

    public Category() {
    }

    //9-6: set getters for all and setters for all except Gifs List

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public List<Gif> getGifs() {
        return gifs;
    }

}
