package com.cadet.tracker.data.repository

import com.cadet.tracker.data.local.CourseDao
import com.cadet.tracker.data.models.Course
import com.cadet.tracker.data.remote.ApiService
import com.cadet.tracker.utils.Result
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val apiService: ApiService,
    private val courseDao: CourseDao
) {

    fun getActiveCourses(): Flow<List<Course>> {
        return courseDao.getActiveCourses()
    }

    fun getCourseById(id: String): Flow<Course?> {
        return courseDao.getCourseByIdFlow(id)
    }

    fun getCoursesByCategory(category: String): Flow<List<Course>> {
        return courseDao.getCoursesByCategory(category)
    }

    suspend fun refreshCourses(): Result<Unit> {
        return try {
            val courses = apiService.getCourses()
            courseDao.insertCourses(courses)
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.e("Error refreshing courses: ${e.message}")
            Result.Error(e.message ?: "Failed to refresh courses")
        }
    }

    suspend fun getCourseFromServer(id: String): Result<Course> {
        return try {
            val course = apiService.getCourse(id)
            courseDao.insertCourse(course)
            Result.Success(course)
        } catch (e: Exception) {
            Timber.e("Error fetching course: ${e.message}")
            Result.Error(e.message ?: "Failed to fetch course")
        }
    }
}
