package com.example.katahati.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.katahati.R
import com.example.katahati.activity.LoginActivity
import com.example.katahati.activity.LoginActivity.Companion.sessionManager
import com.example.katahati.databinding.FragmentAddBinding
import com.example.katahati.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            sessionManager.clearSession()

            val moveIntent = Intent(activity, LoginActivity::class.java)
            startActivity(moveIntent)
            activity?.finish()
        }

        setupAction()
    }

    private fun setupAction() {
        binding.btnChange.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }
}