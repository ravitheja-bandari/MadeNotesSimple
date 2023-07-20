package com.rt.madenotessimple.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
//    @ColumnInfo("title")
    val title: String,
    val content: String
) : Parcelable
