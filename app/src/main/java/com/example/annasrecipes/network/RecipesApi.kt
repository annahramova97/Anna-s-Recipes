package com.example.annasrecipes.network

import com.example.annasrecipes.model.Base
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesApi {
    //complexSearch?apiKey=63a7cf64312240efb7e2f6d634b4fb7e&query=pasta&number=1&addRecipeInformation=true
    @GET("/complexSearch")
    fun getRecipyDetails(@Query("query") title: String,
                         @Query("number")number: Number ,
                         @Query("info")info: Boolean ,
                          @Query("apiKey")key: String): Call<Base>



}