package com.example.annasrecipes


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment

import com.example.annasrecipes.network.RecipesApi

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.bumptech.glide.annotation.GlideModule;
import com.example.annasrecipes.model.Base
import com.example.annasrecipes.network.GlideOptions

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
@GlideModule
class RecipesDetails : DialogFragment() {
    private lateinit var recipy: String
    private var viewImage: ImageView? = null
    private var imgUrl = "https://spoonacular.com/recipeImages/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipy = it.getString(ARG_PARAM1).toString()
        }



        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/recipes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val key = "63a7cf64312240efb7e2f6d634b4fb7e\n"
        val recipyApi = retrofit.create(RecipesApi::class.java)
        val recipyCall = recipyApi.getRecipyDetails(recipy, number = 1, info = true, key= key)

        val recipyDetails = this


        recipyCall.enqueue(object : Callback<Base> {
            override fun onFailure(call: Call<Base>, t: Throwable) {
                //tvCurrentTemp.text = t.message.toString()
            }

            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                val result = response.body()

                if (result != null) {
                    imgUrl += result.results?.get(0)?.image + ".png"

                    //val currTemp = result.main?.temp.toString()
                    val description = result.results?.get(0)?.summary.toString()
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

                    viewImage?.let { GlideOptions().downloadImage(recipyDetails, imgUrl, viewImage!!) }

                }
            }

        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.activity_main, container, false)
        viewImage = view.findViewById(R.id.image)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WheatherDetails.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            RecipesDetails().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }


}