package com.example.beta.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.beta.R


class ProfileAdapter(private val imageList: MutableList<ProfileData>,
):
    RecyclerView.Adapter<ProfileAdapter.ViewHolder>(){

    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view){

        var image: ImageView
        var location: EditText
        var description: EditText

        init {
            description = itemView.findViewById(R.id.info)
            image = itemView.findViewById(R.id.travelImage)
            location = itemView.findViewById(R.id.location)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, ): ProfileAdapter.ViewHolder {
        // method to setup a new Viewholder and its view - called by the recyclerView.
        val context = parent.context //tells the program what xml layout to use
        val inflater = LayoutInflater.from(context) //inflates the layout of the screen
        val adapterLayout = inflater.inflate(R.layout.album_photo_full_size, parent, false)
        return ViewHolder(adapterLayout)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = imageList[position]
        holder.description.text
        holder.location.text
        holder.image.setImageResource(data.images)

    }

    override fun getItemCount() = imageList.size


}
