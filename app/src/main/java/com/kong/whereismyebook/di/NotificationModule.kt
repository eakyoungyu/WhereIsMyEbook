package com.kong.whereismyebook.di

import com.kong.whereismyebook.data.repository.LibraryRepository
import com.kong.whereismyebook.util.notification.handler.AladinEBookNotificationHandler
import com.kong.whereismyebook.util.notification.handler.CremaYes24NotificationHandler
import com.kong.whereismyebook.util.notification.handler.KyoboEBookNotificationHandler
import com.kong.whereismyebook.util.notification.handler.NotificationHandler
import com.kong.whereismyebook.util.notification.handler.NotificationHandlerFactory
import com.kong.whereismyebook.util.notification.handler.RidiNotificationHandler
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
    fun provideCremaYes24Handler(libraryRepository: LibraryRepository): CremaYes24NotificationHandler =
        CremaYes24NotificationHandler(libraryRepository)

    @Provides
    fun provideRidiHandler(libraryRepository: LibraryRepository): RidiNotificationHandler =
        RidiNotificationHandler(libraryRepository)

    @Provides
    fun provideNotificationHandlerFactory(
        kyoboHandler: KyoboEBookNotificationHandler,
        aladinHandler: AladinEBookNotificationHandler,
        cremaYes24Handler: CremaYes24NotificationHandler,
        ridiHandler: RidiNotificationHandler
    ): NotificationHandlerFactory = object : NotificationHandlerFactory {
        private val handlers = listOf(
            kyoboHandler,
            aladinHandler,
            cremaYes24Handler,
            ridiHandler,
        ).associateBy { it.packageName }

        override fun create(packageName: String): NotificationHandler? = handlers[packageName]
    }
}