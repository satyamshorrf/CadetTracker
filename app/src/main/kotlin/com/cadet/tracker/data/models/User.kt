package com.cadet.tracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    @SerializedName("uid")
    val uid: String,
    
    @SerializedName("name")
    val name: String = "",
    
    @SerializedName("email")
    val email: String = "",
    
    @SerializedName("phone")
    val phone: String = "",
    
    @SerializedName("role")
    val role: String = "", // CADET, ANO, PI_STAFF, SUPER_ADMIN
    
    @SerializedName("unit")
    val unit: String = "",
    
    @SerializedName("profilePhoto")
    val profilePhoto: String = "",
    
    @SerializedName("createdAt")
    val createdAt: Long = 0,
    
    @SerializedName("updatedAt")
    val updatedAt: Long = 0,
    
    @SerializedName("status")
    val status: String = "active" // active, inactive, suspended
)

enum class UserRole {
    CADET,
    ANO,
    PI_STAFF,
    SUPER_ADMIN
}
