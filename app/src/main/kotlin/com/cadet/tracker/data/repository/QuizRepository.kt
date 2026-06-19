package com.cadet.tracker.data.repository

import com.cadet.tracker.data.local.QuizDao
import com.cadet.tracker.data.local.QuizResultDao
import com.cadet.tracker.data.models.Quiz
import com.cadet.tracker.data.models.QuizResult
import com.cadet.tracker.data.remote.ApiService
import com.cadet.tracker.utils.Result
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val apiService: ApiService,
    private val quizDao: QuizDao,
    private val quizResultDao: QuizResultDao
) {

    fun getQuizById(id: String): Flow<Quiz?> {
        return quizDao.getQuizByIdFlow(id)
    }

    fun getQuizzesByCourseId(courseId: String): Flow<List<Quiz>> {
        return quizDao.getQuizzesByCourseId(courseId)
    }

    fun getQuizResultsByCaldetId(cadetId: String): Flow<List<QuizResult>> {
        return quizResultDao.getQuizResultsByCaldetId(cadetId)
    }

    suspend fun submitQuiz(result: QuizResult): Result<QuizResult> {
        return try {
            val localResult = result.copy()
            quizResultDao.insertQuizResult(localResult)
            Result.Success(localResult)
        } catch (e: Exception) {
            Timber.e("Error submitting quiz: ${e.message}")
            Result.Error(e.message ?: "Failed to submit quiz")
        }
    }

    suspend fun syncUnsyncedQuizResults(): Result<Unit> {
        return try {
            val unsyncedResults = quizResultDao.getUnsyncedQuizResults()
            if (unsyncedResults.isNotEmpty()) {
                val response = apiService.syncQuizResults(unsyncedResults)
                if (response.success) {
                    unsyncedResults.forEach { result ->
                        quizResultDao.updateQuizResult(result.copy(synced = true))
                    }
                }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.e("Error syncing quiz results: ${e.message}")
            Result.Error(e.message ?: "Failed to sync quiz results")
        }
    }
}
