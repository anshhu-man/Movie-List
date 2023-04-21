package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.Activity2MainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: Activity2MainBinding
    private lateinit var data: ArrayList<Movie>
    private lateinit var adapter: RecyclerAdapter2
    private lateinit var database: FirebaseDatabase
    private lateinit var fav: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity2MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getAllMovies()

        database = FirebaseDatabase.getInstance()
        fav = database.getReference("Favorites")

        data = ArrayList()
        adapter = RecyclerAdapter2(data, this)
        binding.apply {
            rvMain2.layoutManager = LinearLayoutManager(this@MainActivity2)
            rvMain2.adapter = adapter
        }

    }
    private fun getAllMovies(){

        val retrofit = RetrofitInstance.getRetrofitInstance()

        val api = retrofit.create(MovieInterface::class.java)

        val call = api.getMovie()



        call.enqueue(object: Callback<Movies>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                Log.i("MYTAG","OnResponse: code : "+response.code()+" "+response.body()!!)
                if(response.isSuccessful){
                    val movieResponse = response.body()!!
                    for (myMovie in movieResponse.MovieList.iterator()){
                        fav.addListenerForSingleValueEvent(object: ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if(snapshot.hasChild(myMovie.IMDBID)){
                                    data.add(myMovie)
                                    adapter.notifyDataSetChanged()
                                    binding.rvMain2.adapter = adapter
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(this@MainActivity2,"Something went wrong", Toast.LENGTH_SHORT).show()
                            }

                        })
                    }
                    Log.i("MYTAG",(data).toString())
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Toast.makeText(this@MainActivity2, t.message,Toast.LENGTH_LONG).show()
                t.message?.let { Log.i("MYTAG", it) }
            }
        })
    }
}