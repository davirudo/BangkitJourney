package com.example.katahati.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.katahati.databinding.ActivityRegisterBinding
import com.example.katahati.response.RegisterResponse
import com.example.katahati.retrofit.ApiConfig
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

        val edname = binding.edRegisterName
        val edemail = binding.edRegisterEmail
        val edpassword = binding.edRegisterPassword

        binding.btnSignUp.setOnClickListener {
            Toast.makeText(this, "Sign Up.....", Toast.LENGTH_SHORT).show()

            api.register(
                edname.text.toString(),
                edemail.text.toString(),
                edpassword.text.toString()
            )
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
}