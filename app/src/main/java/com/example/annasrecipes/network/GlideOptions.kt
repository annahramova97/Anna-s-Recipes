package com.example.annasrecipes.network

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.annasrecipes.RecipesDetails

@GlideModule
    class GlideOptions: AppGlideModule() {

        fun downloadImage(fragment: RecipesDetails, myUrl: String, imageView: ImageView) {
            Glide.with(fragment)
                .load(myUrl)
                .fitCenter()
                .into(imageView)
        }
    }
