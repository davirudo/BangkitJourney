package com.example.katahati.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.example.katahati.R
import com.example.katahati.databinding.ActivityRegisterBinding
import com.example.katahati.response.RegisterResponse
import com.example.katahati.retrofit.ApiConfig
import com.example.katahati.utils.PasswordEditText
import com.example.katahati.utils.SessionManager
import com.example.katahati.utils.SignUpButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityRegisterBinding
//    private lateinit var myButton: SignUpButton
//    private lateinit var edPassword: PasswordEditText
    private lateinit var binding: ActivityRegisterBinding
    private val api by lazy { ApiConfig.getApiService() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        myButton = findViewById(R.id.sign_up_button)
        val edname = binding.nameEditText
        val edemail = binding.emailEditText
        val edpassword = binding.passwordEditText

        binding.btnSignUp.setOnClickListener{
            Toast.makeText(this, "Sign Up.....", Toast.LENGTH_SHORT).show()

            api.register(edname.text.toString(), edemail.text.toString(), edpassword.text.toString())
                .enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        Log.e("SignUpResponse", response.toString())
                        val correct = response.isSuccessful
                        if (correct) {

                            Toast.makeText(applicationContext, "Success!", Toast.LENGTH_SHORT).show()
                            val moveIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(moveIntent)
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Log.e("SignUpError", t.toString())
                        Toast.makeText(applicationContext, "Something's wrong :(", Toast.LENGTH_SHORT).show()

                    }

                })
        }


//        edPassword.addTextChangedListener(object: TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
//                //
//            }
//
//            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
////                setMyButton()
//            }
//
//            override fun afterTextChanged(p0: Editable) {
//                //
//            }
//        })

//        myButton.setOnClickListener {
//            Toast.makeText(this@RegisterActivity, myEditText.text, Toast.LENGTH_SHORT).show()
//        }
    }

//    private fun setMyButton() {
//        val result = myEditText.text
//        myButton.isEnabled = myEditText.text.toString().isNotEmpty()
//    }



}