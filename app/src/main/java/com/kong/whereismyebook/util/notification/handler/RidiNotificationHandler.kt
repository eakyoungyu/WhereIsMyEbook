package com.kong.whereismyebook.util.notification.handler

import com.kong.whereismyebook.data.repository.LibraryRepository
import javax.inject.Inject

class RidiNotificationHandler @Inject constructor(
    libraryRepository: LibraryRepository
): NotificationHandler(libraryRepository) {
    override val packageName: String
        get() = "com.initialcoms.ridi"
    override val appName: String
        get() = "RIDI"

    override fun parseBookName(title: String?, text: String?): String? {
        if (title?.contains("다운로드 완료") == true) {
            return parseBookNameFromAngleBrackets(title)
        }
        return null
    }
}