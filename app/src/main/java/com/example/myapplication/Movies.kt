package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class Movies(
  @SerializedName("Movie List")
  val MovieList : ArrayList<Movie>
)
