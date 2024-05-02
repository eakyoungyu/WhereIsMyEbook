package com.kong.whereismyebook.util.notification.handler

import com.kong.whereismyebook.data.repository.LibraryRepository
import javax.inject.Inject

/*
Notification from package: com.yes24.ebook.fourth Unknown App
2024-05-01 18:17:20.853 25653-25653 NotificationListener    com.kong.whereismyebook              I  Title: null
2024-05-01 18:17:20.853 25653-25653 NotificationListener    com.kong.whereismyebook              I  Content: <꽃을 그리는 시간 (체험판)> 다운로드 성공

*/
class CremaYes24NotificationHandler @Inject constructor(
    libraryRepository: LibraryRepository
): NotificationHandler(libraryRepository) {
    override val packageName: String
        get() = "com.yes24.ebook.fourth"
    override val appName: String
        get() = "예스24 eBook"

    override fun parseBookName(title: String?, text: String?): String? {
        if (text?.contains("다운로드 성공") == true) {
            return parseBookNameFromAngleBrackets(text)
        }

        return null
    }

}