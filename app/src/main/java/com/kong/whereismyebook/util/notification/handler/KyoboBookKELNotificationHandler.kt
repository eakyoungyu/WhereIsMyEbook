package com.kong.whereismyebook.util.notification.handler

import android.app.Notification
import com.kong.whereismyebook.data.repository.LibraryRepository
import javax.inject.Inject

class KyoboBookKELNotificationHandler @Inject constructor(
    libraryRepository: LibraryRepository
) : NotificationHandler(libraryRepository) {
    override val packageName: String
        get() = "kr.co.kyobobook.KEL"
    override val appName: String
        get() = "교보도서관"

    override fun parseBookName(notification: Notification?): String? {
        notification?.extras?.run {
            val title = getString(Notification.EXTRA_TITLE)
            val bigText = getString(Notification.EXTRA_BIG_TEXT)

            if (bigText?.contains("다운로드를 완료 하였습니다") == true) {
                return title
            }
        }

        return null
    }
}