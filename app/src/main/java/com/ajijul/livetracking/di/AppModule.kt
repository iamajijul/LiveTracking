package com.ajijul.livetracking.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ajijul.livetracking.db.RunDAO
import com.ajijul.livetracking.db.RunDatabase
import com.ajijul.livetracking.helper.Constants.RUNNING_DATABASE_NAME
import com.ajijul.livetracking.repository.MainRepository
import com.ajijul.livetracking.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class) // This define the when object will create and destroy
object AppModule {

    @Singleton
    @Provides
    fun provideRunDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        RunDatabase::class.java,
        RUNNING_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: RunDatabase) = db.getRunDao()

    @Singleton
    @Provides
    fun provideMainRepository(dao: RunDAO): MainRepository = MainRepositoryImpl(dao)

}