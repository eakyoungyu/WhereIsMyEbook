package com.kong.whereismyebook.util.notification.handler

import android.app.Notification
import com.kong.whereismyebook.data.repository.LibraryRepository
import javax.inject.Inject

class RidiNotificationHandler @Inject constructor(
    libraryRepository: LibraryRepository
): NotificationHandler(libraryRepository) {
    override val packageName: String
        get() = "com.initialcoms.ridi"
    override val appName: String
        get() = "RIDI"

    override fun parseBookName(notification: Notification?): String? {
        notification?.extras?.run {
            val title = getString(Notification.EXTRA_TITLE)

            if (title?.contains("다운로드 완료") == true) {
                return parseBookNameFromAngleBrackets(title)
            }
        }

        return null
    }
}