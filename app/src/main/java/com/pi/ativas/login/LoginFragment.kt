package com.pi.ativas.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.pi.ativas.R
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
                //fazia login como professor apenas para testar
                findNavController().navigate(R.id.action_loginFragment_to_homeTeacherFragment)
            }
            else {
//                Toast.makeText(requireContext(), "Login ou senha incorreto!", Toast.LENGTH_SHORT).show()
                //fazia login como estudante apenas para testar
                findNavController().navigate(R.id.action_loginFragment_to_homeStudentFragment)
            }
        }

    }

}