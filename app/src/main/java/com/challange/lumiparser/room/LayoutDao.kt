package com.challange.lumiparser.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.challange.lumiparser.room.models.Layout
import kotlinx.coroutines.flow.Flow

@Dao
interface LayoutDao {
    @Upsert
    suspend fun upsert(layout: Layout)

    @Query("SELECT * FROM layout LIMIT 1")
    fun getFirst(): Flow<Layout?>
}