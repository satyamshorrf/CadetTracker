package com.cadet.tracker.data.local

import androidx.room.*
import com.cadet.tracker.data.models.CampRegistration
import kotlinx.coroutines.flow.Flow

@Dao
interface CampRegistrationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegistration(registration: CampRegistration)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegistrations(registrations: List<CampRegistration>)

    @Update
    suspend fun updateRegistration(registration: CampRegistration)

    @Delete
    suspend fun deleteRegistration(registration: CampRegistration)

    @Query("SELECT * FROM camp_registrations WHERE id = :id")
    suspend fun getRegistrationById(id: String): CampRegistration?

    @Query("SELECT * FROM camp_registrations WHERE cadetId = :cadetId ORDER BY registrationDate DESC")
    fun getRegistrationsByCaldetId(cadetId: String): Flow<List<CampRegistration>>

    @Query("SELECT * FROM camp_registrations WHERE campId = :campId")
    fun getRegistrationsByCampId(campId: String): Flow<List<CampRegistration>>

    @Query("SELECT * FROM camp_registrations WHERE synced = 0")
    suspend fun getUnsyncedRegistrations(): List<CampRegistration>

    @Query("DELETE FROM camp_registrations")
    suspend fun deleteAllRegistrations()
}
