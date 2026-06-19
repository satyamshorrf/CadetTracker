package com.cadet.tracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "events")
data class Event(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    
    @SerializedName("title")
    val title: String = "",
    
    @SerializedName("description")
    val description: String = "",
    
    @SerializedName("date")
    val date: String = "",
    
    @SerializedName("time")
    val time: String = "",
    
    @SerializedName("location")
    val location: String = "",
    
    @SerializedName("type")
    val type: String = "", // PARADE, COMPETITION, CEREMONY, TRAINING
    
    @SerializedName("maxParticipants")
    val maxParticipants: Int = 0,
    
    @SerializedName("registeredCount")
    val registeredCount: Int = 0,
    
    @SerializedName("status")
    val status: String = "upcoming", // upcoming, ongoing, completed, cancelled
    
    @SerializedName("createdAt")
    val createdAt: Long = 0
)
