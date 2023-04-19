package com.example.katahati.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.katahati.R
import com.example.katahati.databinding.FragmentAddBinding
import com.example.katahati.databinding.FragmentStoryBinding

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding

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
                btnVisible = true
            } else {
                ivCamera.visibility = View.GONE
                ivFile.visibility = View.GONE
                btnVisible = false
            }
        }


    }



}