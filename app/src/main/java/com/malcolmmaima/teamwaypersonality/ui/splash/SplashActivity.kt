package com.malcolmmaima.teamwaypersonality.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.malcolmmaima.teamwaypersonality.R
import com.malcolmmaima.teamwaypersonality.databinding.ActivitySplashBinding
import com.malcolmmaima.teamwaypersonality.ui.personalitytest.MainActivity
import com.malcolmmaima.teamwaypersonality.utils.isNetworkAvailable
import com.malcolmmaima.teamwaypersonality.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.proceedBtn.visibility = View.GONE
        binding.proceedBtn.setOnClickListener {
            proceedToApp()
        }

        //check network connectivity
        if (!isNetworkAvailable(this)) {
            //show error
            binding.root.snackbar(getString(R.string.no_internet_connection))

            lifecycleScope.launch {
                delay(10000)
                if (!isNetworkAvailable(this@SplashActivity)) {
                    binding.root.snackbar(getString(R.string.no_internet_connection))
                } else {
                    binding.proceedBtn.visibility = View.VISIBLE
                }
            }

        } else {
            binding.proceedBtn.visibility = View.VISIBLE
        }
    }

    private fun proceedToApp() {
        binding.proceedBtn.visibility = View.GONE
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}