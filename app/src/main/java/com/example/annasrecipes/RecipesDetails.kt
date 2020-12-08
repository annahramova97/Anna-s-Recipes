package com.example.annasrecipes


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.DialogFragment

import com.example.annasrecipes.network.RecipesApi

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.bumptech.glide.annotation.GlideModule;
import com.example.annasrecipes.data.Recipes
import com.example.annasrecipes.model.Base
import com.example.annasrecipes.network.GlideOptions
import kotlinx.android.synthetic.main.recipy_details.*
import kotlinx.android.synthetic.main.recipy_details.view.*
import kotlinx.android.synthetic.main.recipy_dialog.view.*
import kotlinx.android.synthetic.main.recipy_row.view.*

private const val KEY_DETAILS = "KEY_DETAILS"

@GlideModule
class RecipesDetails : DialogFragment () {
    private lateinit var recipy: String

    private var imgUrl = "https://spoonacular.com/recipeImages/"

    lateinit var nameR: EditText
    lateinit var descriptionR: EditText
    lateinit var timeR : EditText
    private var viewImage: ImageView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        super.onCreate(savedInstanceState)

        val dialogBuilder = AlertDialog.Builder(requireContext())


        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.recipy_details, null
        )

        nameR = dialogView.nameR
        descriptionR = dialogView.descriptionR
        timeR = dialogView.timeR
        viewImage = dialogView.imageR


        arguments?.let {
            recipy = it.getString(KEY_DETAILS).toString()
            Log.i("Parameter" ,recipy)
        }

        dialogBuilder.setTitle("Recipy details")
        dialogBuilder.setView(dialogView)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/recipes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val recipyApi = retrofit.create(RecipesApi::class.java)
        val recipyCall = recipyApi.getRecipyDetails(recipy)
        //Log.i("Call", )

        val recipyDetails = this


        recipyCall.enqueue(object : Callback<Base> {
            override fun onFailure(call: Call<Base>, t: Throwable) {

            }

            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                Log.i("Response", response.code().toString())
                Log.i("body", response.body()?.toString())
                val result = response.body()



                if (result != null) {
                    Log.i("result", result.results?.get(0)?.summary.toString())
                    Log.i("SIZE", result.results?.size.toString())

                    val n = result.results?.get(0)?.title.toString()
                    val des = result.results?.get(0)?.analyzedInstructions?.get(0)?.steps
                    var steps : String? = ""
                    var i = 0
                    if (des != null) {
                        for (step in des){
                              steps += des[i].step.toString() + "\n"
                              i++
                        }
                    }
                    val time = result.results?.get(0)?.readyInMinutes.toString()

                    nameR.setText(n)
                    descriptionR.setText(steps)
                    timeR.setText(time)


                    viewImage?.let { GlideOptions().downloadImage(recipyDetails, result.results?.get(0)?.image.toString(),
                        viewImage!!
                    ) }



                }
            }

        })
        return dialogBuilder.create()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.recipy_details, container, false)
        //viewImage = view.findViewById(R.id.imageR)

        return view
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            RecipesDetails().apply {
                arguments = Bundle().apply {
                    putString(KEY_DETAILS, param1)
                    Log.d("bundle", param1)


                }
            }

    }


}