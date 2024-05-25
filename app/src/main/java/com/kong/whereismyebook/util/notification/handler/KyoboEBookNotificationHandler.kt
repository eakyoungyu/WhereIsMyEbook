package com.kong.whereismyebook.util.notification.handler

import android.app.Notification
import com.kong.whereismyebook.data.repository.LibraryRepository
import javax.inject.Inject

class KyoboEBookNotificationHandler @Inject constructor(
    libraryRepository: LibraryRepository
) : NotificationHandler(libraryRepository) {
    override val packageName: String
        get() = "com.kyobo.ebook.common.b2c"
    override val appName: String
        get() = "교보eBook"

    override fun parseBookName(notification: Notification?): String? {
        notification?.extras?.run {
            val title = getString(Notification.EXTRA_TITLE)
            val text = getString(Notification.EXTRA_TEXT)

            if (text?.contains("다운로드가 완료되었습니다") == true) {
                return title
            }
        }

        return null
    }
}