package com.capibara.chagokchago.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.capibara.chagokchago.model.entity.KeywordEntity
import com.capibara.chagokchago.model.entity.LocationEntity
import com.capibara.chagokchago.model.repository.KeywordDao
import com.capibara.chagokchago.model.repository.LocationDao

@Database(entities = [KeywordEntity::class, LocationEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun keywordDao(): KeywordDao
    abstract fun locationDao(): LocationDao
}