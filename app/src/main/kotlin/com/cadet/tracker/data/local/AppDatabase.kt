package com.cadet.tracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cadet.tracker.data.models.*

@Database(
    entities = [
        User::class,
        Course::class,
        Attendance::class,
        Event::class,
        Quiz::class,
        QuizResult::class,
        Camp::class,
        CampRegistration::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun courseDao(): CourseDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun eventDao(): EventDao
    abstract fun quizDao(): QuizDao
    abstract fun quizResultDao(): QuizResultDao
    abstract fun campDao(): CampDao
    abstract fun campRegistrationDao(): CampRegistrationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cadet_tracker_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
