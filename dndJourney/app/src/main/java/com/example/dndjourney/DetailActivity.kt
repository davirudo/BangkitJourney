package com.example.dndjourney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class DetailActivity : AppCompatActivity() {

    companion object {
        const val KEY_RACE = "key_race"
    }

    private lateinit var tvDetailName: TextView
    private lateinit var tvDetailDescription: TextView
    private lateinit var ivDetailPhoto: ImageView
    private lateinit var actionShare: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val action = supportActionBar
        action?.hide()

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
            Toast.makeText(this,"Shared", Toast.LENGTH_LONG).show()
        }
    }


}