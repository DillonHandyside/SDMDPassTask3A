package com.example.passtask3a

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Debug
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MedallistAdapter(private val data: List<Medallist>, private val preferences: SharedPreferences) : RecyclerView.Adapter<MedallistAdapter.ViewHolder>() {
    private val sortedMedallists: List<Medallist> = data.sortedByDescending { it.gold }

    class ViewHolder(view: View, private val data: List<Medallist>, private val preferences: SharedPreferences) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val countryTextView: TextView = view.findViewById(R.id.country_text_view)
        val iocTextView: TextView = view.findViewById(R.id.ioc_text_view)
        val totalMedalTextView: TextView = view.findViewById(R.id.total_medals)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            val medallist = data[position]

            // save the last clicked medallist to preferences
            with (preferences.edit()) {
                putString("country", medallist.country)
                putString("ioc", medallist.ioc)
                putInt("competed", medallist.competed)
                putInt("gold", medallist.gold)
                putInt("silver", medallist.silver)
                putInt("bronze", medallist.bronze)
                commit()
            }

            // display Toast which lists number of each medal
            val output = "Gold: " + medallist.gold.toString() +
                        " Silver: " + medallist.silver.toString() +
                        " Bronze: " + medallist.bronze.toString()
            Toast.makeText(v!!.context, output, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medallist_item, parent, false)
        return ViewHolder(view, data, preferences)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medallist = data[position]
        val totalMedals = medallist.gold + medallist.silver + medallist.bronze
        holder.countryTextView.text = medallist.country
        holder.iocTextView.text = medallist.ioc
        holder.totalMedalTextView.text = totalMedals.toString()

        // set text color to red for top 10 medallists
        if (sortedMedallists.indexOf(medallist) < 10) {
            holder.totalMedalTextView.setTextColor(Color.BLUE)
        } else {
            // since RecycleView acts as an object pool, we need to set colors back manually!!!
            holder.totalMedalTextView.setTextColor(Color.BLACK)
        }
    }

    override fun getItemCount() = data.size
}