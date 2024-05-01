package com.kong.whereismyebook.di

import com.kong.whereismyebook.util.notification.handler.NotificationHandlerFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppEntryPoint {
    fun notificationHandlerFactory(): NotificationHandlerFactory
}