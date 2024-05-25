package com.kong.whereismyebook.util.notification.handler

import android.app.Notification
import android.service.notification.StatusBarNotification
import android.util.Log
import com.kong.whereismyebook.data.repository.LibraryRepository
import com.kong.whereismyebook.model.Book
import javax.inject.Inject

class AladinEBookNotificationHandler @Inject constructor(
    libraryRepository: LibraryRepository
): NotificationHandler(libraryRepository) {
    override val packageName: String
        get() = "kr.co.aladin.ebook"
    override val appName: String
        get() = "알라딘 ebook"

    override fun parseBookName(notification: Notification?): String? {
        notification?.extras?.run {
            val title = getString(Notification.EXTRA_TITLE)
            val text = getString(Notification.EXTRA_TEXT)

            if (title?.contains("다운로드 완료") == true) {
                text?.let {
                    return parseBookNameFromAngleBrackets(it)
                }
            }
        }

        return null
    }
}