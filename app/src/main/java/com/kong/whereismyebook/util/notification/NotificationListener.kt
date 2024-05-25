package com.kong.whereismyebook.util.notification

import android.app.Notification
import android.content.pm.PackageManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.kong.whereismyebook.util.notification.handler.NotificationHandlerRegistry

class NotificationListener: NotificationListenerService() {
    private val TAG = NotificationListener::class.simpleName
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        sbn?.let {notification ->
            val packageName = notification.packageName
            packageName?.let {
                Log.d(TAG, "onNotificationPosted $packageName")
                NotificationHandlerRegistry.getHandler(packageName)?.handle(notification.notification)
            }
        }

        // 패키지 이름 추출
        val packageName = sbn?.packageName
        // 알림 데이터 추출
        val notification = sbn?.notification
        // 알림의 제목과 내용을 extras에서 추출
        val title = notification?.extras?.getString(Notification.EXTRA_TITLE)
        val text = notification?.extras?.getString(Notification.EXTRA_TEXT)
        // 로그 출력
        Log.i("NotificationListener", "Notification from package: $packageName ${getAppName(packageName)}")
        Log.i("NotificationListener", "Title: $title")
        Log.i("NotificationListener", "Content: $text")

//        printAllExtras(notification)
    }

    private fun printAllExtras(notification: Notification?) {
        val extras = notification?.extras
        extras?.keySet()?.forEach { key ->
            val value = extras.get(key)
            Log.i("NotificationListener", "Extra: $key = $value")
        }
    }

    private fun getAppName(packageName: String?): String? {
        return try {
            packageName?.let {
                val pm = applicationContext.packageManager
                val appInfo = pm.getApplicationInfo(packageName, 0)
                val appName = pm.getApplicationLabel(appInfo)
                appName.toString()
            }
        } catch (e: PackageManager.NameNotFoundException) {
            "Unknown App"
        }
    }
}