package com.example.dndjourney

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
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
        val listRace = ArrayList<Race>()
        for (i in dataName.indices) {
            val hero = Race(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listRace.add(hero)
        }
        return listRace
    }

    private fun showRecyclerList() {
        rvRaces.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListRaceAdapter(list)
        rvRaces.adapter = listHeroAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}