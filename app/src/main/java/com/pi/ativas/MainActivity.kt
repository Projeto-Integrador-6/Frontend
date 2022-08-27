package com.pi.ativas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pi.ativas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}