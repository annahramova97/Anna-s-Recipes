package com.example.annasrecipes.model

import android.widget.ImageView

// result generated from /json

data class AnalyzedInstructions1803759043(val name: String?, val steps: List<Any>?)

data class Base(val results: List<Results2090169010>?, val offset: Number?, val number: Number?, val totalResults: Number?)

data class Equipment1197442956(val id: Number?, val name: String?, val localizedName: String?, val image: String?)

data class Equipment2065342773(val id: Number?, val name: String?, val localizedName: String?, val image: String?)

data class Equipment926506268(val id: Number?, val name: String?, val localizedName: String?, val image: String?)

data class Ingredients1263907225(val id: Number?, val name: String?, val localizedName: String?, val image: String?)

data class Ingredients1471357009(val id: Number?, val name: String?, val localizedName: String?, val image: String?)

data class Ingredients1919777041(val id: Number?, val name: String?, val localizedName: String?, val image: String?)

data class Ingredients81907986(val id: Number?, val name: String?, val localizedName: String?, val image: String?)

data class Ingredients858161782(val id: Number?, val name: String?, val localizedName: String?, val image: String?)

data class Results2090169010(val vegetarian: Boolean?, val vegan: Boolean?, val glutenFree: Boolean?, val dairyFree: Boolean?, val veryHealthy: Boolean?, val cheap: Boolean?, val veryPopular: Boolean?, val sustainable: Boolean?, val weightWatcherSmartPoints: Number?, val gaps: String?, val lowFodmap: Boolean?, val aggregateLikes: Number?, val spoonacularScore: Number?, val healthScore: Number?, val creditsText: String?, val license: String?, val sourceName: String?, val pricePerServing: Number?, val id: Number?, val title: String?, val readyInMinutes: Number?, val servings: Number?, val sourceUrl: String?, val image: String?, val imageType: String?, val summary: String?, val cuisines: List<Any>?, val dishTypes: List<String>?, val diets: List<String>?, val occasions: List<Any>?, val analyzedInstructions: List<AnalyzedInstructions1803759043>?, val spoonacularSourceUrl: String?)

data class Steps799651419(val number: Number?, val step: String?, val ingredients: List<Ingredients1471357009>?, val equipment: List<Equipment1197442956>?)
