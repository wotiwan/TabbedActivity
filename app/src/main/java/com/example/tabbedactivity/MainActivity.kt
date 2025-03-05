package com.example.tabbedactivity

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, Fragment1())
            .commit()

        val button1 = findViewById<Button>(R.id.button1);
        val button2 = findViewById<Button>(R.id.button2);

        button1.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, Fragment1())
                .commit()
        }

        button2.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, Fragment2())
                .commit()
        }

    }
}