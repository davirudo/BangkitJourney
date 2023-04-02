package com.example.githubuser.ui

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.ItemsItem
import com.example.githubuser.R
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.model.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    lateinit var themeBtn: FloatingActionButton
    lateinit var favBtn: FloatingActionButton
    lateinit var menuBtn: FloatingActionButton
    var btnVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

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

        themeBtn.setOnClickListener {
            Toast.makeText(this@MainActivity, "Home clicked..", Toast.LENGTH_LONG).show()
        }

        favBtn.setOnClickListener {
            Toast.makeText(this@MainActivity, "Settings clicked..", Toast.LENGTH_LONG).show()
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

