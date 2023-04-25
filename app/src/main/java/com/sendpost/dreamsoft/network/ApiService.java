package com.sendpost.dreamsoft.network;

import com.sendpost.dreamsoft.model.LanguageModel;
import com.sendpost.dreamsoft.model.SettingModel;
import com.sendpost.dreamsoft.responses.FrameResponse;
import com.sendpost.dreamsoft.responses.HomeResponse;
import com.sendpost.dreamsoft.responses.SimpleResponse;
import com.sendpost.dreamsoft.responses.UserResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("{API_KEY}/settings")
    Call<List<SettingModel>> getSettings(@Path("API_KEY") String apikey);

    @GET("{API_KEY}/language")
    Call<List<LanguageModel>> getLanguages(@Path("API_KEY") String apikey);

    @GET("{API_KEY}/profile")
    Call<UserResponse> geUserProfile(@Path("API_KEY") String apikey,
                                     @Query("user_id") String user_id);

    @GET("{API_KEY}/userposts")
    Call<UserResponse> getUserPosts(@Path("API_KEY") String apikey,
                                    @Query("user_id") String user_id);

    @FormUrlEncoded
    @POST("{API_KEY}/login")
    Call<UserResponse> login(@Path("API_KEY") String apikey,
                             @Field("social") String social,
                             @Field("social_id") String social_id,
                             @Field("auth_token") String auth_token,
                             @Field("email") String email,
                             @Field("number") String number,
                             @Field("profile_pic") String profile_pic,
                             @Field("name") String name,
                             @Field("device_token") String device_token);

    @Multipart
    @POST("{API_KEY}/updateProPic")
    Call<UserResponse> updateProfilePic(@Path("API_KEY") String apikey,
                                        @Part("user_id") RequestBody user_id,
                                        @Part MultipartBody.Part image);

    @Multipart
    @POST("{API_KEY}/uploadspost")
    Call<UserResponse> uploadUserPost(@Path("API_KEY") String apikey,
                                      @Part("user_id") RequestBody user_id,
                                      @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("{API_KEY}/updateUserProfile")
    Call<UserResponse> updateProfile(@Path("API_KEY") String apikey,
                                     @Field("user_id") String user_id,
                                     @Field("name") String name,
                                     @Field("email") String email,
                                     @Field("number") String number,
                                     @Field("address") String address,
                                     @Field("website") String website,
                                     @Field("parent_id") String parent_id
                                     );

    @FormUrlEncoded
    @POST("{API_KEY}/activeuser")
    Call<UserResponse> activeuser(@Path("API_KEY") String apikey,
                                     @Field("id") String user_id,
                                     @Field("mobile") String number,
                                     @Field("wallet") String wallet);

    @FormUrlEncoded
    @POST("{API_KEY}/addcontact")
    Call<UserResponse> addContact(@Path("API_KEY") String apikey,
                                  @Field("user_id") String user_id,
                                  @Field("number") String email,
                                  @Field("message") String number);

    @FormUrlEncoded
    @POST("{API_KEY}/updateUserSubscription")
    Call<UserResponse> updateSubscription(@Path("API_KEY") String apikey,
                                          @Field("user_id") String user_id,
                                          @Field("type") String type,
                                          @Field("subscription_id") String subscription_id,
                                          @Field("transaction_id") String transaction_id,
                                          @Field("promocode") String promocode);

    @GET("{API_KEY}/userbusinessdetail")
    Call<UserResponse> getBusinessDetail(@Path("API_KEY") String apikey,
                                         @Query("user_id") String user_id,
                                         @Query("id") String id);

    @GET("{API_KEY}/userbusiness")
    Call<UserResponse> getUserBusiness(@Path("API_KEY") String apikey,
                                       @Query("user_id") String user_id);


    @GET("{API_KEY}/userinvitelist")
    Call<UserResponse> getInvitedUser(@Path("API_KEY") String apikey,
                                      @Query("user_id") String user_id);


    @GET("{API_KEY}/getactiveuser")
    Call<UserResponse> getActiveuser(@Path("API_KEY") String apikey,
                                       @Query("id") String user_id);

    @GET("{API_KEY}/pointhistory")
    Call<UserResponse> getPointhistory(@Path("API_KEY") String apikey,
                                       @Query("id") String user_id);

    @GET("{API_KEY}/getpinpoint")
    Call<UserResponse> getpinpoint(@Path("API_KEY") String apikey,
                                       @Query("id") String user_id);

    @Multipart
    @POST("{API_KEY}/adduserbusiness")
    Call<UserResponse> addBusiness(@Path("API_KEY") String apikey,
                                   @Part MultipartBody.Part image,
                                   @Part("id") RequestBody id,
                                   @Part("user_id") RequestBody user_id,
                                   @Part("name") RequestBody name,
                                   @Part("number") RequestBody number,
                                   @Part("email") RequestBody email,
                                   @Part("website") RequestBody website,
                                   @Part("address") RequestBody address,
                                   @Part("whatsapp") RequestBody whatsapp,
                                   @Part("facebook") RequestBody facebook,
                                   @Part("youtube") RequestBody youtube,
                                   @Part("instagram") RequestBody instagram,
                                   @Part("about") RequestBody about);

    @FormUrlEncoded
    @POST("{API_KEY}/deletebusiness")
    Call<SimpleResponse> deleteUserBusiness(@Path("API_KEY") String apikey,
                                            @Field("id") String id);

    @GET("{API_KEY}/homedata")
    Call<HomeResponse> getHomeData(@Path("API_KEY") String apikey);

    @GET("{API_KEY}/imagedata")
    Call<HomeResponse> getImageData(@Path("API_KEY") String apikey);

    @GET("{API_KEY}/dailydata")
    Call<HomeResponse> getdailyData(@Path("API_KEY") String apikey);

    @GET("{API_KEY}/dailynewdata")
    Call<HomeResponse> getdailynewData(@Path("API_KEY") String apikey);

    @GET("{API_KEY}/videocategory")
    Call<HomeResponse> getVideoCategories(@Path("API_KEY") String apikey);

    @GET("{API_KEY}/greetingdata")
    Call<HomeResponse> getGreetingData(@Path("API_KEY") String apikey);

    @GET("{API_KEY}/subscriptions")
    Call<HomeResponse> getSubscriptions(@Path("API_KEY") String apikey);

    @GET("{API_KEY}/businesscards")
    Call<HomeResponse> getBusinessCards(@Path("API_KEY") String apikey);


    @GET("{API_KEY}/premiumpostsbycategory/{TYPE}")
    Call<HomeResponse> getPremiumPostByCategory(@Path("API_KEY") String apikey,
                                                @Path("TYPE") String type);

    @GET("{API_KEY}/updatepostviews")
    Call<SimpleResponse> updatePostViews(@Path("API_KEY") String apikey,
                                         @Query("id") String id,
                                         @Query("type") String type);

    @GET("{API_KEY}/cheakPromo")
    Call<HomeResponse> cheakPromo(@Path("API_KEY") String apikey,
                                         @Query("code") String code);

    @GET("{API_KEY}/categoriesbypage")
    Call<HomeResponse> getCategoriesByPage(@Path("API_KEY") String apikey,
                                           @Query("page") int page,
                                           @Query("type") String type,
                                           @Query("search") String search);

    @GET("{API_KEY}/postsbypage")
    Call<HomeResponse> getPostByPage(@Path("API_KEY") String apikey,
                                     @Query("page") int page,
                                     @Query("type") String type,
                                     @Query("language") String language,
                                     @Query("post_type") String post_type,
                                     @Query("item_id") String item_id,
                                     @Query("search") String search);

    @GET("{API_KEY}/frames")
    Call<FrameResponse> getFrames(@Path("API_KEY") String apikey);

    @GET("{API_KEY}/userframes")
    Call<UserResponse> getUserFrames(@Path("API_KEY") String apikey,
                                     @Query("user_id") String user_id);

    @GET("{API_KEY}/stickercategory")
    Call<FrameResponse> getStickersByCategory(@Path("API_KEY") String apikey);

    @FormUrlEncoded
    @POST("{API_KEY}/deletepost")
    Call<SimpleResponse> deleteUserPost(@Path("API_KEY") String apikey,
                                            @Field("id") String id);

    @FormUrlEncoded
    @POST("{API_KEY}/createuserbusinesscard")
    Call<SimpleResponse> createBusinessCard(@Path("API_KEY") String apikey,
                                            @Field("card_id") String user_id,
                                            @Field("business_id") String business_id);

}
