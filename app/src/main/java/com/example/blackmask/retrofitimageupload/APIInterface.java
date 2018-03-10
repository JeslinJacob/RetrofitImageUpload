package com.example.blackmask.retrofitimageupload;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Blackma$k on 09/03/2018.
 */

public interface APIInterface {

    @FormUrlEncoded
    @POST("upload.php")
    Call<ImageClass> uploadImage(@Field("title") String title,
                                 @Field("image") String image);
}
