package com.pi.ativas.student.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pi.ativas.R
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.data.bodys.ReportBody
import com.pi.ativas.databinding.FragmentResponseTaskStudentBinding
import com.pi.ativas.student.viewmodel.ResponseTaskStudentViewModel
import com.pi.ativas.util.ApiFileUtils
import com.pi.ativas.util.DateUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ResponseTaskStudentFragment : BaseFragment() {

    private lateinit var binding: FragmentResponseTaskStudentBinding
    private val responseTaskStudentViewModel: ResponseTaskStudentViewModel by viewModel()
    private val responseTaskStudentFragmentArgs: ResponseTaskStudentFragmentArgs by navArgs()
    private lateinit var sharedPreferences: SharedPreferences
    var photoFile: File? = null
    val CAPTURE_IMAGE_REQUEST = 1
    var mCurrentPhotoPath: String? = null
    var photoURI: Uri? = null
    private var isPhoto: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResponseTaskStudentBinding.inflate(layoutInflater)
        initViews()
        initObservers()

        return binding.root
    }

    override fun initObservers() {
        with(responseTaskStudentViewModel) {
            error.observe(viewLifecycleOwner) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Erro")
                    .setMessage(it)
                    .setPositiveButton("Ok") { dialog, which ->
                    }
                    .show()
            }
            response.observe(viewLifecycleOwner) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Atividade respondida!")
                    .setMessage("Você respondeu essa atividade!")
                    .setPositiveButton("Ok") { dialog, which ->
                        findNavController().popBackStack(R.id.homeStudentFragment, false)
                    }
                    .show()
            }
        }
    }

    override fun initViews() {
        with(responseTaskStudentFragmentArgs.task) {
            binding.titleTask.text = question_title
            binding.questionTask.text = question
            binding.btnMenu.setOnClickListener {
                Toast.makeText(requireContext(), "Click!", Toast.LENGTH_SHORT).show()
            }
            binding.btnPhoto.setOnClickListener {
                captureImage()
            }
            binding.btnEnviar.setOnClickListener {
                checkSend()
            }
        }
    }

    private fun checkSend() {
        if (binding.responseEditText.text.isNullOrEmpty() && !isPhoto) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Responsa a questão!")
                .setMessage("Você só pode enviar a questão após responde-la! Pode tirar uma foto se quiser!")
                .setPositiveButton("Ok") { dialog, which ->
                }
                .show()
        } else if (binding.responseEditText.text.isNullOrEmpty()) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Texto da resposta vazio!")
                .setMessage("Deseja enviar a resposta sem texto?")
                .setPositiveButton("Sim") { dialog, which ->
                    val reportBody = ReportBody(
                        email = sharedPreferences.getString("email", "")!!,
                        password = sharedPreferences.getString("password", "")!!,
                        token = sharedPreferences.getString("token", "")!!,
                        date = DateUtils().getDate(),
                        answer = binding.responseEditText.text.toString(),
                        anexo = getImageResponse(),
                        teamId = responseTaskStudentFragmentArgs.task.id
                    )
                    responseTaskStudentViewModel.setResponse(reportBody)

                }
                .setNegativeButton("Não") { dialog, which ->

                }
                .show()
        } else {
            val reportBody = ReportBody(
                email = sharedPreferences.getString("email", "")!!,
                password = sharedPreferences.getString("password", "")!!,
                token = sharedPreferences.getString("token", "")!!,
                date = DateUtils().getDate(),
                answer = binding.responseEditText.text.toString(),
                anexo = getImageResponse(),
                teamId = responseTaskStudentFragmentArgs.task.id
            )
            responseTaskStudentViewModel.setResponse(reportBody)
        }
    }


    private fun getImageResponse(): String? {
        return if (photoURI == null) {
            null
        } else{
            val imageString = ApiFileUtils().encode(requireContext(), photoURI!!)
            imageString
        }
    }

    private fun captureImage() {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                0
            )
        } else {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
                // Create the File where the photo should go
                try {
                    photoFile = createImageFile()
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoURI = FileProvider.getUriForFile(
                            requireContext(),
                            "com.pi.ativas.fileprovider",
                            photoFile!!
                        )
                        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST)
                    }
                } catch (ex: Exception) {
                    // Error occurred while creating the File
                    displayMessage(ex.message.toString())
                }

            } else {
                displayMessage("Null")
            }
        }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    private fun displayMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val filePhotoPath = photoFile!!.absolutePath
            val file = photoFile!!
            val imageOriginal = BitmapFactory.decodeFile(filePhotoPath)
            val myBitmap = Bitmap.createScaledBitmap(imageOriginal, 600, 600, true)

            binding.imageResponse.setImageBitmap(myBitmap)
            binding.imageResponse.visibility = View.VISIBLE
            isPhoto = true

        } else {
            displayMessage("Request cancelled or something went wrong.")
        }
    }
}