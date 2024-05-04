package com.example.pi_sniffsnap_garciafernandezmarta.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pi_sniffsnap_garciafernandezmarta.R
import com.example.pi_sniffsnap_garciafernandezmarta.databinding.FragmentLoginBinding
import com.example.pi_sniffsnap_garciafernandezmarta.isValidEmail
import java.lang.ClassCastException

class LoginFragment : Fragment() {

    interface LoginFragmentActions {
        fun onRegisterButtonClick()
        fun onLoginFieldsValidated(email: String, password: String)
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

}