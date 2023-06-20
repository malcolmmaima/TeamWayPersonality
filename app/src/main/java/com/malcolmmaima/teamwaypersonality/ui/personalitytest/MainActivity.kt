package com.malcolmmaima.teamwaypersonality.ui.personalitytest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.malcolmmaima.teamwaypersonality.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}