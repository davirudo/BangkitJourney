package com.example.katahati.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.katahati.R
import com.example.katahati.databinding.ActivityLoginBinding
import com.example.katahati.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNav
        val navController = binding.navFragment


    }
}