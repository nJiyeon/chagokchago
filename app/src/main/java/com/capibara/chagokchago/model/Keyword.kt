package com.capibara.chagokchago.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keyword")
data class Keyword(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val recentKeyword: String
)