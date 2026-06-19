package com.cadet.tracker.data.local

import androidx.room.*
import com.cadet.tracker.data.models.Attendance
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: Attendance)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendances(attendances: List<Attendance>)

    @Update
    suspend fun updateAttendance(attendance: Attendance)

    @Delete
    suspend fun deleteAttendance(attendance: Attendance)

    @Query("SELECT * FROM attendance WHERE id = :id")
    suspend fun getAttendanceById(id: String): Attendance?

    @Query("SELECT * FROM attendance WHERE cadetId = :cadetId ORDER BY timestamp DESC")
    fun getAttendanceByCaldetId(cadetId: String): Flow<List<Attendance>>

    @Query("SELECT * FROM attendance WHERE date = :date")
    fun getAttendanceByDate(date: String): Flow<List<Attendance>>

    @Query("SELECT * FROM attendance WHERE cadetId = :cadetId AND date = :date")
    suspend fun getAttendanceByCaldetIdAndDate(cadetId: String, date: String): Attendance?

    @Query("SELECT * FROM attendance WHERE synced = 0")
    suspend fun getUnsyncedAttendance(): List<Attendance>

    @Query("DELETE FROM attendance")
    suspend fun deleteAllAttendance()
}
