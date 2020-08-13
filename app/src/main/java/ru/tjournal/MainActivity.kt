package ru.tjournal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.tjournal.BuildConfig.TJ_URL
import ru.tjournal.data.Api
import ru.tjournal.data.SavedData
import ru.tjournal.data.Source
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager


class MainActivity : AppCompatActivity() {

    lateinit var service: Source
    lateinit var savedData: SavedData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setNavigation()
        createRetrofit()
        createSavedData()
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
            .addConverterFactory(GsonConverterFactory.create())
            .client(getUnsafeOkHttpClient().build())
            .build()

        service = Source(retrofit.create(Api::class.java))
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        return try {
            val trustAllCerts = arrayOf(object : X509TrustManager {
                override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
                override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
                override fun getAcceptedIssuers(): Array<out X509Certificate>? = arrayOf()
            })

            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val builder = OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                .addInterceptor(logging)
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun createSavedData() {
        savedData = SavedData(PreferenceManager.getDefaultSharedPreferences(this))
    }
}