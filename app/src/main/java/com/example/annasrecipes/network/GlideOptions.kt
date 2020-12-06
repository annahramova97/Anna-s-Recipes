package com.example.annasrecipes.network

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

    @GlideModule
    class GlideOptions: AppGlideModule() {

        fun downloadImage(fragment: Fragment, myUrl: String, imageView: ImageView) {
            Glide.with(fragment)
                .load(myUrl)
                .fitCenter()
                .into(imageView)
        }
    }
