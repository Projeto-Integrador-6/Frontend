package com.pi.ativas.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.pi.ativas.databinding.FragmentLoginBinding

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

    private fun initViews(){
        with(binding){
            btnLogar.setOnClickListener {
                checkLogin()
            }
        }
    }

    private fun checkLogin(){
        // TODO: Deixar o botão "Entrar" clicavel apenas se login e senha inseridos!
        with(binding){
            if ((txtLogin.text.toString().isNotEmpty()) && (txtSenha.text.toString().isNotEmpty())){
                Toast.makeText(requireContext(), "Favor inserir login e senha!", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(requireContext(), "Login ou senha incorreto!", Toast.LENGTH_SHORT).show()
            }
        }

    }

}