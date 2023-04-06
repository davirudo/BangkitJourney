package com.example.costumview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var myButton: MyButton
    private lateinit var myEditText: MyEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myButton = findViewById(R.id.myButton)
        myEditText = findViewById(R.id.myEditText)

        setMyButton()
    }

    private fun setMyButton() {
        val result = myEditText.text
        myButton.isEnabled = myEditText.text.toString().isNotEmpty()
    }
}