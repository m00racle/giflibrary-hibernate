package com.mooracle.giflibh2.web.controller;

import com.mooracle.giflibh2.model.Gif;
import com.mooracle.giflibh2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

//ENTRY 33; ENTRY 35;
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
 * */

@Controller
public class GifController {

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

    // Single GIF page
    @RequestMapping("/gifs/{gifId}")
    public String gifDetails(@PathVariable Long gifId, Model model) {
        // TODO: Get gif whose id is gifId
        Gif gif = null;

        model.addAttribute("gif", gif);
        return "gif/details";
    }

    // GIF image data
    @RequestMapping("/gifs/{gifId}.gif")
    @ResponseBody
    public byte[] gifImage(@PathVariable Long gifId) {
        // TODO: Return image data as byte array of the GIF whose id is gifId
        return null;
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

    // Upload a new GIF
    @RequestMapping(value = "/gifs", method = RequestMethod.POST)
    public String addGif(@RequestParam MultipartFile file) {
        // TODO: Upload new GIF if data is valid

        // TODO: Redirect browser to new GIF's detail view
        return null;
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
    @RequestMapping(value = "/gifs/{dgifI}/edit")
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