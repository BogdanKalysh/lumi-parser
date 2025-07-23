package com.challange.lumiparser.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Layout(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val layoutJson: String
)