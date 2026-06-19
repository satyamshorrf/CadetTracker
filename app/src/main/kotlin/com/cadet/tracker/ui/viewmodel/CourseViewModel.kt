package com.cadet.tracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cadet.tracker.data.models.Course
import com.cadet.tracker.data.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    private val _selectedCourse = MutableStateFlow<Course?>(null)
    val selectedCourse: StateFlow<Course?> = _selectedCourse.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                courseRepository.getActiveCourses().collect { courseList ->
                    _courses.value = courseList
                }
            } catch (e: Exception) {
                Timber.e("Error loading courses: ${e.message}")
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun selectCourse(courseId: String) {
        viewModelScope.launch {
            courseRepository.getCourseById(courseId).collect { course ->
                _selectedCourse.value = course
            }
        }
    }

    fun refreshCourses() {
        viewModelScope.launch {
            _isLoading.value = true
            when (courseRepository.refreshCourses()) {
                is com.cadet.tracker.utils.Result.Success -> {
                    Timber.d("Courses refreshed")
                }
                is com.cadet.tracker.utils.Result.Error -> {
                    _errorMessage.value = "Failed to refresh courses"
                }
                is com.cadet.tracker.utils.Result.Loading -> {}
            }
            _isLoading.value = false
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
