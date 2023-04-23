package com.example.katahati.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.katahati.R

class PasswordEditText : AppCompatEditText, View.OnTouchListener {

    private lateinit var eyeButtonImage: Drawable
    private var isPasswordVisible: Boolean = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Password"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        eyeButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_eye) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty()) showEyeButton() else hideEyeButton()
                error = if (p0.length < 8) context.getString(R.string.eight_char) else null
            }

            override fun afterTextChanged(p0: Editable) {}
        })
    }

    private fun showEyeButton() {
        setButtonDrawable(endOfTheText = eyeButtonImage)
    }

    private fun hideEyeButton() {
        setButtonDrawable()
    }

    private fun setButtonDrawable(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(startOfTheText, topOfTheText, endOfTheText, bottomOfTheText)
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val eyeButtonStart: Float
            val eyeButtonEnd: Float

            var isEyeButtonClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                eyeButtonEnd = (eyeButtonImage.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < eyeButtonEnd -> isEyeButtonClicked = true
                }
            } else {
                eyeButtonStart = (width - paddingEnd - eyeButtonImage.intrinsicWidth).toFloat()
                when {
                    event.x > eyeButtonStart -> isEyeButtonClicked = true
                }
            }

            if (isEyeButtonClicked) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        togglePasswordVisibility()
                        return true
                    }
                    else -> return false
                }
            } else return false
        }
        return false
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            transformationMethod = PasswordTransformationMethod.getInstance()
            eyeButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_eye) as Drawable
            isPasswordVisible = false
        } else {
            transformationMethod = HideReturnsTransformationMethod.getInstance()
            eyeButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_eye) as Drawable
            isPasswordVisible = true
        }
        setButtonDrawable(endOfTheText = eyeButtonImage)
    }
}