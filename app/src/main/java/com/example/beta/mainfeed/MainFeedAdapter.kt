package com.example.beta.mainfeed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beta.R

class MainFeedAdapter(private val context: Context, private val users: List<TravelPosts>) :
    RecyclerView.Adapter<UserViewHolder>() {

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.display_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val user = users[position]
        holder.userName.text = user.userName

        //Images will work with Glide
        Glide.with(context)
            .load(user.userImage)
            .into(holder.userImage)
    }

}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val userName: TextView = itemView.findViewById(R.id.textView_username)
    val userImage: ImageView = itemView.findViewById(R.id.imageView_user)
}
