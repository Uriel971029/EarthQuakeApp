package com.example.earthquakeapp.ui.earthquake

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.earthquakeapp.R
import com.example.earthquakeapp.data.local.Earthquake
import com.example.earthquakeapp.databinding.EarthquakeAdapterBinding
import java.text.SimpleDateFormat
import java.util.*

class EarthquakeAdapter : ListAdapter<Earthquake, EarthquakeAdapter.ViewHolder>(DiffObject) {

    private lateinit var context : Context

    object DiffObject : DiffUtil.ItemCallback<Earthquake>(){
        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = EarthquakeAdapterBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val earthquake = getItem(position)
        holder.place.text = String.format(context.resources.getString(R.string.place_text_adapter), earthquake.place)
        holder.magnitude.text = String.format(context.resources.getString(R.string.mag_text_adapter), earthquake.magnitude.toString())


        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        val convertedDate = Date(earthquake.time)
        val date = formatter.format(convertedDate)

        holder.time.text = String.format(context.resources.getString(R.string.date_text_adapter), date)
        holder.latitude.text = String.format(context.resources.getString(R.string.latitude_text_adapter), earthquake.coordinates[0].toString())
        holder.longitude.text = String.format(context.resources.getString(R.string.longitude_text_adapter), earthquake.coordinates[1].toString())
    }


    class ViewHolder(binding: EarthquakeAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        val place = binding.textView
        val magnitude = binding.textView2
        val latitude = binding.textView3
        val longitude = binding.textView4
        val time = binding.textView5
    }
}