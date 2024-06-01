package com.example.pi_sniffsnap_garciafernandezmarta.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pi_sniffsnap_garciafernandezmarta.R
import com.example.pi_sniffsnap_garciafernandezmarta.databinding.FragmentLoginBinding
import com.example.pi_sniffsnap_garciafernandezmarta.isValidEmail
import com.example.pi_sniffsnap_garciafernandezmarta.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import java.lang.ClassCastException

class LoginFragment : Fragment() {

    interface LoginFragmentActions {
        fun onRegisterButtonClick()
        fun onLoginFieldsValidated(email: String, password: String)
        fun handleGoogleSignInResult(result: ActivityResult)
    }

    private lateinit var loginFragmentActions: LoginFragmentActions
    private lateinit var binding: FragmentLoginBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginFragmentActions = try {
            context as LoginFragmentActions
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement LoginFragmentActions")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.loginRegisterButton.setOnClickListener{
            loginFragmentActions.onRegisterButtonClick()
        }
        binding.loginButton.setOnClickListener{
            validateFields()
        }
        binding.googleLoginButton.setOnClickListener {
            loginGoogle()
        }
        return binding.root
    }

    private fun validateFields() {
        // Inicializamos los errores a vacío
        binding.emailInput.error = ""
        binding.passwordInput.error = ""

        val email = binding.emailEdit.text.toString()
        if (!isValidEmail(email)){
            binding.emailInput.error = getString(R.string.email_is_not_valid)
            return
        }

        val password = binding.passwordEdit.text.toString()
        if (password.isEmpty()){
            binding.passwordInput.error = getString(R.string.password_must_not_be_empty)
            return
        }

        // Una vez validados, llamamos a la función
        loginFragmentActions.onLoginFieldsValidated(email, password)
    }

    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            loginFragmentActions.handleGoogleSignInResult(result)
            // Con el responseLauncher busca si tenemos guardados los credenciales de nuestra cuenta
            // y si no los tenemos nos deja registrar el usuario
        }
    // Método para loguearnos con GOOGLE
    private fun loginGoogle() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.id_client_google))
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(requireActivity(), googleConf)
        googleClient.signOut() // Importante para que cierre sesión y podamos elegir otro usuario

        responseLauncher.launch(googleClient.signInIntent) //Lanzamos un intent
    }

}