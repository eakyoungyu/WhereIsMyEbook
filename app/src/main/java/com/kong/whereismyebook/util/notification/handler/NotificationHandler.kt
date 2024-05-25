package com.kong.whereismyebook.util.notification.handler

import android.app.Notification
import android.util.Log
import com.kong.whereismyebook.data.repository.LibraryRepository
import com.kong.whereismyebook.model.Book
import com.kong.whereismyebook.model.Library
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

interface NotificationHandlerFactory {
    fun create(packageName: String): NotificationHandler?
}
object NotificationHandlerRegistry {
    private var factory: NotificationHandlerFactory? = null

    fun inject(factory: NotificationHandlerFactory) {
        NotificationHandlerRegistry.factory = factory
    }
    fun getHandler(packageName: String): NotificationHandler? = factory?.create(packageName)
}

abstract class NotificationHandler(
    protected val libraryRepository: LibraryRepository
) {
    val TAG = NotificationHandler::class.simpleName
    abstract val packageName: String
    abstract val appName: String
    protected val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun handle(notification: Notification?) {
        applicationScope.launch {
            saveLibrary()
            val bookName = parseBookName(notification)
            saveBook(bookName)
        }
    }
    abstract fun parseBookName(notification: Notification?): String?

    fun parseBookNameFromAngleBrackets(target: String): String? {
        val start = target.indexOf('<') + 1
        val end = target.lastIndexOf('>')

        if (start != 0 && end != -1 && start < end) {
            val bookName = target.substring(start, end)
            Log.d(TAG, "Save Book $bookName")
            return bookName
        }
        return null
    }

    private suspend fun saveBook(bookName: String?) {
        Log.d(TAG, "Save Book -E: $bookName, $packageName")
        if (bookName != null) {
            libraryRepository.addBook(Book(name = bookName, libraryPackageName = packageName))
            Log.d(TAG, "Save Book -X: $bookName, $packageName")
        }
    }


    private suspend fun saveLibrary() {
        Log.d(TAG, "SaveLibrary - E")
        if (libraryRepository.searchLibraryByPackageName(packageName).firstOrNull() == null) {
            Log.d(TAG, "Save library $packageName")
            libraryRepository.addLibrary(Library(packageName = packageName, name = appName))
        } else {
            Log.d(TAG, "$packageName is already existed")
        }
        Log.d(TAG, "SaveLibrary - X")
    }
}

