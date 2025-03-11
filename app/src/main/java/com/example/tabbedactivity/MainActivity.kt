package com.example.tabbedactivity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.tabbedactivity.WeatherFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)

        button1.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, WeatherFragment.newInstance("Angarsk"))
                .commit()
        }

        button2.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, WeatherFragment.newInstance("Irkutsk"))
                .commit()
        }

        // По умолчанию загружаем Иркутск
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, WeatherFragment.newInstance("Irkutsk"))
            .commit()
    }
}
