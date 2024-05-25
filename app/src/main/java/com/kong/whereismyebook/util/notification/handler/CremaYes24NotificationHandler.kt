package com.kong.whereismyebook.util.notification.handler

import android.app.Notification
import com.kong.whereismyebook.data.repository.LibraryRepository
import javax.inject.Inject

class CremaYes24NotificationHandler @Inject constructor(
    libraryRepository: LibraryRepository
): NotificationHandler(libraryRepository) {
    override val packageName: String
        get() = "com.yes24.ebook.fourth"
    override val appName: String
        get() = "예스24 eBook"

    override fun parseBookName(notification: Notification?): String? {
        notification?.extras?.run {
            val text = getString(Notification.EXTRA_TEXT)

            if (text?.contains("다운로드 성공") == true) {
                return parseBookNameFromAngleBrackets(text)
            }
        }

        return null
    }
}