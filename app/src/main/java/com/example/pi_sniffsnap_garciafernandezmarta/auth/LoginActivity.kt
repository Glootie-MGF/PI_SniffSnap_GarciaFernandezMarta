package com.example.pi_sniffsnap_garciafernandezmarta.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.pi_sniffsnap_garciafernandezmarta.main.MainActivity
import com.example.pi_sniffsnap_garciafernandezmarta.R
import com.example.pi_sniffsnap_garciafernandezmarta.api.ApiResponseStatus
import com.example.pi_sniffsnap_garciafernandezmarta.databinding.ActivityLoginBinding
import com.example.pi_sniffsnap_garciafernandezmarta.model.User
import com.example.pi_sniffsnap_garciafernandezmarta.model.UserGoogle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmentActions, SignUpFragment.SignUpFragmentActions {

    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.status.observe(this) {
            status ->
            when(status) {
                is ApiResponseStatus.Error -> {
                    binding.loadingWheel.visibility = View.GONE
                    showErrorDialog(status.messageId)
                }
                is ApiResponseStatus.Loading -> binding.loadingWheel.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> binding.loadingWheel.visibility = View.GONE
            }
        }
        viewModel.user.observe(this){
            user ->
            if (user != null){
                User.setLoggedInUser(this, user)
                startMainActivity()
            }
        }
    }

    private fun startMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish() // Para cuando volvamos atrás después de haber iniciado sesión
                // no vuelva a la pantalla de login
    }

    private fun showErrorDialog(messageId: Int){
        AlertDialog.Builder(this).setTitle(R.string.there_was_an_error)
            .setMessage(messageId)
            .setPositiveButton(android.R.string.ok) { _, _ -> /** Escondemos diálogo **/}
            .create()
            .show()
    }

    override fun onRegisterButtonClick() {
        findNavController(R.id.nav_host_fragment).navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
    }

    override fun onLoginFieldsValidated(email: String, password: String) {
        viewModel.login(email, password)
    }

    override fun onSignUpFieldsValidated(
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        viewModel.sigUp(email, password, passwordConfirmation)
    }

    override fun handleGoogleSignInResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
                    Firebase.auth.signInWithCredential(credentials)
                        .addOnCompleteListener(this) { signInTask ->
                            if (signInTask.isSuccessful) {
                                // obtener el usuario actual de Firebase y guardar su información
                                val firebaseUser = Firebase.auth.currentUser
                                val userGoogle = UserGoogle(
                                    uid = firebaseUser?.uid ?: "",
                                    email = firebaseUser?.email ?: "",
                                    idToken = account.idToken ?: ""
                                )
                                UserGoogle.setLoggedInUser(this, userGoogle)
                                startMainActivity()
                            } else {
                                Log.e("GoogleSignIn", "Error en el inicio de sesión con Google", signInTask.exception)
                                Toast.makeText(this,
                                    getString(R.string.error_sign_in_google), Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            } catch (e: ApiException) {
                Toast.makeText(this, getString(R.string.error_sign_in_google), Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(this, getString(R.string.user_cancelled), Toast.LENGTH_SHORT).show()
        }
    }

}