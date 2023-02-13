package com.example.passtask3a

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val medallist = intent.getParcelableExtra<Medallist>("medallist")!!
        val output = "Last clicked on: " + medallist.country + " (" + medallist.ioc + ")"

        val settingsTextView = findViewById<TextView>(R.id.settings_text_view)
        settingsTextView.text = output
    }
}