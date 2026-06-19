package com.cadet.tracker.data.local

import androidx.room.*
import com.cadet.tracker.data.models.QuizResult
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizResult(result: QuizResult)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizResults(results: List<QuizResult>)

    @Update
    suspend fun updateQuizResult(result: QuizResult)

    @Delete
    suspend fun deleteQuizResult(result: QuizResult)

    @Query("SELECT * FROM quiz_results WHERE id = :id")
    suspend fun getQuizResultById(id: String): QuizResult?

    @Query("SELECT * FROM quiz_results WHERE cadetId = :cadetId ORDER BY completedAt DESC")
    fun getQuizResultsByCaldetId(cadetId: String): Flow<List<QuizResult>>

    @Query("SELECT * FROM quiz_results WHERE quizId = :quizId")
    fun getQuizResultsByQuizId(quizId: String): Flow<List<QuizResult>>

    @Query("SELECT * FROM quiz_results WHERE synced = 0")
    suspend fun getUnsyncedQuizResults(): List<QuizResult>

    @Query("DELETE FROM quiz_results")
    suspend fun deleteAllQuizResults()
}
