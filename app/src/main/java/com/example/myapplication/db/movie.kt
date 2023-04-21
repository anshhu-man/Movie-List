package com.example.myapplication.db

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class movie(
    val imdbId: String = "",
    val title: String = "",
    val year: String = "",
    val runtime: String = "",
    val cast: String = ""
)
