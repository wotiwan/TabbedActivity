package com.example.tabbedactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class WeatherFragment : Fragment() {
    private lateinit var cityName: TextView
    private lateinit var temperature: TextView
    private lateinit var weatherDescription: TextView
    private val apiKey = "d6843ab8ee963f5d372296dfff62aed7"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_1_layout, container, false)

        cityName = view.findViewById(R.id.cityName)
        temperature = view.findViewById(R.id.temperature)
        weatherDescription = view.findViewById(R.id.weatherDescription)

        val city = arguments?.getString("city") ?: "Angarsk"
        cityName.text = city

        loadWeather(city)

        return view
    }

    private fun loadWeather(city: String) {
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric&lang=ru"

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    temperature.text = "Ошибка загрузки"
                    weatherDescription.text = e.message
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { json ->
                    val jsonObject = JSONObject(json)
                    val temp = jsonObject.getJSONObject("main").getDouble("temp")
                    val description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description")

                    activity?.runOnUiThread {
                        temperature.text = "Температура: $temp°C"
                        weatherDescription.text = description
                    }
                }
            }
        })
    }

    companion object {
        fun newInstance(city: String): WeatherFragment {
            val fragment = WeatherFragment()
            val args = Bundle()
            args.putString("city", city)
            fragment.arguments = args
            return fragment
        }
    }
}
