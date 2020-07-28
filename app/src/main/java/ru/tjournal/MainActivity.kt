package ru.tjournal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Retrofit
import ru.tjournal.BuildConfig.TJ_URL
import ru.tjournal.data.Api
import ru.tjournal.data.Source


class MainActivity : AppCompatActivity() {

    lateinit var service: Source

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setNavigation()
        createRetrofit()
    }

    private fun setNavigation() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.navigation_feed, R.id.navigation_profile))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun createRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(TJ_URL)
            .build()

        service = Source(retrofit.create(Api::class.java))
    }
}