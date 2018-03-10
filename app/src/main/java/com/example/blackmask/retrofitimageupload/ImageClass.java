package com.example.blackmask.retrofitimageupload;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Blackma$k on 08/03/2018.
 */

public class ImageClass {

    // these two variables are required to upload the files into server
    @SerializedName("title")
    private String Title;

    @SerializedName("image")
    private String Image;

    // now we need a variable to get the response from the server
    @SerializedName("response")
    private String response;


    public String getResponse() {
        return response;
    }
}


