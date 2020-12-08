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
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlinx.android.synthetic.main.recipy_dialog.view.*

private const val KEY_DETAILS = "KEY_DETAILS"

@GlideModule
class RecipesDetails : DialogFragment () {
    private lateinit var recipy: String
    private var viewImage: ImageView? = null
    private var imgUrl = "https://spoonacular.com/recipeImages/"

    lateinit var nameR: EditText
    lateinit var descriptionR: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        super.onCreate(savedInstanceState)
        //recipy = arguments?.getString("KEY_DETAILS").toString()
        Log.i("arguments", arguments.toString())
        //Log.i("Parameter" ,recipy)

        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle("Details Recipy")
        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.recipy_details, null
        )

        nameR = dialogView.nameR

        Log.d("createDialog", nameR.toString())
        descriptionR = dialogView.descriptionR

        //recipy = arguments?.getString(KEY_DETAILS).toString()
        //Log.i("Parameter" ,recipy)


        arguments?.let {
            recipy = it.getString(KEY_DETAILS).toString()
            Log.i("Parameter" ,recipy)
        }

        //val arguments = this.arguments
        // if we are in EDIT mode
        //if (arguments != null && arguments.containsKey(MainActivity.KEY_DETAILS)) {
//            val recipy = arguments.getSerializable(MainActivity.KEY_DETAILS) as Recipes
//
//            nameR.setText(recipy.recipyName)
//            descriptionR.setText(recipy.recipyDescription)


            dialogBuilder.setTitle("Recipy details")
        //}



        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/recipes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val key = "63a7cf64312240efb7e2f6d634b4fb7e"
        val recipyApi = retrofit.create(RecipesApi::class.java)
        val recipyCall = recipyApi.getRecipyDetails(recipy)
        //Log.i("Call", )

        val recipyDetails = this


        recipyCall.enqueue(object : Callback<Base> {
            override fun onFailure(call: Call<Base>, t: Throwable) {
                //tvCurrentTemp.text = t.message.toString()
            }

            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                Log.i("Response", response.code().toString())
                Log.i("body", response.body()?.toString())
                val result = response.body()



                if (result != null) {
                    Log.i("result", result.results?.get(0)?.summary.toString())
                    //imgUrl += result.results?.get(0)?.image + ".png"
                    Log.i("SIZE", result.results?.size.toString())

                    //val currTemp = result.main?.temp.toString()
                    val n = result.results?.get(0)?.title.toString()
                    val des = result.results?.get(0)?.summary.toString()
                    nameR.setText(n)
                    descriptionR.setText(des)
//                    val maxTemp = result.main?.temp_max.toString()
//                    val minTemp = result.main?.temp_min.toString()

//                    tvCurrentTemp.text = "Current temperature: " +  currTemp?.let { currTemp }
//                    tvCityName.text = city?. let { city }
//                    tvDescription.text =  description?.let {description}
//                    tvMaxTemp.text = "Max temp: " + maxTemp?.let {maxTemp}
//                    tvMinTemp.text =  "Min temp: "+minTemp?.let {minTemp}
//                    tvHumidity.text = "Humidity: " + result.main?.humidity
//                    tvSunrise.text = "Sunrise: " + result.sys?.sunrise
//                    tvSunset.text = "Sunset: " + result.sys?.sunset

                        //viewImage?.let { GlideOptions().downloadImage(recipyDetails, result.results?.get(0)?.image.toString(), viewImage!!) }



                }
            }

        })
        return dialogBuilder.create()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.recipy_details, container, false)
        viewImage = view.findViewById(R.id.imageR)

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