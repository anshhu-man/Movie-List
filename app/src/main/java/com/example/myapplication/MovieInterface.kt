package com.example.myapplication

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface MovieInterface {
    @GET("1.json")
    fun getMovie() : Call<Movies>
    @GET("2.json")
    fun getMovie2() : Call<Movies>
}