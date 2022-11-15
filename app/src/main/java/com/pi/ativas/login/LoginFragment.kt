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
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pi.ativas.MainActivity
import com.pi.ativas.R
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.common.TextChangedListener
import com.pi.ativas.data.bodys.LoginBody
import com.pi.ativas.databinding.FragmentLoginBinding
import com.pi.ativas.util.DATA_USER
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(DATA_USER, Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).setTittleAppBar("Login")
        binding = FragmentLoginBinding.inflate(layoutInflater)
        initViews()
        initObservers()
        //checkFields()
        binding.btnLogar.isClickable = true
        binding.btnLogar.background.setTint(resources.getColor(R.color.backgroundBottom))
        return binding.root
    }

    override fun initViews() {
        with(binding) {
            txtLogin.addTextChangedListener(object :
                TextChangedListener<EditText>(txtLogin) {
                override fun onTextChanged(target: EditText, p0: Editable?) {
                    loginInputLayout.error = null
                    //checkFields()
                }
            })

            txtSenha.addTextChangedListener(object :
                TextChangedListener<EditText>(txtSenha) {
                override fun onTextChanged(target: EditText, p0: Editable?) {
                    //checkFields()
                }
            })

            btnLogar.setOnClickListener {
                progressBarLogin.visibility = View.VISIBLE
                bottomSheetBG.visibility = View.VISIBLE
                /*loginViewModel.newLogin(
                    txtLogin.text.toString(),
                    txtSenha.text.toString()
                )*/
                if (txtLogin.text.toString().isEmpty()){
                    loginViewModel.newLogin(
                        "gabrieltoledo@gmail.com",
                        "Gabriel12@"
                    )
                } else {
                    loginViewModel.newLogin(
                        "arthur_morgan@gmail.com",
                        "Arthur12@"
                    )
                }
            }
        }
    }

    override fun initObservers() {
        with(loginViewModel) {

            error.observe(viewLifecycleOwner) {
                alertDialog(
                    "Erro $it!",
                    "Ocorreu um erro inesperado! Tente novamente!"
                )
                binding.progressBarLogin.visibility = View.GONE
                binding.bottomSheetBG.visibility = View.GONE
            }

            invalidCredential.observe(viewLifecycleOwner) {
                if (it) {
                    binding.loginInputLayout.error = "Email ou senha incorretos"
                    alertDialog(
                        "credencias inválida",
                        "Email ou senha incorretos, favor verifique e tente novamente!"
                    )
                    binding.progressBarLogin.visibility = View.GONE
                    binding.bottomSheetBG.visibility = View.GONE
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
                    binding.bottomSheetBG.visibility = View.GONE
                }
            }

            newPassword.observe(viewLifecycleOwner) { newPassword ->
                userIsStudent.observe(viewLifecycleOwner) { isStudent ->
                    if (newPassword) {
                        binding.progressBarLogin.visibility = View.GONE
                        binding.bottomSheetBG.visibility = View.GONE
                        dataLogin.value?.let { bodyLogin ->
                            newPassword(bodyLogin, isStudent)
                        }
                    }
                }
            }

            user.observe(viewLifecycleOwner) { user ->
                userIsStudent.observe(viewLifecycleOwner) { isStudent ->
                    dataRequisition.observe(viewLifecycleOwner) { dataLogin ->
                        binding.progressBarLogin.visibility = View.GONE

                        (activity as MainActivity).setNavHeader(
                            user.name ?: "null",
                            user.email ?: "null"
                        )

                        sharedPreferences.edit()
                            .putBoolean("isLoggedUser", true)
                            .putBoolean("isStudent", isStudent)
                            .putString("email", dataLogin.email)
                            .putString("password", dataLogin.password)
                            .putString("token", dataLogin.token)
                            .putString("userName", user.name)
                            .putString("userEmail", user.email)
                            .apply()

                        if (isStudent) {
                            (activity as MainActivity).getDrawerStudent()
                            findNavController().navigate(R.id.action_loginFragment_to_homeStudentFragment)
                        } else {
                            (activity as MainActivity).getDrawerTeatcher()
                            findNavController().navigate(R.id.action_loginFragment_to_homeTeacherFragment)
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

    private fun newPassword(dataLogin: LoginBody, isStudent: Boolean) {
        val action =
            LoginFragmentDirections.actionLoginFragmentToNewPasswordFragment(
                dataLogin.getArray(),
                isStudent
            )
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("NOVA SENHA")
            .setMessage("Por ser seu primeiro acesso ao UNI-Rank, você deve defenir uma nova senha!")
            .setPositiveButton("OK") { dialog, which ->
                findNavController().navigate(action)
            }
            .show()
    }
}

