package com.example.githubuser.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.ItemsItem
import com.example.githubuser.R
import com.example.githubuser.SettingPreferences
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.model.MainViewModel
import com.example.githubuser.model.ThemeViewModelFactory
import com.example.githubuser.model.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
    }

    lateinit var themeBtn: FloatingActionButton
    lateinit var favBtn: FloatingActionButton
    lateinit var menuBtn: FloatingActionButton
    var btnVisible = false
    var isDark = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref))[MainViewModel::class.java]

        mainViewModel.listUser.observe(this, {
            setUserData(it)
        })
        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        val layoutManager = LinearLayoutManager(this)
        binding.rvAkun.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvAkun.addItemDecoration(itemDecoration)

        menuBtn = findViewById(R.id.menuBtn)
        themeBtn = findViewById(R.id.themeBtn)
        favBtn = findViewById(R.id.favBtn)
        btnVisible = false

        menuBtn.setOnClickListener {
            if (!btnVisible) {
                themeBtn.show()
                favBtn.show()

                themeBtn.visibility = View.VISIBLE
                favBtn.visibility = View.VISIBLE
                menuBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_clear))
                btnVisible = true
            } else {
                themeBtn.hide()
                favBtn.hide()

                themeBtn.visibility = View.GONE
                favBtn.visibility = View.GONE
                menuBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_menu))
                btnVisible = false
            }
        }

        mainViewModel.getThemeSettings().observe(this) {
            if (it) {
                isDark = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                isDark = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        }

        favBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, FavActivity::class.java)
            startActivity(intent)
        }

        themeBtn.setOnClickListener {
            checkTheme()
        }


    }

    private fun checkTheme() {
        if (isDark) {
            mainViewModel.saveThemeSetting(false)
            Toast.makeText(this, "Light Mode", Toast.LENGTH_SHORT).show()
        } else {
            mainViewModel.saveThemeSetting(true)
            Toast.makeText(this, "Dark Mode", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUserData(listDataUser: List<ItemsItem>) {
        val adapter = UserAdapter(listDataUser)
        binding.rvAkun.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_option, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

}

