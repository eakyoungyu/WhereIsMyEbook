package com.kong.whereismyebook.util.notification.handler

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

    fun handle(title: String?, text: String?) {
        applicationScope.launch {
            saveLibrary()
            val bookName = parseBookName(title, text)
            saveBook(bookName)

        }

    }
    abstract fun parseBookName(title: String?, text: String?): String?
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

