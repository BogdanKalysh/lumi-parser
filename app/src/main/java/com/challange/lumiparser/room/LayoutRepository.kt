package com.challange.lumiparser.room

import com.challange.lumiparser.room.models.Layout
import kotlinx.coroutines.flow.Flow

interface LayoutRepository {
    suspend fun upsertLayout(layout: Layout)
    fun getFirstLayout(): Flow<Layout?>
}