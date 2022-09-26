package com.pi.ativas.firstLogin

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pi.ativas.R
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.common.TextChangedListener
import com.pi.ativas.data.bodys.LoginBody
import com.pi.ativas.data.bodys.NewPasswordBody
import com.pi.ativas.databinding.FragmentNewPasswordBinding
import com.pi.ativas.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewPasswordFragment : BaseFragment() {

    private lateinit var binding: FragmentNewPasswordBinding
    private val newPasswordViewModel: NewPasswordViewModel by viewModel()
    private val args: NewPasswordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNewPasswordBinding.inflate(layoutInflater)
        initObservers()
        initViews()
        return binding.root
    }


    override fun initViews() {
        with(binding) {
            txtNewPassword.addTextChangedListener(object :
                TextChangedListener<EditText>(txtNewPassword) {
                override fun onTextChanged(target: EditText, p0: Editable?) {
                    newPasswordLayout.error = null
                }
            })

            txtConfirmNewPassword.addTextChangedListener(object :
                TextChangedListener<EditText>(txtConfirmNewPassword) {
                override fun onTextChanged(target: EditText, p0: Editable?) {
                    confirmNewPasswordLayout.error = null
                }
            })

            btnConfirmar.setOnClickListener {
                if (isValidPassword(txtNewPassword.text.toString().trim())) {
                    if (txtNewPassword.text.toString() == txtConfirmNewPassword.text.toString()) {
                        val newPasswordBody = NewPasswordBody(
                            email = args.loginData[0],
                            password = args.loginData[1],
                            token = args.loginData[2],
                            oldPassword = args.loginData[1],
                            newPassword =  txtNewPassword.text.toString().trim()
                        )
                        newPasswordViewModel.newPassword(newPasswordBody)
                    } else {
                        confirmNewPasswordLayout.error = "Confirmação de senha incorreta!"
                    }

                } else {
                    newPasswordLayout.error = "Senha muito fraca!"
                }
            }
        }
    }

    override fun initObservers() {
        newPasswordViewModel.success.observe(viewLifecycleOwner) {
            callNewPassword()
        }
        newPasswordViewModel.success1.observe(viewLifecycleOwner) {
            callNewPassword1()
        }
    }


    private fun callNewPassword() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("NOVA SENHA DEFINIDA COM SUCESSO")
            .setMessage("Volte a tela de login e use suas novas credenciais!")
            .setPositiveButton("OK") { dialog, which ->
                findNavController().navigate(R.id.action_newPasswordFragment_to_loginFragment)
            }
            .show()
    }

    private fun callNewPassword1() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("ERRO")
            .setMessage("Ocorreu um erro, tente novamente!")
            .setPositiveButton("OK") { dialog, which ->

            }
            .show()
    }

}