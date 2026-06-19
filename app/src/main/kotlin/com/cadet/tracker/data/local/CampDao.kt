package com.cadet.tracker.data.local

import androidx.room.*
import com.cadet.tracker.data.models.Camp
import kotlinx.coroutines.flow.Flow

@Dao
interface CampDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCamp(camp: Camp)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCamps(camps: List<Camp>)

    @Update
    suspend fun updateCamp(camp: Camp)

    @Delete
    suspend fun deleteCamp(camp: Camp)

    @Query("SELECT * FROM camps WHERE id = :id")
    suspend fun getCampById(id: String): Camp?

    @Query("SELECT * FROM camps WHERE id = :id")
    fun getCampByIdFlow(id: String): Flow<Camp?>

    @Query("SELECT * FROM camps WHERE status = 'upcoming' ORDER BY startDate ASC")
    fun getUpcomingCamps(): Flow<List<Camp>>

    @Query("SELECT * FROM camps WHERE status IN ('upcoming', 'ongoing') ORDER BY startDate ASC")
    fun getActiveCamps(): Flow<List<Camp>>

    @Query("SELECT * FROM camps")
    fun getAllCamps(): Flow<List<Camp>>

    @Query("DELETE FROM camps")
    suspend fun deleteAllCamps()
}
