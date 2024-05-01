package com.kong.whereismyebook.util.notification.handler

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

    override fun parseBookName(title: String?, text: String?): String? {
        if (title?.contains("다운로드 완료") == true) {
            text?.let {
                val start = it.indexOf('<') + 1
                val end = it.lastIndexOf('>')

                if (start != 0 && end != -1 && start < end) {
                    val bookName = it.substring(start, end)
                    Log.d(TAG, "Save Book $bookName")
                    return bookName
                }
            }
        }
        return null
    }
}