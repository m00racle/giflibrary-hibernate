package com.mooracle.giflibh2.validator;

import com.mooracle.giflibh2.model.Gif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/** ENTRY 51: Using a Custom Validator on the Uploaded GIF
    Next we need to make the class of GifValidator: (this is a custom validator class
    1.  Add @Component to the class declaration for Spring to scan it as framework component
    2.  This class will implements interface called Validator make sure to also import all methods
    WARNING: MAKE SURE CHOOSE VALIDATOR FROM SPRING NOT JAVAVX
    3.  The we need to @Autowired the private Validator field and name it validator
    4.  First we modify the supports method and return boolean Gif.class.equals(clazz);
    5.  The important method here is the validate method which specify what and how to validate
    6.  First we make sure the target object is casted Gif gif = (Gif)target;
    7.  Note we only validate newly uploaded gif which has id==null.
    8.  Thus existing gif can be updated without uploading new gif
    9.  Thus if(gif.getId() == null && (gif.getFile() == null || gif.getFile().isEmpty()))
    10. Then errors.rejectValue(“file”, “file.required”, “Please choose a file to upload”);
    11. Next we need to validate description that cannot be empty or filled with white space only
    12. Thus
    ValidationUtils.rejectIfEmptyOrWhiteSpace(errors, “description”,”description.empty”,”Please enter a description”);
    13. The same applies also for Category
    14. Thus ValidationUtils.rejectIfEmpty(errors,”category”,”category.empty”,”Please choose a category”);
 */

//51-1.
    @Component
public class GifValidator implements Validator {
        //51-3.
    @Autowired
    private Validator validator;

    @Override
    public boolean supports(Class<?> clazz) {
        //51-4.
        return Gif.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //51-6.
        Gif gif = (Gif)target;
        //51-7.51-9
        if(gif.getId() == null && (gif.getFile() == null || gif.getFile().isEmpty())){
            //51-10.
            errors.rejectValue("file", "file.required", "Please choose a file to Upload");
        }
        //5-12.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.empty",
                "Please enter a description");
        //51-14.
        ValidationUtils.rejectIfEmpty(errors, "category", "category.empty",
                "Please choose a category");
    }
}
