package com.cadet.tracker.data.remote

import com.cadet.tracker.data.models.*
import retrofit2.http.*

interface ApiService {

    // Auth APIs
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST("api/auth/verify-otp")
    suspend fun verifyOtp(@Body request: OtpRequest): OtpResponse

    // User APIs
    @GET("api/users/{uid}")
    suspend fun getUser(@Path("uid") uid: String): User

    @PUT("api/users/{uid}")
    suspend fun updateUser(@Path("uid") uid: String, @Body user: User): User

    // Course APIs
    @GET("api/courses")
    suspend fun getCourses(): List<Course>

    @GET("api/courses/{id}")
    suspend fun getCourse(@Path("id") id: String): Course

    @GET("api/courses/category/{category}")
    suspend fun getCoursesByCategory(@Path("category") category: String): List<Course>

    // Attendance APIs
    @GET("api/attendance/{cadetId}")
    suspend fun getAttendance(@Path("cadetId") cadetId: String): List<Attendance>

    @POST("api/attendance")
    suspend fun markAttendance(@Body attendance: Attendance): Attendance

    @GET("api/attendance/date/{date}")
    suspend fun getAttendanceByDate(@Path("date") date: String): List<Attendance>

    // Event APIs
    @GET("api/events")
    suspend fun getEvents(): List<Event>

    @GET("api/events/{id}")
    suspend fun getEvent(@Path("id") id: String): Event

    @POST("api/events/{id}/register")
    suspend fun registerEvent(@Path("id") eventId: String): EventRegistration

    // Quiz APIs
    @GET("api/quizzes/{id}")
    suspend fun getQuiz(@Path("id") id: String): Quiz

    @POST("api/quizzes/{id}/submit")
    suspend fun submitQuiz(@Path("id") quizId: String, @Body result: QuizResult): QuizResult

    // Camp APIs
    @GET("api/camps")
    suspend fun getCamps(): List<Camp>

    @GET("api/camps/{id}")
    suspend fun getCamp(@Path("id") id: String): Camp

    @POST("api/camps/{id}/register")
    suspend fun registerCamp(@Path("id") campId: String): CampRegistration

    // Sync APIs
    @POST("api/sync/attendance")
    suspend fun syncAttendance(@Body attendances: List<Attendance>): SyncResponse

    @POST("api/sync/quiz-results")
    suspend fun syncQuizResults(@Body results: List<QuizResult>): SyncResponse

    @POST("api/sync/registrations")
    suspend fun syncRegistrations(@Body registrations: List<CampRegistration>): SyncResponse
}

// Request/Response Models
data class LoginRequest(
    val email: String,
    val password: String,
    val biometric: Boolean = false
)

data class LoginResponse(
    val success: Boolean,
    val user: User?,
    val token: String,
    val message: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val role: String,
    val unit: String
)

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val uid: String?
)

data class OtpRequest(
    val uid: String,
    val otp: String
)

data class OtpResponse(
    val success: Boolean,
    val message: String
)

data class EventRegistration(
    val id: String,
    val eventId: String,
    val cadetId: String,
    val registrationDate: Long
)

data class SyncResponse(
    val success: Boolean,
    val message: String,
    val syncedCount: Int
)
