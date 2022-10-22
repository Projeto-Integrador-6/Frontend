package com.pi.ativas.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pi.ativas.MainActivity
import com.pi.ativas.R
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.common.TextChangedListener
import com.pi.ativas.data.bodys.LoginBody
import com.pi.ativas.databinding.FragmentLoginBinding
import com.pi.ativas.model.User
import com.pi.ativas.teacher.model.DataForRequirement
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        initViews()
        initObservers()
        checkFields()
        return binding.root
    }

    override fun initViews() {
        with(binding) {
            txtLogin.addTextChangedListener(object :
                TextChangedListener<EditText>(txtLogin) {
                override fun onTextChanged(target: EditText, p0: Editable?) {
                    loginInputLayout.error = null
                    checkFields()
                }
            })

            txtSenha.addTextChangedListener(object :
                TextChangedListener<EditText>(txtSenha) {
                override fun onTextChanged(target: EditText, p0: Editable?) {
                    checkFields()
                }
            })

            btnLogar.setOnClickListener {
                progressBarLogin.visibility = View.VISIBLE
                loginViewModel.newLogin(
                    txtLogin.text.toString(),
                    txtSenha.text.toString()
                )
            }
        }
    }

    override fun initObservers() {
        with(loginViewModel) {
            invalidCredential.observe(viewLifecycleOwner) {
                if (it) {
                    binding.loginInputLayout.error = "Email ou senha incorretos"
                    alertDialog(
                        "credencias inválida",
                        "Email ou senha incorretos, favor verifique e tente novamente!"
                    )
                    binding.progressBarLogin.visibility = View.GONE
                }
            }

            inactiveAccount.observe(viewLifecycleOwner) {
                if (it) {
                    binding.loginInputLayout.error = "Sua conta está inativada"
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
                        newPassword(bodyLogin)
                    }
                }
            }

            user.observe(viewLifecycleOwner) { user ->
                userIsStudent.observe(viewLifecycleOwner) { isUser ->
                    dataRequisition.observe(viewLifecycleOwner) { dataLogin ->
                        binding.progressBarLogin.visibility = View.GONE
                        if (isUser) {
                            goToHomeStudent(
                                user, DataForRequirement(
                                    email = dataLogin.email,
                                    password = dataLogin.password,
                                    token = dataLogin.token
                                )
                            )
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
        with(binding) {
            if (txtLogin.text.isNullOrBlank() || txtSenha.text.isNullOrBlank()) {
                btnLogar.isClickable = false
                btnLogar.background.setTint(resources.getColor(R.color.gray_100))
            } else {
                btnLogar.isClickable = true
                btnLogar.background.setTint(resources.getColor(R.color.backgroundBottom))
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

    private fun goToHomeStudent(student: User, dataForRequirement: DataForRequirement) {
        val activity: MainActivity = activity as MainActivity
        activity.getDrawerStudent()
        activity.setNavHeader("Vinicius crispim de Azevedo", "vinicrispim02@hotmail.com")

        sharedPreferences.edit()
            .putBoolean("isLoggedUser", true)
            .putBoolean("isStudent", false)
            .putString("email", dataForRequirement.email)
            .putString("password", dataForRequirement.password)
            .putString("token", dataForRequirement.token)
            .apply()

        val action = LoginFragmentDirections.actionLoginFragmentToHomeStudentFragment()
        findNavController().navigate(action)
    }

    private fun goToHomeTeacher(teacher: User, dataForRequirement: DataForRequirement) {
        val activity: MainActivity = activity as MainActivity
        activity.getDrawerTeatcher()
        activity.setNavHeader(teacher.name ?: "null", teacher.email ?: "null")

        sharedPreferences.edit()
            .putBoolean("isLoggedUser", true)
            .putBoolean("isStudent", false)
            .putString("email", dataForRequirement.email)
            .putString("password", dataForRequirement.password)
            .putString("token", dataForRequirement.token)
            .apply()

        findNavController().navigate(R.id.action_loginFragment_to_homeTeacherFragment)
    }
}

