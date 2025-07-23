package com.challange.lumiparser.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.challange.lumiparser.room.models.Layout

@Database(
    entities = [Layout::class],
    version = 1
)
abstract class LayoutDatabase: RoomDatabase()  {
    abstract val dao: LayoutDao
}