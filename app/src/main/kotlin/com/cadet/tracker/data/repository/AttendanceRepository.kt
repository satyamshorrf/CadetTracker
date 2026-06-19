package com.cadet.tracker.data.repository

import com.cadet.tracker.data.local.AttendanceDao
import com.cadet.tracker.data.models.Attendance
import com.cadet.tracker.data.remote.ApiService
import com.cadet.tracker.utils.Result
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class AttendanceRepository @Inject constructor(
    private val apiService: ApiService,
    private val attendanceDao: AttendanceDao
) {

    fun getAttendanceByCaldetId(cadetId: String): Flow<List<Attendance>> {
        return attendanceDao.getAttendanceByCaldetId(cadetId)
    }

    fun getAttendanceByDate(date: String): Flow<List<Attendance>> {
        return attendanceDao.getAttendanceByDate(date)
    }

    suspend fun markAttendance(attendance: Attendance): Result<Attendance> {
        return try {
            val localAttendance = attendance.copy()
            attendanceDao.insertAttendance(localAttendance)
            Result.Success(localAttendance)
        } catch (e: Exception) {
            Timber.e("Error marking attendance: ${e.message}")
            Result.Error(e.message ?: "Failed to mark attendance")
        }
    }

    suspend fun syncUnsyncedAttendance(): Result<Unit> {
        return try {
            val unsyncedAttendance = attendanceDao.getUnsyncedAttendance()
            if (unsyncedAttendance.isNotEmpty()) {
                val response = apiService.syncAttendance(unsyncedAttendance)
                if (response.success) {
                    unsyncedAttendance.forEach { attendance ->
                        attendanceDao.updateAttendance(attendance.copy(synced = true))
                    }
                }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.e("Error syncing attendance: ${e.message}")
            Result.Error(e.message ?: "Failed to sync attendance")
        }
    }
}
