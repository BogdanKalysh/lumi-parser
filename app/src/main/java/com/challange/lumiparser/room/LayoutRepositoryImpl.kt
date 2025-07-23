package com.challange.lumiparser.room

import android.util.Log
import com.challange.lumiparser.room.models.Layout
import kotlinx.coroutines.flow.Flow

class LayoutRepositoryImpl(private val layoutDao: LayoutDao): LayoutRepository {
    override suspend fun upsertLayout(layout: Layout) {
        Log.d(TAG, "Calling upsertLayout with: $layout")
        layoutDao.upsert(layout)
    }

    override fun getFirstLayout(): Flow<Layout?> {
        Log.d(TAG, "Calling getFirstLayout")
        return layoutDao.getFirst()
    }

    companion object {
        val TAG: String = LayoutRepositoryImpl::class.java.name
    }
}