package com.cadet.tracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "camps")
data class Camp(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    
    @SerializedName("title")
    val title: String = "",
    
    @SerializedName("description")
    val description: String = "",
    
    @SerializedName("startDate")
    val startDate: String = "",
    
    @SerializedName("endDate")
    val endDate: String = "",
    
    @SerializedName("location")
    val location: String = "",
    
    @SerializedName("coordinates")
    val coordinates: String = "", // "latitude,longitude"
    
    @SerializedName("maxParticipants")
    val maxParticipants: Int = 0,
    
    @SerializedName("registeredCount")
    val registeredCount: Int = 0,
    
    @SerializedName("schedule")
    val schedule: String = "",
    
    @SerializedName("status")
    val status: String = "upcoming", // upcoming, ongoing, completed, cancelled
    
    @SerializedName("createdAt")
    val createdAt: Long = 0
)

@Entity(tableName = "camp_registrations")
data class CampRegistration(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    
    @SerializedName("campId")
    val campId: String = "",
    
    @SerializedName("cadetId")
    val cadetId: String = "",
    
    @SerializedName("registrationDate")
    val registrationDate: Long = 0,
    
    @SerializedName("status")
    val status: String = "pending", // pending, approved, rejected, cancelled
    
    @SerializedName("synced")
    val synced: Boolean = false
)
