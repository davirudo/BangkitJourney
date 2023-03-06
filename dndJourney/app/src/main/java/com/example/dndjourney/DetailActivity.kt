package com.example.dndjourney

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

class DetailActivity : AppCompatActivity() {

    companion object {
        const val KEY_RACE = "key_race"
    }

    private lateinit var tvDetailName: TextView
    private lateinit var tvDetailDescription: TextView
    private lateinit var ivDetailPhoto: ImageView
    private lateinit var actionShare: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val action = supportActionBar
        action?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        val dataRace = intent.getParcelableExtra<Race>(KEY_RACE)

        tvDetailName = findViewById(R.id.tv_detail_name)
        tvDetailDescription = findViewById(R.id.tv_detail_description)
        ivDetailPhoto = findViewById(R.id.iv_detail_photo)
        actionShare = findViewById(R.id.action_share)


        dataRace?.let {
            tvDetailName.text = dataRace.name
            tvDetailDescription.text = dataRace.description
            ivDetailPhoto.setImageResource(dataRace.photo)
        }

        actionShare.setOnClickListener {
           val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            val body = "You got the link!"
            val sub = "https://www.dndbeyond.com/races"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, body)
            shareIntent.putExtra(Intent.EXTRA_TEXT, sub)
            startActivity(Intent.createChooser(shareIntent, "Here's the link"))
        }
    }


}