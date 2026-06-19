package com.cadet.tracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    
    @SerializedName("title")
    val title: String = "",
    
    @SerializedName("description")
    val description: String = "",
    
    @SerializedName("instructor")
    val instructor: String = "",
    
    @SerializedName("duration")
    val duration: String = "", // e.g., "30 days"
    
    @SerializedName("category")
    val category: String = "",
    
    @SerializedName("thumbnail")
    val thumbnail: String = "",
    
    @SerializedName("syllabus")
    val syllabus: String = "",
    
    @SerializedName("modules")
    val modules: List<String> = emptyList(),
    
    @SerializedName("createdAt")
    val createdAt: Long = 0,
    
    @SerializedName("status")
    val status: String = "active" // active, archived
)
