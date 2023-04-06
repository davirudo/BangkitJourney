package com.example.costumview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var myButton: MyButton
    private lateinit var myEditText: MyEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myButton = findViewById(R.id.myButton)
        myEditText = findViewById(R.id.myEditText)

        setMyButton()

        myEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                setMyButton()
            }

            override fun afterTextChanged(p0: Editable) {
                //
            }
        })

        myButton.setOnClickListener {
            Toast.makeText(this@MainActivity, myEditText.text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setMyButton() {
        val result = myEditText.text
        myButton.isEnabled = myEditText.text.toString().isNotEmpty()
    }
}