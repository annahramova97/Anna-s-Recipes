package com.example.annasrecipes.adapter

//import com.example.annasrecipes.ScrollingActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.annasrecipes.MainActivity
import com.example.annasrecipes.R
import com.example.annasrecipes.data.AppDatabase
import com.example.annasrecipes.data.Recipes
import com.example.annasrecipes.network.GlideOptions
import com.example.annasrecipes.touch.RecipesTouchHelperCallback
import kotlinx.android.synthetic.main.recipy_row.view.*
import java.util.*


class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.ViewHolder>, RecipesTouchHelperCallback {

    var  takenImage: Bitmap? = null
    var recipes = mutableListOf<Recipes>(
        Recipes(null, "Oatmeal with berries", 0 , "Porridge makes for a healthy and tasty breakfast"),
        Recipes(null, "Banana split", 0 , "Porridge makes for a healthy and tasty breakfast"),
        Recipes(null, "Porridge", 0 , "Porridge makes for a healthy and tasty breakfast"),
        Recipes(null, "Poatched egg", 0 , "Porridge makes for a healthy and tasty breakfast"),
        Recipes(null, "Omelette", 0 , "Porridge makes for a healthy and tasty breakfast"),
        Recipes(null, "Pancakes", 0 , "Porridge makes for a healthy and tasty breakfast"),
        Recipes(null, "Pasta", 1 , "Porridge makes for a healthy and tasty breakfast"),
        Recipes(null, "Quesadilla", 1 , "Porridge makes for a healthy and tasty breakfast"),
        Recipes(null, "Roast turkey", 1 , "Porridge makes for a healthy and tasty breakfast"),
        Recipes(null, "Lasagne", 1 , "Porridge makes for a healthy and tasty breakfast"),
        Recipes(null, "Bulgur", 1 , "Porridge makes for a healthy and tasty breakfast"),
        Recipes(null, "Rice with vegetables", 1 , "Porridge makes for a healthy and tasty breakfast"),
        Recipes(null, "Borsch", 2 , ""),
        Recipes(null, "Chicken soup", 2 , ""),
        Recipes(null, "Tomato soup", 2 , ""),
        Recipes(null, "Brocolli soup", 2 , ""),
        Recipes(null, "Banana smoothie", 3 , ""),
        Recipes(null, "Greek salad", 4 , ""),
        Recipes(null, "Warm salad", 4, ""),
        Recipes(null, "Vitamin salad", 4 , ""),
        Recipes(null, "Bruschetta", 5 , ""),
        Recipes(null, "Sandwich", 5 , ""),
        Recipes(null, "Muffins", 6 , ""),
        Recipes(null, "Almond pie", 6 , "")

           )

    val context: Context



    constructor(
        context: Context,
        listItems: List<Recipes>
        ) {
        this.context = context
        recipes.addAll(listItems)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.recipy_row, parent, false
        )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = recipes[position]

        holder.name.setText(current.recipyName)

        holder.description.setText(current.recipyDescription)



        holder.btnEdit.setOnClickListener {
            (context as MainActivity).showEditDialog(
                recipes[holder.adapterPosition], holder.adapterPosition
            )
        }

        val packageManager = context.packageManager

        holder.btnPhoto.setOnClickListener {
            //Toast.makeText(this, "Here", Toast.LENGTH_SHORT).show()
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                (context as MainActivity).startActivityForResult(takePictureIntent, MainActivity.REQUEST_CODE)
                Log.d("buttonPhoto!!!!", takePictureIntent?.toString())
//                (context as MainActivity).setPhoto(
//                recipes[holder.adapterPosition], holder.adapterPosition

                //)


            } else {
                Toast.makeText(context, "Unable to take photo", Toast.LENGTH_SHORT).show()
            }
        }



        holder.btnDetails.setOnClickListener {
            (context as MainActivity).showDetailsDialog(
                recipes[holder.adapterPosition], holder.adapterPosition

            )
            Log.d("name adapter", recipes[holder.adapterPosition].recipyName.toString() )

        }


        if (recipes[holder.adapterPosition].recipyCategory == 0) {
            holder.ivIcon.setImageResource(R.drawable.breakfast)

        } else if (recipes[holder.adapterPosition].recipyCategory == 1) {
            holder.ivIcon.setImageResource(R.drawable.main_course)

        } else if (recipes[holder.adapterPosition].recipyCategory == 2) {
            holder.ivIcon.setImageResource(R.drawable.soup)

        } else if (recipes[holder.adapterPosition].recipyCategory == 3) {
            holder.ivIcon.setImageResource(R.drawable.smoothie)

        } else if (recipes[holder.adapterPosition].recipyCategory == 4) {
            holder.ivIcon.setImageResource(R.drawable.salad)

        } else if (recipes[holder.adapterPosition].recipyCategory == 5) {
            holder.ivIcon.setImageResource(R.drawable.snack)

        } else if (recipes[holder.adapterPosition].recipyCategory == 6) {
            holder.ivIcon.setImageResource(R.drawable.dessert)




        }



        if (recipes[holder.adapterPosition].recipyName == "Banana split") {
            Log.d("PHOTO", recipes[holder.adapterPosition].recipyName)
            holder.ivIcon.setImageBitmap(MainActivity.takenImage)
        }






    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Log.d("RESULT", takenImage?.toString())
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            takenImage = data?.extras?.get("data") as Bitmap
            Log.d("RESULT", takenImage?.toString())





           // val imageDrawable: Drawable = BitmapDrawable(Resources.getSystem(), takenImage)

        }
    }




    private fun deleteRecipy(position: Int) {

        Thread {
            AppDatabase.getInstance(context).recipyDao().deleteItem(
                recipes.get(position))

            (context as MainActivity).runOnUiThread {
                Log.i("position", position.toString())
                recipes.removeAt(position)
                notifyItemRemoved(position)
            }
        }.start()
    }


    public fun addRecipy(recipy: Recipes) {
        recipes.add(recipy)

        //notifyDataSetChanged() // this refreshes the whole list
        notifyItemInserted(recipes.lastIndex)
        Log.i("size ADD", recipes.size.toString())



    }

    public fun updateRecipy(recipy: Recipes, editIndex: Int) {
        recipes.set(editIndex, recipy)
        notifyItemChanged(editIndex)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name = itemView.title
        val description = itemView.description
        //val btnDelete = itemView.btnDelete
        val btnEdit = itemView.btnEdit
        val btnDetails = itemView.btnDetails
        val btnPhoto = itemView.btnPhoto
        val ivIcon = itemView.image


    }

    override fun onDismissed(position: Int) {
        deleteRecipy(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(recipes, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    companion object {
        const val REQUEST_CODE = 42
    }




}
