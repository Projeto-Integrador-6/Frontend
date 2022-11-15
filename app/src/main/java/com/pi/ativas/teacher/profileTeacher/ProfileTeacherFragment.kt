package com.pi.ativas.teacher.profileTeacher

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.pi.ativas.R
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.data.bodys.LoginBody
import com.pi.ativas.databinding.FragmentProfileTeacherBinding
import com.pi.ativas.teacher.model.DataForRequirement
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileTeacherFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileTeacherBinding
    private val profileTeacherViewModel: ProfileTeacherViewModel by viewModel()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dataForRequirement: DataForRequirement
    //private lateinit var teacherProfile: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE)

        with(sharedPreferences) {
            getString("email", "")?.let { email ->
                getString("password", "")?.let { password ->
                    getString("token", "")?.let { token ->
                        dataForRequirement = DataForRequirement(email, password, token)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileTeacherBinding.inflate(layoutInflater)

        initObservers()
        initViews()
        return binding.root
    }

    override fun initObservers() {
        with(binding) {
            btnVerCurriculo.setOnClickListener {
                if (!txtSeusDadosCurriculo.text?.startsWith("http://")!! && !txtSeusDadosCurriculo.text?.startsWith(
                        "https://"
                    )!!
                ) {
                    txtSeusDadosCurriculo.setText("http://" + txtSeusDadosCurriculo.text.toString())
                }
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(txtSeusDadosCurriculo.text.toString())
                startActivity(i)
            }
        }
    }

    override fun initViews() {
        with(binding) {

            val loginBody = LoginBody(
                email = dataForRequirement.email,
                password = dataForRequirement.password,
                token = dataForRequirement.token
            )
            progressBarLogin.visibility = View.VISIBLE
            profileTeacherViewModel.getProfile(loginBody)
            profileTeacherViewModel.profileTeacher.observe(viewLifecycleOwner) {
                txtSeusDadosNome.setText(it.name)
                txtSeusDadosEmail.setText(it.email)
                txtSeusDadosContato.setText(it.phone)
                txtSeusDadosCurriculo.setText(it.lattes)
                txtSeusDadosAniversario.setText(it.birthday)
                try {
                    it.photo?.let { photo ->
                        val imagemBites: ByteArray
                        imagemBites = Base64.decode(photo, Base64.DEFAULT)
                        val imagemdecodificada =
                            BitmapFactory.decodeByteArray(imagemBites, 0, imagemBites.size)
                        val bitmapRound =
                            RoundedBitmapDrawableFactory.create(resources, imagemdecodificada)
                        bitmapRound.cornerRadius = 1000f
                        imgFoto.setImageDrawable(bitmapRound)
                    } ?: imgFoto.setImageResource(R.drawable.image_example)
                }catch (e: java.lang.Exception){
                    imgFoto.setImageResource(R.drawable.image_example)
                }

                progressBarLogin.visibility = View.GONE

            }
        }
    }

}