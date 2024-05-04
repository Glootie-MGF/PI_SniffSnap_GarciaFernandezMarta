package com.example.pi_sniffsnap_garciafernandezmarta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pi_sniffsnap_garciafernandezmarta.auth.LoginActivity
import com.example.pi_sniffsnap_garciafernandezmarta.model.User

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = User.getLoggedInUser(this)
        if (user == null){
            openLoginActivity()
            return
        }
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}