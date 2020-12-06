//package com.example.annasrecipes
//
//import android.app.Activity
//import android.content.Intent
//import android.graphics.Bitmap
//import android.os.Bundle
//import android.provider.MediaStore
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.recipy_row.*
//import kotlinx.android.synthetic.main.start_activity.*
//
//class StartActivity: AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.start_activity)
//
//        Photo.setOnClickListener{
//            Toast.makeText(this, "Here" , Toast.LENGTH_SHORT).show()
//            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            if (takePictureIntent.resolveActivity(this.packageManager) != null){
//                startActivityForResult(takePictureIntent, REQUEST_CODE)
//            }
//            else {
//                Toast.makeText(this, "Unable" , Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
//            val takenImage = data?.extras?.get("data") as Bitmap
//            Image.setImageBitmap(takenImage)
//        }
//        else {
//            super.onActivityResult(requestCode, resultCode, data)
//        }
//    }
//
//    companion object {
//        const val REQUEST_CODE = 42
//    }
//
//    fun buttonClickFunction(v: View?) {
//        val intent = Intent(applicationContext, MainActivity::class.java)
//        startActivity(intent)
//    }
//}