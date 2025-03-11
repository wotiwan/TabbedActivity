package com.example.tabbedactivity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("WeatherPrefs", Context.MODE_PRIVATE)
        applyTheme()

        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, getSavedFragment())
            .commit()

        val settingsButton = findViewById<Button>(R.id.btn_settings)
        settingsButton.setOnClickListener {
            showSettingsDialog()
        }
    }

    private fun showSettingsDialog() {
        val cities = arrayOf("Ангарск", "Иркутск")
        val themes = arrayOf("Светлая", "Темная")
        val languages = arrayOf("Русский", "English")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Настройки")

        builder.setSingleChoiceItems(cities, getSavedCityIndex()) { _, which ->
            savePreference("city", cities[which])
        }

        builder.setSingleChoiceItems(languages, getSavedLanguageIndex()) { _, which ->
            savePreference("language", languages[which])
        }

        builder.setSingleChoiceItems(themes, getSavedThemeIndex()) { _, which ->
            savePreference("theme", themes[which])
            recreate()
        }

        builder.setPositiveButton("OK") { _, _ ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, getSavedFragment())
                .commit()
        }

        builder.setNegativeButton("Отмена", null)
        builder.show()
    }

    private fun savePreference(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    private fun getSavedFragment(): Fragment {
        val city = sharedPreferences.getString("city", "Ангарск")
        return if (city == "Ангарск") Fragment1() else Fragment2()
    }

    private fun getSavedCityIndex(): Int {
        return if (sharedPreferences.getString("city", "Ангарск") == "Ангарск") 0 else 1
    }

    private fun getSavedLanguageIndex(): Int {
        return if (sharedPreferences.getString("language", "Русский") == "Русский") 0 else 1
    }

    private fun getSavedThemeIndex(): Int {
        return if (sharedPreferences.getString("theme", "Светлая") == "Светлая") 0 else 1
    }

    private fun applyTheme() {
        when (sharedPreferences.getString("theme", "Светлая")) {
            "Темная" -> setTheme(R.style.Theme_Dark)
            else -> setTheme(R.style.Theme_Light)
        }
    }
}
