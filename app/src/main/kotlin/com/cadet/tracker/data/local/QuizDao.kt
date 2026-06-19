package com.cadet.tracker.data.local

import androidx.room.*
import com.cadet.tracker.data.models.Quiz
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quiz: Quiz)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizzes(quizzes: List<Quiz>)

    @Update
    suspend fun updateQuiz(quiz: Quiz)

    @Delete
    suspend fun deleteQuiz(quiz: Quiz)

    @Query("SELECT * FROM quizzes WHERE id = :id")
    suspend fun getQuizById(id: String): Quiz?

    @Query("SELECT * FROM quizzes WHERE id = :id")
    fun getQuizByIdFlow(id: String): Flow<Quiz?>

    @Query("SELECT * FROM quizzes WHERE courseId = :courseId")
    fun getQuizzesByCourseId(courseId: String): Flow<List<Quiz>>

    @Query("SELECT * FROM quizzes")
    fun getAllQuizzes(): Flow<List<Quiz>>

    @Query("DELETE FROM quizzes")
    suspend fun deleteAllQuizzes()
}
