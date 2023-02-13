package com.example.passtask3a

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var medallistPreferences: SharedPreferences
    private lateinit var medallist: Medallist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // grab the RecyclerView and set a layout manager
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(baseContext, DividerItemDecoration.VERTICAL))

        // initialise SharedPreferences for data persistence
        medallistPreferences = getPreferences(Context.MODE_PRIVATE)

        // parse the medallist csv data
        val data = parseCSV("medallists.csv")

        // feed the parsed medallist data into an adapter such that we can bind it to the views
        val adapter = MedallistAdapter(data, medallistPreferences)
        recyclerView.adapter = adapter
    }

    private fun parseCSV(filename: String): List<Medallist> {
        val output = mutableListOf<Medallist>()

        val inputStream = assets.open(filename)
        inputStream.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val items = line.split(",") // split csv

                // initialise all medallist values
                val country = items[0]
                val ioc = items[1]
                val competed = items[2].toInt()
                val gold = items[3].toInt()
                val silver = items[4].toInt()
                val bronze = items[5].toInt()

                // create new medallist
                val medallist = Medallist(country, ioc, competed, gold, silver, bronze)
                output.add(medallist)
            }
        }
        return output
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_button -> {
                val settingsIntent = Intent(this, SettingsActivity::class.java)

                // fetch the most recently saved medallist and put it into the intent using Parcelable
                medallist = Medallist(
                    medallistPreferences.getString("country", "")!!,
                    medallistPreferences.getString("ioc", "")!!,
                    medallistPreferences.getInt("competed", 0),
                    medallistPreferences.getInt("gold", 0),
                    medallistPreferences.getInt("silver", 0),
                    medallistPreferences.getInt("bronze", 0)
                )
                settingsIntent.putExtra("medallist", medallist)
                startActivity(settingsIntent)
                return true
            }
        }
        return false
    }
}