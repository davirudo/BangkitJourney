package com.example.penghitungbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var edWidth: EditText
    private lateinit var edHeight: EditText
    private lateinit var edLength: EditText
    private lateinit var btnCalculate:  Button
    private lateinit var tvResult: TextView

    companion object {
        private const val STATE_RESULT = "state_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edWidth = findViewById(R.id.ed_width)
        edHeight = findViewById(R.id.ed_height)
        edLength = findViewById(R.id.ed_length)
        btnCalculate = findViewById(R.id.btn_calculate)
        tvResult = findViewById(R.id.tv_result)

        btnCalculate.setOnClickListener(this)

        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(STATE_RESULT)
            tvResult.text = result
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, tvResult.text.toString())
    }

    override fun onClick(p0: View?) {
        if(p0?.id == R.id.btn_calculate) {
            val inputLength = edLength.text.toString().trim()
            val inputWidth = edWidth.text.toString().trim()
            val inputHeight = edHeight.text.toString().trim()

            var isEmptyFields = false

            if (inputLength.isEmpty()) {
                isEmptyFields = true
                edLength.error = "Kolom jangan kosong"
            }
            if (inputWidth.isEmpty()) {
                isEmptyFields = true
                edWidth.error = "Kolom jangan kosong"
            }
            if (inputHeight.isEmpty()) {
                isEmptyFields = true
                edHeight.error = "Kolom jangan kosong"
            }

            if(!isEmptyFields) {
                val volume = inputLength.toDouble() * inputWidth.toDouble() * inputHeight.toDouble()
                tvResult.text = volume.toString()
            }
        }
    }

}