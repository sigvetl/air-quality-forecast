package no.uio.ifi.in2000.gruppe55

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.arch.lifecycle.ViewModelProvider
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.NotificationCompat
import android.support.v7.widget.Toolbar
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.gruppe55.destinations.InfoDialogNavigator
import no.uio.ifi.in2000.gruppe55.viewmodel.DailyForecastModel
import no.uio.ifi.in2000.gruppe55.viewmodel.FavoriteStationModel

// TODO (julianho): Ugly hack to pass viewmodel provider into the continuous job service. Consider whether there is a
// way to get access to some underlying provider from services directly.
private lateinit var globalViewModelProvider: ViewModelProvider

class AirqualityforecastJobService : JobService() {

    // List of viewmodels relevant to this job.

    private val dailyForecastModel: DailyForecastModel by lazy {
        globalViewModelProvider.get(DailyForecastModel::class.java)
    }

    private var fetchJob: Job? = null

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

class BadAirqualityAlertJobService : JobService() {

    // List of services relevant to this job.

    private val notificationManager: NotificationManager by lazy {
        applicationContext.getSystemService(NotificationManager::class.java)
    }

    // List of viewmodels relevant to this job.

    private lateinit var viewModelProvider: ViewModelProvider
    private lateinit var dailyForecastModel: DailyForecastModel
    private lateinit var favoriteStationModel: FavoriteStationModel

    // List of metadata relevant to sending notifications over channels

    private val channelId: String by lazy {
        val id = "low_air_quality"
        val name = "Low air quality"
        val description = "Notifications about low air quality."

        // Older versions of Android has no need for channels; consequently, only *actually* assign the channel if a
        // newer Android version is used.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
            channel.description = description

            notificationManager.createNotificationChannel(channel)
        }

        id
    }

    private var updateJob: Job? = null

    override fun onCreate() {
        super.onCreate()

        dailyForecastModel = globalViewModelProvider.get(DailyForecastModel::class.java)
        favoriteStationModel = globalViewModelProvider.get(FavoriteStationModel::class.java)
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        updateJob = launch {
            delay(1000) // Temporary solution to get notifications right away.

            val stationMap = dailyForecastModel.stations.value ?: hashMapOf()
            val favoriteSet = favoriteStationModel.favorites.value ?: hashSetOf()

            // Only notify about stations that are in marked areas.

            val constrainedMap = stationMap.filterKeys { stationModel ->
                favoriteSet.contains(stationModel.kommune?.name)
            }

            for ((stationModel, measurementModel) in constrainedMap) {
                if (measurementModel != null && exceedsLimit(measurementModel)) {
                    notify(stationModel, measurementModel)
                }
            }

            jobFinished(params, false)
        }

        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        updateJob?.cancel()
        return false
    }

    private fun exceedsLimit(measurementModel: AirQualityTimeDataModel): Boolean {
        val threshold = 2.0

        return (measurementModel.variables?.aqi?.value ?: 0.0) >= threshold
    }

    private fun notify(stationModel: StationModel, measurementModel: AirQualityTimeDataModel) {
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.aqi_h)
            .setContentTitle("${stationModel.name} har dårlig luftkvalitet")
            .setContentText("${stationModel.name} har luftkvalitetsverdi på ${measurementModel.variables?.aqi?.value}")
            .build()

        // Notification manager needs to be told of the notification and what ID to give it. By reusing the ID, it
        // is possible to later change the contents of the aforementioned ID.

        notificationManager.notify(stationModel.hashCode(), notification)
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

        globalViewModelProvider = ViewModelProvider(
            viewModelStore,
            ViewModelProvider.AndroidViewModelFactory(application)
        )

        val navController = findNavController(this, R.id.nav_host_fragment)

        //Add the custom destination type to the navigation component
        val destination = InfoDialogNavigator(nav_host_fragment.childFragmentManager)
        navController.navigatorProvider.addNavigator(destination)
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)
        navController.graph = graph

        //link navigation controller and application bar with toolbar
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.listFragment, R.id.mapFragment))
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController, appBarConfiguration)
        //link navigation controller with the bottom navigation menu
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)

        // For some reason, observing a LiveData that stems from SQL seems to be necessary to extract its value.

        globalViewModelProvider[FavoriteStationModel::class.java].favorites.observe({ lifecycle }) { }

        // Run background service for showing notifications on relevant stations.

        val alertName = ComponentName(this, BadAirqualityAlertJobService::class.java)
        val alertInfo = JobInfo.Builder(0, alertName)
            .setPeriodic(15 * 1000 * 60)
            .build()

        jobScheduler.schedule(alertInfo)

        // Run background service for automatically fetching measurements from each station.

        val updateName = ComponentName(this, AirqualityforecastJobService::class.java)
        val updateInfo = JobInfo.Builder(1, updateName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPeriodic(15 * 1000 * 60) // TODO: Increase time between measurements.
            .build()

        jobScheduler.schedule(updateInfo)
    }
}
