package no.uio.ifi.in2000.gruppe55

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelStore
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.Toolbar
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.gruppe55.viewmodel.DailyForecastModel

// TODO (julianho): Ugly hack to pass viewmodel store into the continuous job service. Consider whether there is a way
// to get access to some underlying store from services directly.
private lateinit var globalViewModelStore: ViewModelStore

class AirqualityforecastJobService : JobService() {

    // List of viewmodels relevant to this job.

    private lateinit var viewModelProvider: ViewModelProvider
    private lateinit var dailyForecastModel: DailyForecastModel

    private var fetchJob: Job? = null

    override fun onCreate() {
        viewModelProvider = ViewModelProvider(globalViewModelStore, ViewModelProvider.NewInstanceFactory())
        dailyForecastModel = viewModelProvider.get(DailyForecastModel::class.java)
        super.onCreate()
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        fetchJob = launch {
            dailyForecastModel.loadStations()
            jobFinished(params, false)
        }
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        fetchJob?.cancel()
        return false
    }
}

class MainActivity : AppCompatActivity() {

    private val jobScheduler: JobScheduler by lazy {
        applicationContext.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Make sure the global view model store for job services is always up to date.
        //REMOVE!!!!
        val intent = Intent(this, InfoActivity::class.java)
        // To pass any data to next activity
        //intent.putExtra("keyIdentifier", value)
        // start your next activity
        startActivity(intent)


        globalViewModelStore = viewModelStore

        val navController = findNavController(this, R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.listFragment, R.id.mapFragment))

        //link navigation controller and application bar with toolbar
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController, appBarConfiguration)
        //link navigation controller with the bottom navigation menu
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)

        // Run background service for automatically fetching measurements from each station.

        val componentName = ComponentName(this, AirqualityforecastJobService::class.java)
        val jobInfo = JobInfo.Builder(0, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPeriodic(15 * 1000 * 60) // TODO: Increase time between measurements.
            .build()

        jobScheduler.schedule(jobInfo)
    }
}
