package com.mooracle.giflibh2.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

//ENTRY 10; ENTRY 12
/** ENTRY 10: CREATING A GIF POJO MODEL
 *  1.  HERE we will mode a Gif POJO which all fields are included from those field there are some fields that need
 *      some additional note such as:
 *          a.  bytes is the field to denotes the size of a gif image
 *          b.  dateUpload is the imported package to denotes time and also will be used to measure the age of the gif
 *              image resides in the website by differentiating the date to now
 *  2.  We start by listing all fields related to the Gif POJO
 *  3.  Then we make default constructor method for the class by selecting none in the alt+insert
 *  4.  For this POJO we need to define the age on how long a gif image has reside in the website by differentiating
 *      the dateUploaded to now and set the time unit appropriately. Thus we need this method
 *  5.  Build getters and setters for all fields
 *  NEXT:   we just gaing to copy the web package content because it is content and still has many to dos
 *          But for the color Enum I will create using create new Java Class but select Enum in the drop down option and
 *          name it Color Please refer to the notes inside the Enum since I will not add any entry there. Make sure to
 *          understand the function of the Color method inside the Enum
 *          - Next we will see if copying packages from other project can works on our's There are some notes on this:
 *          a.  even if the package declaration adapted to our's the import is not
 *          b.  so be careful in the paste packages to make sure no Object is wrongly imported. In my case I need to
 *              refer from importing from teamtreehouse and change it to mooracle
 *          c.  the fastest way to do this is by re-typing the object name (in this case fortunatelt the object class
 *              name between teamtreehouse and mooracle is similar)
 *  MOO: CHECK THE DATABASE SERVER FROM TERMININAL IT IS WORKING THE CONNECTION IS ALIVE!
 *  NEXT: ENTRY 11: ADD JPA ANNOTATIONS
 *  GOTO: Category.java for ENTRY 11
 *
 *  ENTRY 12: ADD JPA ANNOTATION TO GIF
 *  --> we wanto to add @ManyToOne on the Category field here as opposed to Category.java
 *  1.  Here we need to add @Entity in the Class Gif
 *  2.  Then we add @Id in the id field as primary key.
 *  3.  Then we also add @GeneratedValue with  strategy = Generation.Type. IDENTITY
 *  4.  Now this is IMPORTANT on the bytes field we are going to use the @Lob (learn more) which allows us to stor the
 *      GIF image file data into the database using the database provider's implementation of a large object (which
 *      exactly what Lob or Larger Object stands for).
 *  NEXT: ENTRY 13: FINISHING ADD JPA ANNOTATION FOR CATEGORY
 *  GOTO: Category.java
 *
 *  ENTRY 50: Using a Custom Validator on the Uploaded GIF
 *  We will start from adding the MultipartFile object as the field in the Gif @Entity class:
 *  1.  We go to the /model/Gif class and go to the field declaration part
 *  2.  We add private MultipartFile and name it file.
 *  3.  Then we add @Transient to the file field to ensure it will not persist in database.
 *  4.  Build getter and setter for file.
 * Pre-req: we need to make a new package name validator on the com.mooracle.giflibh2 package and name it validator.
 *      Inside we make a Java Class called GifValidator.java
 * */

//12-1:
@Entity
public class Gif {
    //10-2: list of all fields related to the Gif POJO; 12-2;12-3
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //12-4:
    @Lob
    private byte[] bytes;

    //50-1.
    @Transient
    private MultipartFile file;

    private String description;

    //12-->
    @ManyToOne
    private Category category;
    private LocalDateTime dateUpLoaded = LocalDateTime.now();
    private String username = "You";
    private boolean favorite;
    private String hash;

    //10-3: make default constructor
    public Gif() {
    }

    //10-4: set the age of the gif image
    public String getTimeSinceUploaded() {
        String unit = "";
        LocalDateTime now = LocalDateTime.now();
        long diff;
        if((diff = ChronoUnit.SECONDS.between(dateUpLoaded,now))<60){
            unit = "secs";
        } else if((diff = ChronoUnit.MINUTES.between(dateUpLoaded,now))<60){
            unit = "mins";
        } else if((diff = ChronoUnit.HOURS.between(dateUpLoaded,now))<24){
            unit = "hours";
        } else if((diff = ChronoUnit.DAYS.between(dateUpLoaded,now))<30){
            unit = "days";
        } else if((diff = ChronoUnit.MONTHS.between(dateUpLoaded,now))<12){
            unit = "months";
        } else {
            diff = ChronoUnit.YEARS.between(dateUpLoaded,now);
            unit = "years";
        }
        return String.format("%d %s", diff, unit);
    }

    //10-5: getters and setters for all fields

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getDateUpLoaded() {
        return dateUpLoaded;
    }

    public void setDateUpLoaded(LocalDateTime dateUpLoaded) {
        this.dateUpLoaded = dateUpLoaded;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
