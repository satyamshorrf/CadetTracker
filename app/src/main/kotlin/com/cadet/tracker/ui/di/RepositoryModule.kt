package com.cadet.tracker.ui.di

import com.cadet.tracker.data.local.AppDatabase
import com.cadet.tracker.data.remote.ApiService
import com.cadet.tracker.data.repository.*
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        apiService: ApiService,
        database: AppDatabase
    ): AuthRepository {
        return AuthRepository(firebaseAuth, apiService, database.userDao())
    }

    @Provides
    @Singleton
    fun provideCourseRepository(
        apiService: ApiService,
        database: AppDatabase
    ): CourseRepository {
        return CourseRepository(apiService, database.courseDao())
    }

    @Provides
    @Singleton
    fun provideAttendanceRepository(
        apiService: ApiService,
        database: AppDatabase
    ): AttendanceRepository {
        return AttendanceRepository(apiService, database.attendanceDao())
    }

    @Provides
    @Singleton
    fun provideEventRepository(
        apiService: ApiService,
        database: AppDatabase
    ): EventRepository {
        return EventRepository(apiService, database.eventDao())
    }

    @Provides
    @Singleton
    fun provideQuizRepository(
        apiService: ApiService,
        database: AppDatabase
    ): QuizRepository {
        return QuizRepository(apiService, database.quizDao(), database.quizResultDao())
    }

    @Provides
    @Singleton
    fun provideCampRepository(
        apiService: ApiService,
        database: AppDatabase
    ): CampRepository {
        return CampRepository(apiService, database.campDao(), database.campRegistrationDao())
    }
}
