package com.kong.whereismyebook

import android.app.Application
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationManagerCompat
import com.kong.whereismyebook.di.AppEntryPoint
import com.kong.whereismyebook.ui.theme.WhereIsMyEbookTheme
import com.kong.whereismyebook.util.notification.handler.NotificationHandlerRegistry
import com.kong.whereismyebook.util.notification.NotificationListener
import com.kong.whereismyebook.view.MainView
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionChecker()
        setContent {
            WhereIsMyEbookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView()
                }
            }
        }
    }

    private fun isNotificationPermissionGranted(): Boolean {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.isNotificationListenerAccessGranted(ComponentName(application, NotificationListener::class.java))
        } else {
            NotificationManagerCompat.getEnabledListenerPackages(applicationContext).contains(applicationContext.packageName)
        }
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun permissionChecker() {
        if (!isNotificationPermissionGranted()) {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }
    }

    @HiltAndroidApp
    class WhereIsMyEbook: Application() {
        override fun onCreate() {
            super.onCreate()

            initializeNotificationHandlerRegistry()
        }
        private fun initializeNotificationHandlerRegistry() {
            val appContext = applicationContext
            val entryPoint = EntryPointAccessors.fromApplication(appContext, AppEntryPoint::class.java)
            val handlerFactory = entryPoint.notificationHandlerFactory()
            NotificationHandlerRegistry.inject(handlerFactory)
        }
    }
}
