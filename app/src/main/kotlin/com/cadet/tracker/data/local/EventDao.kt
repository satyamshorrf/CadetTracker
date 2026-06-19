package com.cadet.tracker.data.local

import androidx.room.*
import com.cadet.tracker.data.models.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<Event>)

    @Update
    suspend fun updateEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getEventById(id: String): Event?

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventByIdFlow(id: String): Flow<Event?>

    @Query("SELECT * FROM events WHERE status = 'upcoming' ORDER BY date ASC")
    fun getUpcomingEvents(): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE status IN ('upcoming', 'ongoing') ORDER BY date ASC")
    fun getActiveEvents(): Flow<List<Event>>

    @Query("SELECT * FROM events")
    fun getAllEvents(): Flow<List<Event>>

    @Query("DELETE FROM events")
    suspend fun deleteAllEvents()
}
