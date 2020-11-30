package com.example.annasrecipes.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.annasrecipes.MainActivity
import com.example.annasrecipes.R
//import com.example.annasrecipes.ScrollingActivity
import com.example.annasrecipes.data.AppDatabase
import com.example.annasrecipes.data.Recipes
import com.example.annasrecipes.touch.RecipesTouchHelperCallback
import kotlinx.android.synthetic.main.recipy_row.view.*
import java.util.*

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.ViewHolder>, RecipesTouchHelperCallback {

    var recipes = mutableListOf<Recipes>()
    val context: Context

    constructor(context: Context, listItems: List<Recipes>) {
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


        holder.btnDelete.setOnClickListener {
            deleteRecipy(holder.adapterPosition)
        }

        holder.btnDelete.setOnClickListener {
           //todo
        }

        holder.btnEdit.setOnClickListener {
            (context as MainActivity).showEditItemDialog(
                recipes[holder.adapterPosition], holder.adapterPosition
            )
        }

//        holder.btnDetails.setOnClickListener {
//            (context as MainActivity).showDetailsItemDialog(
//                recipes[holder.adapterPosition], holder.adapterPosition
//            )
//        }




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
        val btnDelete = itemView.btnDelete
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

}
