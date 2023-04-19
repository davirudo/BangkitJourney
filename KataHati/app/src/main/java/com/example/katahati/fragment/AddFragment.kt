package com.example.katahati.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.katahati.R
//import com.example.katahati.activity.CameraActivity
import com.example.katahati.databinding.FragmentAddBinding
import com.example.katahati.databinding.FragmentStoryBinding
import java.io.File

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var currentPhotoPath: String

    private var getFile: File? = null

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf("android.permission.CAMERA")
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

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

    var btnVisible = false

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
            if (!btnVisible) {
                ivCamera.visibility = View.VISIBLE
                ivFile.visibility = View.VISIBLE
                ivPhoto.visibility = View.GONE
                btnVisible = true
            } else {
//                ivCamera.visibility = View.GONE
//                ivFile.visibility = View.GONE
//                btnVisible = false
            }
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
//        binding.iv.setOnClickListener { uploadImage() }


    }

    //Ini Camera X
//    private fun startCameraX() {
//        val intent = Intent(requireContext(), CameraActivity::class.java)
//        launcherIntentCameraX.launch(intent)
//    }

    //Ini Camera biasa
    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        launcherIntentCamera.launch(intent)
    }

    private fun startGallery() {
        Toast.makeText(
            requireContext(),
            "ini galeri.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun uploadStory() {
        Toast.makeText(
            requireContext(),
            "ini upload.",
            Toast.LENGTH_SHORT
        ).show() }

    //ini Camera biasa
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val imageBitmap = it.data?.extras?.get("data") as Bitmap
            binding.previewImageView.setImageBitmap(imageBitmap)
        }
    }

    //ini Camera X
//    private val launcherIntentCameraX = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) {
//        if (it.resultCode == CAMERA_X_RESULT) {
//            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                it.data?.getSerializableExtra("picture", File::class.java)
//            } else {
//                @Suppress("DEPRECATION")
//                it.data?.getSerializableExtra("picture")
//            } as? File
////            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
//
//            myFile?.let { file ->
//                getFile = file
//                binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
//            }
//        }
//    }



}