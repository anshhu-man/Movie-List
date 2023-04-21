package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.HomepageBinding

class Homepage : AppCompatActivity() {


    private lateinit var binding: HomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnMovie.setOnClickListener{
                val intent = Intent(this@Homepage,MainActivity::class.java)
                startActivity(intent)
            }

            btnMyFav.setOnClickListener {
                val intent = Intent(this@Homepage,MainActivity2::class.java)
                startActivity(intent)
            }

            btnProfile.setOnClickListener {
                val intent = Intent(this@Homepage,MyProfile::class.java)
                startActivity(intent)
            }
        }
    }
}