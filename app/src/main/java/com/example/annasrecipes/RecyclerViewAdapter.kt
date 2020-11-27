package com.example.annasrecipes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val titles = arrayListOf( "Breakfast", "Salads", "Soups", "Smothie")
    private val description =  arrayListOf( "text1", "text2", "text3", "text4")
    private val images = intArrayOf(
        R.drawable.breakfast,
        R.drawable.salad,
        R.drawable.soup,
        R.drawable.smoothie
    )

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var image : ImageView
        var textTitle: TextView
        var textDescription: TextView

        init {
            image = itemView.findViewById(R.id.image)
            textTitle = itemView.findViewById(R.id.title)
            textDescription = itemView.findViewById(R.id.description)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textTitle.text = titles [position]
        holder.textDescription.text = description [position]
        holder.image.setImageResource( images [position])
        holder.itemView.setOnClickListener { v: View ->
             Toast.makeText (v.context, "clockes on the iten", Toast.LENGTH_SHORT).show()
         }


    }

    override fun getItemCount(): Int {
        return titles.size
    }
}