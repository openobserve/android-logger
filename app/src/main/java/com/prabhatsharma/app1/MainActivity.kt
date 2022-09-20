package com.prabhatsharma.app1

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.datadog.android.Datadog
import com.datadog.android.DatadogSite
import com.datadog.android.core.configuration.Configuration
import com.datadog.android.core.configuration.Credentials
import com.datadog.android.log.Logger
import com.datadog.android.privacy.TrackingConsent
import com.prabhatsharma.app1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // datadog start

        StartLoggingWithDatadogSDK()

        // datadog end

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    fun StartLoggingWithDatadogSDK(): Boolean {

        val configuration = Configuration.Builder(
            logsEnabled = true,
            tracesEnabled = false,
            crashReportsEnabled = false,
            rumEnabled = false
        )//.useCustomLogsEndpoint("http://192.168.86.165:8080")
            .useCustomLogsEndpoint("http://192.168.1.41/test_projects/endpoint/index.php")
            .build()

        val credentials = Credentials("pubf92340d3d2ddbd287fb0792181c6556f", "test", "first_app", "com.prabhatsharma.android")

        Datadog.initialize(this, credentials, configuration, TrackingConsent.GRANTED)

        val logger = Logger.Builder()
            .setNetworkInfoEnabled(true)
            .setLogcatLogsEnabled(true)
            .setDatadogLogsEnabled(true)
            .setBundleWithTraceEnabled(true)
            .setLoggerName("android1")
            .build()

        logger.d("cool. A debug message.")
//        logger.i("Some relevant information ?")
//        logger.w("An important warning…")
//        logger.e("An error was met!")
//        logger.wtf("What a Terrible Failure!")

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}