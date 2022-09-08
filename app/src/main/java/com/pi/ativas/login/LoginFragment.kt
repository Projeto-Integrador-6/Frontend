package com.pi.ativas.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.pi.ativas.R
import com.pi.ativas.databinding.FragmentLoginBinding
import kotlin.math.log

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    private fun initViews() {
        with(binding) {
            btnLogar.setOnClickListener {
                checkLogin()
            }
        }
    }

    private fun checkLogin() {
        // TODO: Deixar o botão "Entrar" clicavel apenas se login e senha inseridos!
        with(binding) {
            val login = txtLogin.text.toString()
            val password = txtSenha.text.toString()

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Favor inserir login e senha!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                when (login) {
                    "np" -> callNewPassword()
                    "st" -> findNavController().navigate(R.id.action_loginFragment_to_homeStudentFragment)
                    "tc" -> findNavController().navigate(R.id.action_loginFragment_to_homeTeacherFragment)
                    else -> Toast.makeText(
                        requireContext(),
                        "Login ou senha incorreto!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun callNewPassword() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("NOVA SENHA")
            .setMessage("Por ser seu primeiro acesso ao UNI-Rank, você deve defenir uma nova senha!")
            .setPositiveButton("OK") { dialog, which ->
                findNavController().navigate(R.id.action_loginFragment_to_newPasswordFragment)
            }
            .show()
    }

}