//package com.example.annasrecipes
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import com.example.annasrecipes.model.Base
//import com.example.annasrecipes.network.GlideOptions
//import com.example.annasrecipes.network.RecipesApi
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
//class DetailActivity : AppCompatActivity() {
//
//    private lateinit var recipy: String
//    private var viewImage: ImageView? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail)
//
//
////        arguments?.let {
//            recipy = it.getString(ARG_PARAM1).toString()
//            Log.i("Parameter", recipy)
//        }
//
//
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.spoonacular.com/recipes/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val key = "63a7cf64312240efb7e2f6d634b4fb7e"
//        val recipyApi = retrofit.create(RecipesApi::class.java)
//        val recipyCall = recipyApi.getRecipyDetails(recipy)
//        //Log.i("Call", )
//
//
//
//
//        recipyCall.enqueue(object : Callback<Base> {
//            override fun onFailure(call: Call<Base>, t: Throwable) {
//                //tvCurrentTemp.text = t.message.toString()
//            }
//
//            override fun onResponse(call: Call<Base>, response: Response<Base>) {
//                Log.i("Response", response.code().toString())
//                Log.i("error body", response.body()?.results?.get(0).toString())
//                Log.i("body", response.body()?.toString())
//                val result = response.body()
//
//
//
//                if (result != null) {
//                    Log.i("result", result.results?.get(0)?.summary.toString())
//                    //imgUrl += result.results?.get(0)?.image + ".png"
//
//                    //val currTemp = result.main?.temp.toString()
//                    val  = result.results?.get(0)?.summary.toString()
////                    val maxTemp = result.main?.temp_max.toString()
////                    val minTemp = result.main?.temp_min.toString()
//
////                    tvCurrentTemp.text = "Current temperature: " +  currTemp?.let { currTemp }
////                    tvCityName.text = city?. let { city }
////                    tvDescription.text =  description?.let {description}
////                    tvMaxTemp.text = "Max temp: " + maxTemp?.let {maxTemp}
////                    tvMinTemp.text =  "Min temp: "+minTemp?.let {minTemp}
////                    tvHumidity.text = "Humidity: " + result.main?.humidity
////                    tvSunrise.text = "Sunrise: " + result.sys?.sunrise
////                    tvSunset.text = "Sunset: " + result.sys?.sunset
//
//                    viewImage?.let { GlideOptions().downloadImage(this, result.results?.get(0)?.image.toString(), viewImage!!) }
//
//
//
//                }
//            }
//
//        })
//
//
//
//
//    }
//
//
//
//
//
//
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment WheatherDetails.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String) =
//            DetailActivity().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                }
//            }
//    }
//}