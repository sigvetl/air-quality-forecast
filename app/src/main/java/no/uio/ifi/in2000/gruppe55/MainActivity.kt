package no.uio.ifi.in2000.gruppe55

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.Toolbar
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AirqualityforecastJobService : JobService() {
    private var fetchJob: Job? = null

    override fun onStartJob(params: JobParameters?): Boolean {
        fetchJob = launch {
            jobFinished(params, false)
        }
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        fetchJob?.cancel()
        return true
    }
}

class MainActivity : AppCompatActivity() {

    private val jobScheduler: JobScheduler by lazy {
        applicationContext.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(this, R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.listFragment, R.id.mapFragment))

        //link navigation controller and application bar with toolbar
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController, appBarConfiguration)
        //link navigation controller with the bottom navigation menu
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)

        val componentName = ComponentName(this, AirqualityforecastJobService ::class.java)
        val jobInfo = JobInfo.Builder(0, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .build()

        jobScheduler.schedule(jobInfo)
    }
}
