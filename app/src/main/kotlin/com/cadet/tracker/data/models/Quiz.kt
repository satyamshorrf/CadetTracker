package com.cadet.tracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quizzes")
data class Quiz(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    
    @SerializedName("courseId")
    val courseId: String = "",
    
    @SerializedName("title")
    val title: String = "",
    
    @SerializedName("questions")
    val questions: List<Question> = emptyList(),
    
    @SerializedName("passingScore")
    val passingScore: Int = 60,
    
    @SerializedName("timeLimit")
    val timeLimit: Int = 0, // in minutes, 0 = no limit
    
    @SerializedName("maxAttempts")
    val maxAttempts: Int = -1, // -1 = unlimited
    
    @SerializedName("createdAt")
    val createdAt: Long = 0
)

data class Question(
    @SerializedName("id")
    val id: String = "",
    
    @SerializedName("type")
    val type: String = "", // MULTIPLE_CHOICE, TRUE_FALSE, SHORT_ANSWER
    
    @SerializedName("question")
    val question: String = "",
    
    @SerializedName("options")
    val options: List<String> = emptyList(),
    
    @SerializedName("correctAnswer")
    val correctAnswer: String = "",
    
    @SerializedName("points")
    val points: Int = 1
)

@Entity(tableName = "quiz_results")
data class QuizResult(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    
    @SerializedName("quizId")
    val quizId: String = "",
    
    @SerializedName("cadetId")
    val cadetId: String = "",
    
    @SerializedName("answers")
    val answers: Map<String, String> = emptyMap(),
    
    @SerializedName("score")
    val score: Int = 0,
    
    @SerializedName("totalPoints")
    val totalPoints: Int = 0,
    
    @SerializedName("percentage")
    val percentage: Float = 0f,
    
    @SerializedName("passed")
    val passed: Boolean = false,
    
    @SerializedName("completedAt")
    val completedAt: Long = 0,
    
    @SerializedName("synced")
    val synced: Boolean = false
)
