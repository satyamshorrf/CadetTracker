package com.cadet.tracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "attendance")
data class Attendance(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    
    @SerializedName("cadetId")
    val cadetId: String = "",
    
    @SerializedName("date")
    val date: String = "",
    
    @SerializedName("sessionId")
    val sessionId: String = "",
    
    @SerializedName("status")
    val status: String = "", // PRESENT, ABSENT, ON_LEAVE
    
    @SerializedName("timestamp")
    val timestamp: Long = 0,
    
    @SerializedName("location")
    val location: String = "",
    
    @SerializedName("markedBy")
    val markedBy: String = "",
    
    @SerializedName("notes")
    val notes: String = "",
    
    @SerializedName("synced")
    val synced: Boolean = false
)
