package com.pi.ativas.firstLogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pi.ativas.R
import com.pi.ativas.databinding.FragmentNewPasswordBinding

class NewPasswordFragment : Fragment(){

    private lateinit var binding: FragmentNewPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewPasswordBinding.inflate(layoutInflater)
        initViews()

        return binding.root
    }


    private fun initViews(){
        with(binding){
            btnConfirmar.setOnClickListener {
                confirmNewPasswordLayout.error = null
                newPasswordLayout.error = null
                if (isValidPassword(txtNewPassword.text.toString().trim())){
                    if(txtNewPassword.text.toString() == txtConfirmNewPassword.text.toString()){
                        callNewPassword()
                    }else{
                        confirmNewPasswordLayout.error = "Confirmação de senha incorreta!"
                    }

                }else {
                    newPasswordLayout.error = "Senha muito fraca!"
                }
            }
        }
    }

    private fun callNewPassword(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("NOVA SENHA DEFINIDA COM SUCESSO")
            .setMessage("Volte a tela de login e use suas novas credenciais!")
            .setPositiveButton("OK") { dialog, which ->
                findNavController().navigate(R.id.action_newPasswordFragment_to_loginFragment)
            }
            .show()
    }


}