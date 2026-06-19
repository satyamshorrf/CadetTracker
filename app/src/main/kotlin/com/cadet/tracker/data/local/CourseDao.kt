package com.cadet.tracker.data.local

import androidx.room.*
import com.cadet.tracker.data.models.Course
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: Course)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<Course>)

    @Update
    suspend fun updateCourse(course: Course)

    @Delete
    suspend fun deleteCourse(course: Course)

    @Query("SELECT * FROM courses WHERE id = :id")
    suspend fun getCourseById(id: String): Course?

    @Query("SELECT * FROM courses WHERE id = :id")
    fun getCourseByIdFlow(id: String): Flow<Course?>

    @Query("SELECT * FROM courses WHERE status = 'active'")
    fun getActiveCourses(): Flow<List<Course>>

    @Query("SELECT * FROM courses")
    fun getAllCourses(): Flow<List<Course>>

    @Query("SELECT * FROM courses WHERE category = :category")
    fun getCoursesByCategory(category: String): Flow<List<Course>>

    @Query("DELETE FROM courses")
    suspend fun deleteAllCourses()
}
