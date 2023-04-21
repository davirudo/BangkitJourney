package com.example.katahati.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.example.katahati.R
import com.example.katahati.activity.LoginActivity
import com.example.katahati.databinding.FragmentAddBinding
import com.example.katahati.response.AddResponse
import com.example.katahati.retrofit.ApiConfig
import com.example.katahati.utils.SessionManager
import com.example.katahati.utils.createCustomTempFile
import com.example.katahati.utils.reduceFileImage
import com.example.katahati.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var currentPhotoPath: String

    private var getFile: File? = null

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf("android.permission.CAMERA")
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    requireContext(),
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivPhoto = binding.ivPhoto
        val ivCamera = binding.ivCamera
        val ivFile = binding.ivFile

        ivPhoto.setOnClickListener {
            ivCamera.visibility = View.VISIBLE
            ivFile.visibility = View.VISIBLE
            ivPhoto.visibility = View.GONE
        }

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.ivCamera.setOnClickListener { startTakePhoto() }
        binding.ivFile.setOnClickListener { startGallery() }
        binding.buttonAdd.setOnClickListener { uploadStory() }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireContext().packageManager)

        createCustomTempFile(requireActivity().application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.katahati",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Pilih Gambar")
        launcherIntentGallery.launch(chooser)
    }

    private fun uploadStory() {
        Toast.makeText(requireContext(), "Posting.....", Toast.LENGTH_SHORT).show()
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)

            StoryFragment.sessionManager = SessionManager(requireContext())
            val token = LoginActivity.sessionManager.getString("TOKEN")
            val description = binding.edAddDescription.text.toString().toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            val apiService = ApiConfig.getApiService()
            val uploadImageRequest = apiService.addNewStory(token.toString(), imageMultipart, description)
            uploadImageRequest.enqueue(object : Callback<AddResponse> {
                override fun onResponse(call: Call<AddResponse>, response: Response<AddResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null && !responseBody.error) {
                            Toast.makeText(requireContext(), responseBody.message, Toast.LENGTH_SHORT).show()
                            findNavController().navigate(AddFragmentDirections.actionAddFragmentToStoryFragment())
                        }
                    } else {
                        Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<AddResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(requireContext(), "Masukin foto atau gambar dulu ya.", Toast.LENGTH_SHORT).show()
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            myFile.let { file ->
                getFile = file
                binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, requireContext())
                getFile = myFile
                binding.previewImageView.setImageURI(uri)
            }
        }
    }
}