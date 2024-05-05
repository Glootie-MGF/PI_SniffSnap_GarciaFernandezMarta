package com.example.pi_sniffsnap_garciafernandezmarta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pi_sniffsnap_garciafernandezmarta.auth.LoginActivity
import com.example.pi_sniffsnap_garciafernandezmarta.databinding.ActivityMainBinding
import com.example.pi_sniffsnap_garciafernandezmarta.model.User
import com.example.pi_sniffsnap_garciafernandezmarta.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = User.getLoggedInUser(this)
        if (user == null){
            openLoginActivity()
            return
        }

        binding.settingsFab.setOnClickListener{
            openSettingsActivity()
        }
    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}