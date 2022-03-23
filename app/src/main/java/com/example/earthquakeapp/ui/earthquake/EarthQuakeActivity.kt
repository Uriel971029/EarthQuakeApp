package com.example.earthquakeapp.ui.earthquake

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.earthquakeapp.R
import com.example.earthquakeapp.data.local.Earthquake
import com.example.earthquakeapp.databinding.ActivityMainBinding
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton

class EarthQuakeActivity : AppCompatActivity() {

    private var isSortedList = false
    private lateinit var earthquakesList : List<Earthquake>
    private val earthquakeAdapter = EarthquakeAdapter()

    private val FILTER_PREFERENCE = "filter_preference"
    private val EARTHQUAKES_PREFERENCES = "earthquakes_preferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbEarthquakes)
        val earthQuakeViewModel = ViewModelProvider(
            this,
            EarthQuakeViewModel.EarthQuakeViewModelFactory(application))[EarthQuakeViewModel::class.java]

        var skeleton :  Skeleton

        binding.rvEarthquakes.apply {
            adapter = earthquakeAdapter
            layoutManager = LinearLayoutManager(this@EarthQuakeActivity)
            skeleton = applySkeleton(R.layout.earthquake_adapter)
        }

        skeleton.showSkeleton()

        earthQuakeViewModel.earthquakesLiveData.observe(this, { earthquakes ->
            earthquakesList = earthquakes
            sortEarthquakes()
            earthquakeAdapter.submitList(earthquakesList)
            skeleton.showOriginal()
        })
    }

    private fun sortEarthquakes() {
        earthquakesList = if(isSortedList){
            earthquakesList.sortedByDescending {
                it.magnitude
            }
        } else {
            earthquakesList.sortedBy {
                it.magnitude
            }
        }
    }

    private fun setFilters(menuItem : MenuItem) {
        isSortedList = getFromPreferences(FILTER_PREFERENCE)
        if(isSortedList){
            menuItem.title = getString(R.string.filter_menor_magnitude)
        } else {
            menuItem.title = getString(R.string.filter_major_magnitude)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        menu?.let {
            setFilters(menu.getItem(0))
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.filter_earthquakes->{
            isSortedList = !isSortedList
            saveInPreferences(FILTER_PREFERENCE, isSortedList)
            earthquakesList = if(isSortedList){
                item.title = getString(R.string.filter_menor_magnitude)
                earthquakesList.sortedByDescending {
                    it.magnitude
                }
            } else {
                item.title = getString(R.string.filter_major_magnitude)
                earthquakesList.sortedBy {
                    it.magnitude
                }
            }
            earthquakeAdapter.submitList(earthquakesList)
            true
        }
        else -> {super.onOptionsItemSelected(item)}
    }

    private fun saveInPreferences(preference : String,  value: Boolean) {
        val prefs = getSharedPreferences(EARTHQUAKES_PREFERENCES, MODE_PRIVATE)
        prefs.edit().putBoolean(preference, value).apply()
    }

    private fun getFromPreferences(preference : String) : Boolean {
        val prefs = getSharedPreferences(EARTHQUAKES_PREFERENCES, MODE_PRIVATE)
        return prefs.getBoolean(preference, false)
    }
}