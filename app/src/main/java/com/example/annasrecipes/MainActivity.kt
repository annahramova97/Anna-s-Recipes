package com.example.annasrecipes

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Layout
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.annasrecipes.adapter.RecipesAdapter
import com.example.annasrecipes.data.AppDatabase
import com.example.annasrecipes.data.Recipes
import com.example.annasrecipes.touch.RecipesRecyclerTouchCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recipy_row.*
import kotlinx.android.synthetic.main.recipy_row.view.*
import java.io.*
import java.io.File.separator
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), RecipyDialog.RecipyHandler {

    lateinit var recipyAdapter: RecipesAdapter

    companion object {
        const val KEY_EDIT = "KEY_EDIT"
        const val PREF_NAME = "PREFTODO"
        const val KEY_STARTED = "KEY_STARTED"
        const val KEY_LAST_USED = "KEY_LAST_USED"
        const val KEY_DETAILS = "KEY_DETAILS"
        const val REQUEST_CODE = 42
        var takenImage: Bitmap? = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)

        Thread {
            var recipesList = AppDatabase.getInstance(this).recipyDao().getAllItems()

            runOnUiThread {
                recipyAdapter = RecipesAdapter(this, recipesList)

                recyclerView.adapter = recipyAdapter


                val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
                recyclerView.addItemDecoration(itemDecoration)

                val touchCallbakList =
                    RecipesRecyclerTouchCallback(
                        recipyAdapter
                    )
                val itemTouchHelper = ItemTouchHelper(touchCallbakList)
                itemTouchHelper.attachToRecyclerView(recyclerView)

            }
        }.start()

        saveStartInfo()

    }


    fun saveStartInfo() {
        var sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var editor = sharedPref.edit()
        editor.putBoolean(KEY_STARTED, true)
        editor.putString(KEY_LAST_USED, Date(System.currentTimeMillis()).toString())
        editor.apply()
    }

    fun wasStartedBefore(): Boolean {
        var sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        var lastTime = sharedPref.getString(KEY_LAST_USED, "This is the first time")
        Toast.makeText(this, lastTime, Toast.LENGTH_LONG).show()

        return sharedPref.getBoolean(KEY_STARTED, false)
    }

    fun showAddItemDialog() {
        RecipyDialog().show(supportFragmentManager, "Dialog")
    }

    var editIndex: Int = -1

    public fun showEditDialog(recipyToEdit: Recipes, index: Int) {
        editIndex = index

        val editItemDialog = RecipyDialog()

        val bundle = Bundle()
        bundle.putSerializable(KEY_EDIT, recipyToEdit)
        editItemDialog.arguments = bundle

        editItemDialog.show(supportFragmentManager, "EDITDIALOG")
    }


    fun showDetailsDialog(recipyToShow: Recipes, index: Int) {
        editIndex = index


        val details = RecipesDetails.newInstance(recipyToShow.recipyName.toString())
        Log.i("IMPORTANT", recipyToShow.recipyName)
        val bundle = Bundle()
        bundle.putSerializable(KEY_DETAILS, recipyToShow.recipyName)
        details.arguments = bundle


        details.show(supportFragmentManager, "DIALOG")

        //bundle.putString(recipyToShow.recipyName, "KEY_DETAILS");

    }

    fun photo(recipyToShow: Recipes, index: Int) {
        editIndex = index
        recipyToShow.photo = true

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {

            Toast.makeText(
                this, "SETTINGS",
                Toast.LENGTH_LONG
            ).show()

        } else if (item.itemId == R.id.add) {
            showAddItemDialog()


        }
        return super.onOptionsItemSelected(item)
    }


    override fun recipyCreated(recipy: Recipes) {
        saveRecipy(recipy)
    }

    private fun saveRecipy(recipy: Recipes) {
        Thread {
            AppDatabase.getInstance(this).recipyDao().insertItem(recipy)

            runOnUiThread {
                recipyAdapter.addRecipy(recipy)
            }
        }.start()
    }

    override fun recipyUpdated(recipy: Recipes) {
        Thread {
            AppDatabase.getInstance(this).recipyDao().updateItem(recipy)

            runOnUiThread {
                recipyAdapter.updateRecipy(recipy, editIndex)
            }
        }.start()
    }

    override fun onRestart() {
        super.onRestart();
        recyclerView.removeAllViewsInLayout();
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("RESULT", REQUEST_CODE.toString())
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            takenImage = data?.extras?.get("data") as Bitmap
            //imageTaken.setImageBitmap(takenImage);


            Log.d("RESULT", takenImage?.toString())
            saveImage(takenImage!!, this)


        }
    }

    private fun saveImage(bitmap: Bitmap, context: Context) {
        Log.d("SAVE", takenImage?.toString())
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            val values = contentValues()
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" )
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            // RELATIVE_PATH and IS_PENDING are introduced in API 29.

            val uri: Uri? = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri))
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                context.contentResolver.update(uri, values, null, null)
            }
        } else {
            val directory = File(Environment.getExternalStorageDirectory().toString() + separator )
            // getExternalStorageDirectory is deprecated in API 29

            if (!directory.exists()) {
                directory.mkdirs()
            }
            val fileName = System.currentTimeMillis().toString() + ".png"
            val file = File(directory, fileName)
            saveImageToStream(bitmap, FileOutputStream(file))
            if (file.absolutePath != null) {
                val values = contentValues()
                values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                // .DATA is deprecated in API 29
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            }
        }
    }

    private fun contentValues() : ContentValues {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



}

