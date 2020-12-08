package com.example.annasrecipes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.annasrecipes.RecipesDetails.Companion.newInstance
import com.example.annasrecipes.adapter.RecipesAdapter
import com.example.annasrecipes.data.AppDatabase
import com.example.annasrecipes.data.Recipes
import com.example.annasrecipes.touch.RecipesRecyclerTouchCallback
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt

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
        Log.i("IMPORTANT",recipyToShow.recipyName)
        val bundle = Bundle()
        bundle.putSerializable(KEY_DETAILS, recipyToShow.recipyName)
        details.arguments = bundle


        details.show(supportFragmentManager, "DIALOG")

        //bundle.putString(recipyToShow.recipyName, "KEY_DETAILS");

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings){

            Toast.makeText(this, "SETTINGS",
                Toast.LENGTH_LONG).show()

        } else if (item.itemId == R.id.add) {
            showAddItemDialog()


        }
        return super.onOptionsItemSelected(item)
    }


    override fun recipyCreated(recipy: Recipes) {
        saveRecipy(recipy)
    }

    private fun saveRecipy(recipy: Recipes) {
        Thread{
            AppDatabase.getInstance(this).recipyDao().insertItem(recipy)

            runOnUiThread {
                recipyAdapter.addRecipy(recipy)
            }
        }.start()
    }

    override fun recipyUpdated(recipy: Recipes)  {
        Thread{
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

}