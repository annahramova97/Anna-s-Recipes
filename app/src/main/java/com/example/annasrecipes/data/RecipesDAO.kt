package com.example.annasrecipes.data

import androidx.room.*

@Dao
interface RecipesDAO {
    @Query("SELECT * FROM recipes")
    fun getAllItems(): List<Recipes>

    @Insert
    fun insertItem(recipy: Recipes) : Long

    @Delete
    fun deleteItem(recipy: Recipes)

    @Update
    fun updateItem(recipy: Recipes)
}