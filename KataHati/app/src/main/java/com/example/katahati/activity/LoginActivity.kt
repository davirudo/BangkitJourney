package com.example.katahati.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.katahati.databinding.ActivityLoginBinding
import com.example.katahati.response.LoginResponse
import com.example.katahati.retrofit.ApiConfig
import com.example.katahati.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    companion object {
        lateinit var sessionManager: SessionManager
    }

    private lateinit var binding: ActivityLoginBinding
    private val api by lazy { ApiConfig.getApiService() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        val loginStatus = sessionManager.getBoolean("LOGIN_STATUS")
        if(loginStatus) {
            val moveIntent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(moveIntent)
            finish()
        }

        playAnimation()

        val edEmail = binding.edLoginEmail
        val edPassword = binding.edLoginPassword

        binding.btnLogin.setOnClickListener {
            Toast.makeText(this, "Sign In.....", Toast.LENGTH_SHORT).show()

            //sebaiknya di ViewModel kan
            api.login(edEmail.text.toString(), edPassword.text.toString())
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        Log.e("SignInResponse", response.toString())
                        val correct = response.isSuccessful

                        if (correct) {
                            val token = response.body()!!.loginResult.token

                            sessionManager.saveString("TOKEN", "Bearer $token")
                            sessionManager.saveBoolean("LOGIN_STATUS", true)


                            val moveIntent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(moveIntent)
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.e("SignInError", t.toString())
                        Toast.makeText(applicationContext, "Something's wrong :(", Toast.LENGTH_SHORT).show()
                    }

                })
        }

        binding.tvSignUp.setOnClickListener {
            val moveIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(moveIntent)
        }
    }
    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_Y, -30f, 30f).apply {
            duration = 2000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val email = ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val donthave = ObjectAnimator.ofFloat(binding.tvDontHave, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.tvSignUp, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(donthave, signup)
        }

        AnimatorSet().apply {
            playSequentially(email, password, login, together)
            start()
        }
    }
}