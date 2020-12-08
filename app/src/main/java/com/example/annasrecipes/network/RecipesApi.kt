package com.example.annasrecipes.network

import com.example.annasrecipes.model.Base
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesApi {
    //complexSearch?apiKey=63a7cf64312240efb7e2f6d634b4fb7e&query=pasta&number=1&addRecipeInformation=true
    @GET("complexSearch?apiKey=63a7cf64312240efb7e2f6d634b4fb7e&number=1&addRecipeInformation=true")
    fun getRecipyDetails(@Query("query") title: String
                       ): Call<Base>



}