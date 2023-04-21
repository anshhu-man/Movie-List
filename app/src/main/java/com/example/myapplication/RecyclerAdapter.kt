package com.example.myapplication

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.db.movie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RecyclerAdapter(private val list: ArrayList<Movie>,context: Context): RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_design,parent,false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currMovie = list[position]
        holder.bind(currMovie)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
class MovieViewHolder(view: View): RecyclerView.ViewHolder(view){

    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var fav: DatabaseReference
    var imdbId: String = ""
    private val title:TextView = view.findViewById(R.id.title)
    private val year:TextView = view.findViewById(R.id.year)
    private val runtime:TextView = view.findViewById(R.id.runtime)
    private val cast:TextView = view.findViewById(R.id.cast)
    val likeBtn:Button = view.findViewById(R.id.likeBtn)

    fun bind(movie: Movie){

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        fav = database.getReference("Favorites")
        checkIsFavourite()

        imdbId = movie.IMDBID
        title.text = movie.Title
        year.text = movie.Year
        runtime.text = movie.Runtime
        cast.text = movie.Cast

        likeBtn.setOnClickListener {
            if(likeBtn.text.toString() == "Remove from Favorite"){
                removeFromFavorites()
            }
            else{
                addToFavourites()
            }
            checkIsFavourite()
        }
    }

    private fun checkIsFavourite(){
        Log.i("MYTAG","Checking if movie is in fav or not")

        fav.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.child(imdbId).exists()){
                    Log.i("MYTAG","Available in Fav")
                    likeBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_favorite_filled_white,0)
                    likeBtn.text = "Remove from Favorite"
                }
                else{
                    Log.i("MYTAG","not Available in Fav")
                    likeBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_favorite_border_white,0)
                    likeBtn.text = "Add to Favorite"
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun addToFavourites(){
        Log.i("Tag","Adding to Fav")

        val myFav = movie(
            imdbId,
            title.text.toString(),
            year.text.toString(),
            runtime.text.toString(),
            cast.text.toString()
        )
        fav.child(imdbId)
            .setValue(myFav)
            .addOnSuccessListener {
                Log.i("MYTAG","Added to Fav")
            }
            .addOnFailureListener {e ->
                Log.i("MYTAG","Failed to add to Fav due to ${e.message}")
            }
    }

    private fun removeFromFavorites(){
        Log.i("Tag","Removing from Fav")
        val ref = FirebaseDatabase.getInstance().getReference("Favorites")
        ref.child(imdbId).removeValue()
            .addOnSuccessListener {
                Log.i("MYTAG","Removed from Fav")
            }
            .addOnFailureListener{e ->
                Log.i("MYTAG","Removed from Fav due to ${e.message}")
            }
    }
}
