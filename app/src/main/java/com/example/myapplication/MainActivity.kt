package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var data: ArrayList<Movie>
    private lateinit var adapter: RecyclerAdapter
    private lateinit var call: Call<Movies>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = RetrofitInstance.getRetrofitInstance()
        val api = retrofit.create(MovieInterface::class.java)

        data = ArrayList()
        adapter = RecyclerAdapter(data,this)
        binding.apply {
            rvMain.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMain.adapter = adapter
        }

        call = api.getMovie()
        getAllMovies()
        binding.getData.setOnClickListener {
            call = api.getMovie2()
            getAllMovies()
            binding.getData.visibility = GONE
        }
    }

    private fun getAllMovies(){
        call.enqueue(object: Callback<Movies>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                Log.i("MYTAG","OnResponse: code : "+response.code()+" "+response.body()!!)
                if(response.isSuccessful){
                    val movieResponse = response.body()!!
                    for (myMovie in movieResponse.MovieList.iterator()){
                        data.add(myMovie)
                    }
                    adapter.notifyDataSetChanged()
                    binding.rvMain.adapter = adapter
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message,Toast.LENGTH_LONG).show()
                t.message?.let { Log.i("MYTAG", it) }
            }
        })
    }
}