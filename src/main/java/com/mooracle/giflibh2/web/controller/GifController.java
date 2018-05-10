package com.mooracle.giflibh2.web.controller;

import com.mooracle.giflibh2.model.Gif;
import com.mooracle.giflibh2.service.CategoryService;
import com.mooracle.giflibh2.service.GifService;
import com.mooracle.giflibh2.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        // TODO: Get all gifs
        List<Gif> gifs = new ArrayList<>();

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
    public String addGif(Gif gif, @RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
        // 41-3: Upload new GIF if data is valid
        gifService.save(gif, file);
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
        return "gif/form";
    }

    // Form for editing an existing GIF
    @RequestMapping(value = "/gifs/{gifId}/edit")
    public String formEditGif(@PathVariable Long gifId, Model model) {
        // TODO: Add model attributes needed for edit form

        return "gif/form";
    }

    // Update an existing GIF
    @RequestMapping(value = "/gifs/{gifId}", method = RequestMethod.POST)
    public String updateGif() {
        // TODO: Update GIF if data is valid

        // TODO: Redirect browser to updated GIF's detail view
        return null;
    }

    // Delete an existing GIF
    @RequestMapping(value = "/gifs/{gifId}/delete", method = RequestMethod.POST)
    public String deleteGif(@PathVariable Long gifId) {
        // TODO: Delete the GIF whose id is gifId

        // TODO: Redirect to app root
        return null;
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