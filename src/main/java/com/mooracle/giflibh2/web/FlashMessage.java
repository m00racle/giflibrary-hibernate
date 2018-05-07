package com.mooracle.giflibh2.web;

/** ENTRY 30: ENCAPSULATING A FLASH MESSAGE
 *  1.  Create a Private String field and name it message to put the message text.
 *  2.  For the status we need to create an enum that hold two options (success or failure)
 *  3.  So we private Status name it status.
 *  4.  Then we create an enum out of it: public static enum Status.
 *  5.  When creating a Java enum the convention is to write the options by ALL CAPS
 *  6.  Thus we code SUCCESS and FAILURE.
 *  7.  Next we create a constructor for the FlashMessage class to enable FlashMessage initialization. Both fields
 *      must be present and passed in as arguments
 *  8.  Next we need getters for both fields. We don’t need setters since it will only set once in constructor.
 * */
public class FlashMessage {
    //30-1;
    private String message;
    private Status status;

    //30-7;


    public FlashMessage(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    //30-4;
    public enum Status{
        //30-6;
        SUCCESS, FAILURE
    }

    //30-8;

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }
}
