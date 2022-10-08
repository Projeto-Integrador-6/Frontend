package com.pi.ativas.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pi.ativas.MainActivity
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.data.bodys.LoginBody
import com.pi.ativas.databinding.FragmentLoginBinding
import com.pi.ativas.model.User
import com.pi.ativas.teacher.model.DataForRequirement
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        initViews()
        initObservers()
        return binding.root
    }

    override fun initViews() {
        with(binding) {
            btnLogar.setOnClickListener {
                checkFields()
            }
        }
    }

    override fun initObservers() {
        with(loginViewModel) {
            invalidCredential.observe(viewLifecycleOwner) {
                if (it) {
                    alertDialog(
                        "credencias inválida",
                        "Email ou senha incorretos, favor verifique e tente novamente!"
                    )
                    binding.progressBarLogin.visibility = View.GONE
                }
            }

            inactiveAccount.observe(viewLifecycleOwner) {
                if (it) {
                    alertDialog(
                        "Conta inativa",
                        "Sua conta está inativada, favor contade sua instituição!"
                    )
                    binding.progressBarLogin.visibility = View.GONE
                }
            }

            newPassword.observe(viewLifecycleOwner) {
                if (it) {
                    binding.progressBarLogin.visibility = View.GONE
                    dataLogin.value?.let { bodyLogin ->
                        this@LoginFragment.newPassword(bodyLogin)
                    }
                }
            }

            user.observe(viewLifecycleOwner) { user ->
                userIsStudent.observe(viewLifecycleOwner) { isUser ->
                    dataRequisition.observe(viewLifecycleOwner) { dataLogin ->
                        binding.progressBarLogin.visibility = View.GONE
                        if (isUser) {
                            goToHomeStudent(user)
                        } else {
                            goToHomeTeacher(
                                user,
                                DataForRequirement(
                                    email = dataLogin.email,
                                    password = dataLogin.password,
                                    token = dataLogin.token
                                )
                            )
                        }
                    }

                }
            }
        }
    }

    private fun checkFields() {
        // TODO: Deixar o botão "Entrar" clicavel apenas se login e senha inseridos!
        with(binding) {
            val login = "teacher@teste.com"//txtLogin.text.toString()
            val password = "Teste12@" //txtSenha.text.toString()

            if (login.isNotEmpty() && password.isNotEmpty()) {
                progressBarLogin.visibility = View.VISIBLE
                loginViewModel.newLogin(login, password)
            } else {
                Toast.makeText(requireContext(), "Favor inserir login e senha!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun alertDialog(
        title: String,
        message: String,
        positiveButton: String = "Ok"
    ) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButton) { dialog, which ->
            }
            .show()
    }

    private fun newPassword(dataLogin: LoginBody) {
        val action =
            LoginFragmentDirections.actionLoginFragmentToNewPasswordFragment(dataLogin.getArray())
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("NOVA SENHA")
            .setMessage("Por ser seu primeiro acesso ao UNI-Rank, você deve defenir uma nova senha!")
            .setPositiveButton("OK") { dialog, which ->
                findNavController().navigate(action)
            }
            .show()
    }

    private fun goToHomeStudent(student: User) {
        val activity: MainActivity = activity as MainActivity
        activity.getDrawerStudent()
        activity.setNavHeader("Vinicius crispim de Azevedo", "vinicrispim02@hotmail.com")
        val action = LoginFragmentDirections.actionLoginFragmentToHomeStudentFragment()
        findNavController().navigate(action)
    }

    private fun goToHomeTeacher(teacher: User, dataForRequirement: DataForRequirement) {
        val activity: MainActivity = activity as MainActivity
        activity.getDrawerTeatcher()
        activity.setNavHeader(teacher.name ?: "null", teacher.email ?: "null")
        val action = LoginFragmentDirections.actionLoginFragmentToHomeTeacherFragment(
            teacher,
            dataForRequirement
        )
        findNavController().navigate(action)
    }

}

