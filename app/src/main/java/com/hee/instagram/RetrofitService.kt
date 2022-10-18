package com.hee.instagram

import retrofit2.Call
import retrofit2.http.*

class User(
    val username: String, val token: String, val id: Int
)

class InstaPost(
    val content: String, val image:String, val owner_profile: OwnerProfile
)

class OwnerProfile(
    val username: String, val image: String
)

interface RetrofitService {

    @GET("instagram/post/list/all/")
    fun getInstagramPosts(
    ): Call<ArrayList<InstaPost>>

    @POST("user/signup/")
    @FormUrlEncoded
    fun instaJoin(
        @FieldMap params: HashMap<String, Any>
    ): Call<User>

    @POST("user/login/")
    @FormUrlEncoded
    fun instaLogin(
        @FieldMap params: HashMap<String, Any>
    ): Call<User>
}