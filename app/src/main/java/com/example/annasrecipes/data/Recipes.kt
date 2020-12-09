package com.example.annasrecipes.data
import android.widget.ImageView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "recipes")
data class Recipes(
    @PrimaryKey(autoGenerate = true) var recipyId: Long?,
    @ColumnInfo(name = "recipyName") var recipyName: String,
    @ColumnInfo(name = "recipyCategory") var recipyCategory: Int,
    @ColumnInfo(name = "recipyDescription") var recipyDescription: String,
    @ColumnInfo(name = "photo") var photo: Boolean

) : Serializable