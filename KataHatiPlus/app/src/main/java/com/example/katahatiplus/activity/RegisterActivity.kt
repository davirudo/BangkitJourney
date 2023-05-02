package com.example.katahatiplus.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.katahatiplus.databinding.ActivityRegisterBinding
import com.example.katahatiplus.response.RegisterResponse
import com.example.katahatiplus.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val api by lazy { ApiConfig.getApiService() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()

        binding.btnSignUp.setOnClickListener {
            Toast.makeText(this, "Sign Up.....", Toast.LENGTH_SHORT).show()

            val edName = binding.edRegisterName.text.toString()
            val edEmail = binding.edRegisterEmail.text.toString()
            val edPassword = binding.edRegisterPassword.text.toString()

            api.register(edName, edEmail, edPassword)
                .enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        Log.e("SignUpResponse", response.toString())
                        val correct = response.isSuccessful
                        if (correct) {

                            Toast.makeText(applicationContext, "Success!", Toast.LENGTH_SHORT)
                                .show()
                            val moveIntent =
                                Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(moveIntent)
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Log.e("SignUpError", t.toString())
                        Toast.makeText(
                            applicationContext,
                            "Something's wrong :(",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
    private fun playAnimation() {
        val joinUs = ObjectAnimator.ofFloat(binding.joinUs, View.ALPHA, 1f).setDuration(500)
        val name = ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.btnSignUp, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(joinUs, name, email, password, signup)
            start()
        }
    }
}