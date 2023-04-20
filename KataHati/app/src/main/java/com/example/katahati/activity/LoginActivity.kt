package com.example.katahati.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        val edEmail = binding.edLoginEmail
        val edPassword = binding.edLoginPassword

        binding.btnLogin.setOnClickListener {
            Toast.makeText(this, "Sign In.....", Toast.LENGTH_SHORT).show()

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
}