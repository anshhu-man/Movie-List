package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter2(private val list: ArrayList<Movie>,context: Context): RecyclerView.Adapter<MovieViewHolder2>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder2 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_design_2,parent,false)
        return MovieViewHolder2(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder2, position: Int) {
        val currMovie = list[position]
        holder.bind(currMovie)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
class MovieViewHolder2(view: View): RecyclerView.ViewHolder(view){

    private lateinit var imdbId: String
    private val title:TextView = view.findViewById(R.id.title2)
    private val year:TextView = view.findViewById(R.id.year2)
    private val runtime:TextView = view.findViewById(R.id.runtime2)
    private val cast:TextView = view.findViewById(R.id.cast2)

    fun bind(movie: Movie){
        imdbId = movie.IMDBID
        title.text = movie.Title
        year.text = movie.Year
        runtime.text = movie.Runtime
        cast.text = movie.Cast
    }
}
