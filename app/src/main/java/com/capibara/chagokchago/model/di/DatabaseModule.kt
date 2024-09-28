package com.capibara.chagokchago.model.di

import com.capibara.chagokchago.model.database.AppDatabase
import com.capibara.chagokchago.model.repository.KeywordDao
import com.capibara.chagokchago.model.repository.KeywordRepository
import com.capibara.chagokchago.model.repository.LocationDao
import com.capibara.chagokchago.model.repository.LocationRepository
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration() // 필요시 마이그레이션 재생성
            .build()
    }

    @Provides
    @Singleton
    fun provideKeywordDao(database: AppDatabase): KeywordDao {
        return database.keywordDao()
    }

    @Provides
    @Singleton
    fun provideItemDao(database: AppDatabase): LocationDao {
        return database.locationDao()
    }

    @Provides
    @Singleton
    fun provideKeywordRepository(keywordDao: KeywordDao): KeywordRepository {
        return KeywordRepository(keywordDao)
    }

    @Provides
    @Singleton
    fun provideLocationSearcher(locationDao: LocationDao): LocationRepository {
        return LocationRepository(locationDao)
    }

}