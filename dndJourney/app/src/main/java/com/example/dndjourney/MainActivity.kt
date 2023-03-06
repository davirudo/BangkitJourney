package com.example.dndjourney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvRaces: RecyclerView
    private val list = ArrayList<Race>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvRaces = findViewById(R.id.rv_races)
        rvRaces.setHasFixedSize(true)

        list.addAll(getListRaces())
        showRecyclerList()
    }

    private fun getListRaces(): ArrayList<Race> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listHero = ArrayList<Race>()
        for (i in dataName.indices) {
            val hero = Race(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listHero.add(hero)
        }
        return listHero
    }

    private fun showRecyclerList() {
        rvRaces.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListRaceAdapter(list)
        rvRaces.adapter = listHeroAdapter
    }
}