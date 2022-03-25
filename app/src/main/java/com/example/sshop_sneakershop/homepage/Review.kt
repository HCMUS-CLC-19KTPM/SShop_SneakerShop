package com.example.sshop_sneakershop.homepage

import android.media.Image
import java.util.*

var reviewList = mutableListOf<Review>()

class Review(
    var avatar: Int,
    var reviewer: String,
    var count: Int, //Number of reviews posted
    var date: String,
    var upvote: Boolean, //True = Up-vote, False = Down-vote
    var rate: Double, //Int vale scale from 0-5
    var content: String,
    val id: Int? = reviewList.size
)