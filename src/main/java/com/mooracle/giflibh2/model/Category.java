package com.mooracle.giflibh2.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

//ENTRY 9; ENTRY 11; ENTRY 13; ENTRY 25;
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
 *
 *  ENTRY 11: ADD JPA ANNOTATIONS
 *  1.  we already set in the DataConfig too pick up any JPA annotations at boot in the entities location
 *  2.  start by adding @Entity annotationto the class Category to make it an entity as the table
 *  3.  add @Id to the id field and @GeneratedValue with startegy GenerationType.IDENTITY
 *  4.  NOTE: if we already mark one field with @Id then every other non-transient fields will be markked as a column
 *      So therefore we'll get every field marked here as a column in the category table so there's no need to include
 *      that @Column on the other fields.
 *  5.  However, there is one exception to this and that is the Gif on gifs field that is a collection. Unless we plan
 *      on marking this field as transient, we'll need to indicate a relationship between Category entity with the Gif
 *      entity. This opens up a whole world within the world of ORM and in particular collection mapping.
 *      NOTE: CHECK THE TEACHER'S NOTES FOR MORE INFO ON THIS!
 *  6.  For now we just need to know about two types of collection mapping, and these line up with relationship that are
 *      very common among database relational data:
 *          -   ONE TO MANY RELATIONSHIP: here one entity (Category object) can be associated with many Gif objects
 *          -   MANY TO ONE RELATIONSHIP: here many Gif objects can be associated to one Category object as entity
 *  7.  Thus we need to give the gifs field @OneToMany herein the gifs field and GOTO gif.java for the opposite
 *  NEXT: ENTRY 12: ADD JPA ANNOTATION TO GIF OBJECT
 *  GOTO: Gif.java to ENTRY 12: ADD JPA ANNOTATION TO GIF
 *
 *  ENTRY 13: FINISHING ADD JPA FOR CATEGORY
 *  1.  We just need one more change before we fire up the application. If we were to run the application now, we will
 *      find that Hibernate hbm2ddl tool would CREATE a separate table to associate GIFs with categories. This often the
 *      best practice. However, for the simplicity purposes of this project we want to include the category ID as a
 *      column in the GIF table. Thus we can associate every GIF object to a category by looking at the category ID
 *      column in the GIF table.
 *  2.  To do this we need to tell Hibernate that each of these GIF objects is a map to the category field associated
 *      object by including a mappedBy element on @OneToMany (mappedBy="category")
 *      NOTE: this is saying that it is the category field in the Gif entity that will be used to map the category of
 *          each GIF. By DEFAULT this wil use the Category class' id field because it has @Id on it and thereby creating
 *          a column name CATEGORY_ID in the GIF table
 *  RUN: you can save all and fire up the database. IF you still runs the database you need to shut it down first by
 *      pressing Ctrl+C.
 *      Then back use this mantra: java -cp h2-1.4.196.jar org.h2.tools.Server
 *      WARNING: MAKE SURE THE PATH IN THE REMOTE SERVER IS CORRECT JUST COPY PASTE THE PATH HERE:
 *      jdbc:h2:tcp://localhost/~/IdeaProjects/giflibrary-hibernate/data/giflibh2
 *      At first the table will be empty but it is about to change WE NEED TO boot run from Spring Gradle plugin!
 *  NEXT: ENTRY 14: FETCHING DATA WITH HIBERNATE IN SPRING
 *  GOTO: CategoryController.java for ENTRY 14
 *
 *  ENTRY 25: CAPTURING FORM VALIDATION ERRORS ENTITY CLASS
 *  1. Head for the name field and make a decision that the value has to be present and that its length must be between
 *      3 to 12 characters.
 *  2.  To do this we add @NotNull which will ensure that it’s not blank,
 *  3.  Add also @Size(min = 3, max = 12)
 *  4.  Next we add validation annotations for the colorCode field. The value must be a valid hexadecimal colorCode as
 *      used in CSS.
 *  5.  We add @NotNull to the colorCode field
 *  6.  The we add @Pattern which we will utilize regular expression thus we add regular expression element
 *      (regexp =”#[0-9a-fA-F]{6}“) which means it starts with # and then the value can be numeric 0 to 9 or
 *      alphabetic range from a - f in either case repeated 6 times.
 *  NEXT: ENTRY 26: CAPTURING FORM VALIDATION ERRORS CONTROLLER
 *  GOTO: CategoryController.java
 * */

@Entity
public class Category {

    //9-4: list of all field;11-3: add Id and Generated Value strategy
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //25-2; 25-3;
    @NotNull
    @Size(min=3, max=12)
    private String name;
    //25-5; 25-6;
    @NotNull
    @Pattern(regexp = "#[0-9a-fA-F]{6}")
    private String colorCode;

    //11-7: 13-2
    @OneToMany(mappedBy = "category")
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
