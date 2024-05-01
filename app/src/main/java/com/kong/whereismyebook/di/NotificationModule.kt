package com.kong.whereismyebook.di

import com.kong.whereismyebook.data.repository.LibraryRepository
import com.kong.whereismyebook.util.notification.handler.AladinEBookNotificationHandler
import com.kong.whereismyebook.util.notification.handler.KyoboEBookNotificationHandler
import com.kong.whereismyebook.util.notification.handler.NotificationHandler
import com.kong.whereismyebook.util.notification.handler.NotificationHandlerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {
    @Provides
    fun provideKyoboHandler(libraryRepository: LibraryRepository): KyoboEBookNotificationHandler =
        KyoboEBookNotificationHandler(libraryRepository)

    @Provides
    fun provideAladinHandler(libraryRepository: LibraryRepository): AladinEBookNotificationHandler =
        AladinEBookNotificationHandler(libraryRepository)

    @Provides
    fun provideNotificationHandlerFactory(
        kyoboHandler: KyoboEBookNotificationHandler,
        aladinHandler: AladinEBookNotificationHandler
    ): NotificationHandlerFactory = object : NotificationHandlerFactory {
        private val handlers = listOf(
            kyoboHandler,
            aladinHandler
        ).associateBy { it.packageName }

        override fun create(packageName: String): NotificationHandler? = handlers[packageName]
    }
}