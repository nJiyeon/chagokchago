package com.capibara.chagokchago.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keyword")
data class KeywordEntity2(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val recentKeyword: String
)