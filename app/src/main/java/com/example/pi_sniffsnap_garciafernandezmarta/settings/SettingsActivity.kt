package com.example.pi_sniffsnap_garciafernandezmarta.settings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.pi_sniffsnap_garciafernandezmarta.auth.LoginActivity
import com.example.pi_sniffsnap_garciafernandezmarta.database.FavoritesActivity
import com.example.pi_sniffsnap_garciafernandezmarta.databinding.ActivitySettingsBinding
import com.example.pi_sniffsnap_garciafernandezmarta.model.User

class SettingsActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        binding.themeSwitch.isChecked = isDarkMode

        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPreferences.edit().putBoolean("dark_mode", true).apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPreferences.edit().putBoolean("dark_mode", false).apply()
            }
        }

        binding.favoritesButton.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }

        binding.developerInfoIcon.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        binding.buttonCalculate.setOnClickListener {
            val ageText = binding.editTextAge.text.toString()
            if (ageText.isNotEmpty()) {
                val age = ageText.toInt()
                if (age in 1..100) {
                    val dogAge = calculateDogAge(age)
                    binding.textViewResult.text = "OH WOW!\n You are $dogAge doggy years old!"
                } else {
                    Toast.makeText(this, "Please, enter a valid age between 1 and 100.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please, enter your age.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.logoutButton.setOnClickListener{
            logout()
        }
    }

    private fun calculateDogAge(humanAge: Int): Int {
        return when {
            humanAge <= 2 -> humanAge * 10
            else -> 20 + (humanAge - 2) * 7
        }
    }

    private fun logout() {
        User.logout(this)
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}