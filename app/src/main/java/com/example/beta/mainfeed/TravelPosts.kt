package com.example.beta.mainfeed

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TravelPosts(
    @PrimaryKey
    val userName: String,
    val userImage: String
) {
    constructor() : this(
        "",
        ""
    )
}