package com.cadet.tracker.data.repository

import com.cadet.tracker.data.local.CampDao
import com.cadet.tracker.data.local.CampRegistrationDao
import com.cadet.tracker.data.models.Camp
import com.cadet.tracker.data.models.CampRegistration
import com.cadet.tracker.data.remote.ApiService
import com.cadet.tracker.utils.Result
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class CampRepository @Inject constructor(
    private val apiService: ApiService,
    private val campDao: CampDao,
    private val campRegistrationDao: CampRegistrationDao
) {

    fun getUpcomingCamps(): Flow<List<Camp>> {
        return campDao.getUpcomingCamps()
    }

    fun getActiveCamps(): Flow<List<Camp>> {
        return campDao.getActiveCamps()
    }

    fun getCampById(id: String): Flow<Camp?> {
        return campDao.getCampByIdFlow(id)
    }

    fun getRegistrationsByCaldetId(cadetId: String): Flow<List<CampRegistration>> {
        return campRegistrationDao.getRegistrationsByCaldetId(cadetId)
    }

    suspend fun refreshCamps(): Result<Unit> {
        return try {
            val camps = apiService.getCamps()
            campDao.insertCamps(camps)
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.e("Error refreshing camps: ${e.message}")
            Result.Error(e.message ?: "Failed to refresh camps")
        }
    }

    suspend fun registerForCamp(campId: String): Result<CampRegistration> {
        return try {
            val registration = apiService.registerCamp(campId)
            val localRegistration = CampRegistration(
                id = registration.id,
                campId = registration.campId,
                cadetId = registration.cadetId,
                registrationDate = registration.registrationDate
            )
            campRegistrationDao.insertRegistration(localRegistration)
            Result.Success(localRegistration)
        } catch (e: Exception) {
            Timber.e("Error registering for camp: ${e.message}")
            Result.Error(e.message ?: "Failed to register for camp")
        }
    }

    suspend fun syncUnsyncedRegistrations(): Result<Unit> {
        return try {
            val unsyncedRegistrations = campRegistrationDao.getUnsyncedRegistrations()
            if (unsyncedRegistrations.isNotEmpty()) {
                val response = apiService.syncRegistrations(unsyncedRegistrations)
                if (response.success) {
                    unsyncedRegistrations.forEach { registration ->
                        campRegistrationDao.updateRegistration(registration.copy(synced = true))
                    }
                }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.e("Error syncing registrations: ${e.message}")
            Result.Error(e.message ?: "Failed to sync registrations")
        }
    }
}
