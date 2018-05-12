package com.mooracle.giflibh2.web.controller;

import com.mooracle.giflibh2.model.Gif;
import com.mooracle.giflibh2.service.CategoryService;
import com.mooracle.giflibh2.service.GifService;
import com.mooracle.giflibh2.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

//ENTRY 33; ENTRY 35; ENTRY 41;
/** ENTRY 33: A FILE UPLOAD FROM IN THYMELEAF
 *  1.  Scroll down to the method that renders a form for adding a new gif and it’s called formNewGif
 *  2.  We start by adding the “gif” attribute. model.addAtribute(“gif”, new Gif());
 *  3.  Next we need to add the current list of categories. To do this we need to @Autowired the CategoryService
 *      interface.
 *  4.  We scroll up to the top of the class GifController.java and add @Autowired private CategoryService and name it
 *      categoryService.
 *  5.  Go back to the formNewGif method and add that list of categories.
 *      model.addAttribute(“categories”, categoryService.findAll())
 *
 *  ENTRY 35: CODING THE GIF SERVICE
 *  1.  Open the structure tool window (alt+7 of |view|tool window|structure) then go to addGif method.
 *  2.  In order to capture the file data upon upload we need to add MultipartFile parameter to this addGif method.
 *      Let’s just name it file: addGif(MultipartFile file)
 *  3.  To tell Spring framework that this is a value that will be passed via the HTTP request as parameter in the
 *      payload we’ll use @RequestParam.
 *  4.  The @RequestParam offers the name attribute that we can specify. But in this case we will use the name same as
 *      the name of the MultipartFile which is file. This is because we also use the name file in the form.html.:
 *  5.  Thus the added parameter will be addGif(@RequestParam MultipartFile file)
 *  PRE-REQ:
 *  1.  Create a new Interface in the com.mooracle.giflibh2.service name it GifService
 *  GOTO: com/mooracle/giflibh2/service/GifService.java
 *
 *  ENTRY 41: Persisting a GIF From the Controller
 *  1.  First @Autowired private GifService and name it gifService.
 *  2.  Go to the addGif method to add Gif gif as parameter and finish the uploading work
 *  3.  Invoke the gifService.save method and passed in (Gif gif, MultipartFile file)
 *  4.  After we save we should include a flash message indicating that save completed
 *  5.  First add RedirectAttrbute in the addGif parameter and name it redirectAttribute.
 *  6.  Then we call
 *      redirectAttribute.addFlashAttribute(“flash”, new FlashMessage(“Gif Added”, FlashMessage.Status.SUCCESS));
 *  7.  As for the redirect this time it will not be to the list of gif but to the newly added gif.
 *  8.  Thus it will be return String.format(“redirect:/gif/%s”, gif.getId())
 *  9.  The HIbernate session save method will takes care of updating the id field of the gif for us.
 *  10. Next we need to set the gifDetail method to view the correct gif (in this case the newly added)
 *  11. We need to define the Gif gif from null to gifService.findById and passed (gifId)
 *      NOTE: (gifId) is comes from /gif/{gifId}/ @RequestMapping. Other things already set by Chris.
 *  12. Next we move to the rendering of actual image. This will be used so that the page can view actual .gif
 *      image file.
 *  13. We go to gifImage method and fetch the gif the same way as gifDetail method
 *  14. Next we need to return the gif byte array: return gifService.findById(gifId).getBytes();
 *  MORE NOTES ON THE GOOGLE DRIVE STUDY NOTES.
 *
 *  ENTRY 47: UPDATING GIF
 *  1.  just like Updating Category first we need to clone the same attributes from the formNewGif method
 *      NOTE: we don't make new Gif object rather fetch the existing Gif using @PathVariable gifId
 *
 *  ENTRY 49: UPDATING GIF
 *  /web/controller/GifController.java
 *  1.  modify the existing formNewGif to include proper newly created attributes, action, heading, submit
 *  2.  modify the existing formEditGif to include proper attributes action, heading, submit
 *  3.  go to updateGif method and build it appropriately similar to addGif method
 *      add these parameters Gif gif, @PathVariable Long gifId, RedirectAttributes redirectAttributes
 *  4.  we have not build any validation for it so let's just fire away
 *  5.  just be careful with return String.format("redirect:/gifs/%s", gifId)
 *  6.  WARNING: for index ("/") need to Get all Gifs using gifService.findAll()
 *
 *  ENTRY 54: Using a Custom Validator on the Uploaded GIF
 *  Now we go to the /web/controller/GifController.java class
 *  1.  Since now MultipartFile file is part of @Transient field of Gif @Entity we can delete
 *      the @RequestParam MultipartFile file in addGif method parameter and @Valid Gif gif
 *  2.  Same as in the updateGif: delete @RequestParam MultipartFile file and @Valid Gif gif
 *  3.  For addGif and updateGif mehtods add BindingResult result parameter
 *  4.  For addGif and updateGif method build similar if(result.hasErrors()) as in CategoryController
 *  5.  However, for addGif if there is error : return “redirect:/upload”
 *  6.  While, updateGif : return String.format(“redirect:/gifs/%s/edit”, gifId);
 *  7.  For addGif method outside the if statement call gifService.save(gif, gif.getFile());
 *  8.  For updateGif call gifService.update(gif, gif.getFile());
 *  9.  Both in the end : return String.format(“redirect:/gifs/%s”, gif.getId());
 *
 *  ENTRY 63: DELETING GIF
 *  GOTO: deleteGif method
 * */

