package ru.tjournal

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.tjournal.BuildConfig.TJ_URL
import ru.tjournal.data.Api
import ru.tjournal.data.Source
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager


class MainActivity : AppCompatActivity() {

    companion object {
        private const val APP_NAME = "tjournal"
        private const val APP_VERSION = "1.1"
        private const val ANDROID = "Android"
    }

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

    //dtf-app/2.2.0 (Pixel 2; Android/9; ru; )
    private fun createRetrofit() {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader(
                    "User-agent",
                    "$APP_NAME-app/$APP_VERSION (${Build.DEVICE}; $ANDROID/${Build.VERSION.RELEASE}; ${getLocale()}; 1980x1794"
                ).build()
            chain.proceed(request)
        }

//        val client = httpClient.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(TJ_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getUnsafeOkHttpClient().build())
            .build()

        service = Source(retrofit.create(Api::class.java))
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder =
        try {
            val trustAllCerts = arrayOf(object : X509TrustManager {
                override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
                override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
                override fun getAcceptedIssuers(): Array<out X509Certificate>? = arrayOf()
            })

            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            val builder = OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true }
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    private fun getLocale(): String =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.resources.configuration.locales.get(0).language
        } else {
            //noinspection deprecation
            resources.configuration.locale.language
        }
}