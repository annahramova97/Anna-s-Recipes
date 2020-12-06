package com.example.annasrecipes.touch

import android.content.Intent

interface RecipesTouchHelperCallback {
    fun onDismissed(position: Int)
    fun onItemMoved(fromPosition: Int, toPosition: Int)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}