@Controller
public class GifController {

    //41-1.
    @Autowired
    private GifService gifService;
    //33-4;
    @Autowired
    private CategoryService categoryService;
    // Home page - index of all GIFs
    @RequestMapping("/")
    public String listGifs(Model model) {
        // 49-6: Get all gifs
        List<Gif> gifs = gifService.findAll();

        model.addAttribute("gifs", gifs);
        return "gif/index";
    }

    // Single GIF page.41-10.
    @RequestMapping("/gifs/{gifId}")
    public String gifDetails(@PathVariable Long gifId, Model model) {
        // 41-11: Get gif whose id is gifId
        Gif gif = gifService.findById(gifId);

        model.addAttribute("gif", gif);
        return "gif/details";
    }

    // GIF image data.41-12.
    @RequestMapping("/gifs/{gifId}.gif")
    @ResponseBody
    public byte[] gifImage(@PathVariable Long gifId) {
        // 41-14.41-15: Return image data as byte array of the GIF whose id is gifId
        return gifService.findById(gifId).getBytes();
    }

    // Favorites - index of all GIFs marked favorite
    @RequestMapping("/favorites")
    public String favorites(Model model) {
        // TODO: Get list of all GIFs marked as favorite
        List<Gif> faves = new ArrayList<>();

        model.addAttribute("gifs",faves);
        model.addAttribute("username","Chris Ramacciotti"); // Static username
        return "gif/favorites";
    }

    // Upload a new GIF.41-2.41.5
    @RequestMapping(value = "/gifs", method = RequestMethod.POST)
    public String addGif(@Valid Gif gif, RedirectAttributes redirectAttributes, BindingResult result) {
        // 41-3: Upload new GIF if data is valid;54-4;
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.gif",
                    result);
            redirectAttributes.addFlashAttribute("gif", gif);
            //54-5.
            return "redirect:/upload";
        }
        //54-7;
        gifService.save(gif, gif.getFile());
        //41-6.
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("gif uploaded",
                FlashMessage.Status.SUCCESS));
        // 41-8: Redirect browser to new GIF's detail view
        return String.format("redirect:/gifs/%s", gif.getId());
    }

    // Form for uploading a new GIF
    @RequestMapping("/upload")
    public String formNewGif(Model model) {
        // 33-2: Add model attributes needed for new GIF upload form
        model.addAttribute("gif", new Gif());
        //33-5;
        model.addAttribute("categories", categoryService.findAll());
        //49-1.
        model.addAttribute("action", "/gifs");
        model.addAttribute("heading", "Upload");
        model.addAttribute("submit", "Upload");
        return "gif/form";
    }

    // Form for editing an existing GIF
    @RequestMapping(value = "/gifs/{gifId}/edit")
    public String formEditGif(@PathVariable Long gifId, Model model) {
        // 47-1: Add model attributes needed for edit form
        model.addAttribute("gif", gifService.findById(gifId));
        model.addAttribute("categories", categoryService.findAll());
        //49-2.
        model.addAttribute("action", String.format("/gifs/%s", gifId));
        model.addAttribute("heading", "Edit Gif");
        model.addAttribute("submit", "Update");
        return "gif/form";
    }

    // 49-3: Update an existing GIF
    @RequestMapping(value = "/gifs/{gifId}", method = RequestMethod.POST)
    public String updateGif(Gif gif, @PathVariable Long gifId, RedirectAttributes redirectAttributes,
                            BindingResult result) {
        // 49-4: Update GIF if data is valid;54-4;
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.gif",
                    result);
            redirectAttributes.addFlashAttribute("gif", gif);
            //54-6;
            return String.format("redirect:/gifs/%s/edit", gifId);
        }
        //54-8;
        gifService.update(gif, gif.getFile());
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Gif updated",
                FlashMessage.Status.SUCCESS));
        // 49-5: Redirect browser to updated GIF's detail view
        return String.format("redirect:/gifs/%s", gifId);
    }

    /**ENTRY 64: Delete an existing GIF
    * here we will fetch the gif with gifId and then delete it
    *
    * */
    @RequestMapping(value = "/gifs/{gifId}/delete", method = RequestMethod.POST)
    public String deleteGif(@PathVariable Long gifId, RedirectAttributes redirectAttributes) {
        // 63: Delete the GIF whose id is gifId and add Flash message about it
        gifService.delete(gifService.findById(gifId));
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Gif Deleted!",
                FlashMessage.Status.SUCCESS));
        // 63: Redirect to app root
        return "redirect:/";
    }

    // Mark/unmark an existing GIF as a favorite
    @RequestMapping(value = "/gifs/{gifId}/favorite", method = RequestMethod.POST)
    public String toggleFavorite(@PathVariable Long gifId) {
        // TODO: With GIF whose id is gifId, toggle the favorite field

        // TODO: Redirect to GIF's detail view
        return null;
    }

    // Search results
    @RequestMapping("/search")
    public String searchResults(@RequestParam String q, Model model) {
        // TODO: Get list of GIFs whose description contains value specified by q
        List<Gif> gifs = new ArrayList<>();

        model.addAttribute("gifs",gifs);
        return "gif/index";
    }
}