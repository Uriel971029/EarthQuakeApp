package com.example.earthquakeapp.ui.earthquake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.earthquakeapp.R
import com.example.earthquakeapp.databinding.ActivityMainBinding
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton

class EarthQuakeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val earthQuakeViewModel = ViewModelProvider(this)[EarthQuakeViewModel::class.java]
        val earthquakeAdapter = EarthquakeAdapter()
        var skeleton :  Skeleton

        binding.rvEarthquakes.apply {
            adapter = earthquakeAdapter
            layoutManager = LinearLayoutManager(this@EarthQuakeActivity)
            skeleton = applySkeleton(R.layout.earthquake_adapter)
        }

        skeleton.showSkeleton()

        earthQuakeViewModel.earthquakesLiveData.observe(this, { earthquakes ->
            earthquakeAdapter.submitList(earthquakes)
            skeleton.showOriginal()
        })

    }
}