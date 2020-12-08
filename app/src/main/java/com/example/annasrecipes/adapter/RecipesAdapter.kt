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
import androidx.recyclerview.widget.RecyclerView
import com.example.annasrecipes.MainActivity
import com.example.annasrecipes.R
import com.example.annasrecipes.RecipesDetails
import com.example.annasrecipes.data.AppDatabase
import com.example.annasrecipes.data.Recipes
import com.example.annasrecipes.touch.RecipesTouchHelperCallback
import kotlinx.android.synthetic.main.recipy_row.view.*
import java.util.*


class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.ViewHolder>, RecipesTouchHelperCallback {

    var image: Bitmap? = null
    var recipes = mutableListOf<Recipes>()
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


//        holder.btnDelete.setOnClickListener {
//            deleteRecipy(holder.adapterPosition)
//        }



        holder.btnEdit.setOnClickListener {
            (context as MainActivity).showEditItemDialog(
                recipes[holder.adapterPosition], holder.adapterPosition
            )
        }

        val packageManager = context.packageManager

        holder.btnPhoto.setOnClickListener {
            //Toast.makeText(this, "Here", Toast.LENGTH_SHORT).show()
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                (context as MainActivity).startActivityForResult(takePictureIntent, MainActivity.REQUEST_CODE)
                holder.ivIcon.setImageBitmap(image)

            } else {
                //Toast.makeText(this, "Unable", Toast.LENGTH_SHORT).show()
            }
        }



        holder.btnDetails.setOnClickListener {
            (context as MainActivity).showDialog(
                recipes[holder.adapterPosition], holder.adapterPosition


            )
            Log.d("name adapter", recipes[holder.adapterPosition].recipyName.toString() )


//            val fragmentB = RecipesDetails.newInstance(current.recipyName)
//            Log.i("Name Adapter", current.recipyName.toString())
//            (context as MainActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.constraintLayout, fragmentB, "fragmnetId")
//                .addToBackStack(null)
//                .commit();


        }


        //food
        if (recipes[holder.adapterPosition].recipyCategory == 0) {
            holder.ivIcon.setImageResource(R.drawable.breakfast)
            //pharmacy
        } else if (recipes[holder.adapterPosition].recipyCategory == 1) {
            holder.ivIcon.setImageResource(R.drawable.breakfast)
            //shopping
        } else if (recipes[holder.adapterPosition].recipyCategory == 2) {
            holder.ivIcon.setImageResource(R.drawable.breakfast)
            //books
        } else if (recipes[holder.adapterPosition].recipyCategory == 3) {
            holder.ivIcon.setImageResource(R.drawable.breakfast)
        }


    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val takenImage = data?.extras?.get("data") as Bitmap
            image = takenImage

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
