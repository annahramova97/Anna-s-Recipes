package com.example.annasrecipes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "recipes")
data class Recipes(
    @PrimaryKey(autoGenerate = true) var recipyId: Long?,
    @ColumnInfo(name = "recipyName") var recipyName: String,
    @ColumnInfo(name = "description") var description: String,

) : Serializable