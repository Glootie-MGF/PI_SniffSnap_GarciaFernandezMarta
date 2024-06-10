package com.example.pi_sniffsnap_garciafernandezmarta.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pi_sniffsnap_garciafernandezmarta.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }
}
