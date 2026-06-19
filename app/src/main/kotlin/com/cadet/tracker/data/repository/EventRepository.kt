package com.cadet.tracker.data.repository

import com.cadet.tracker.data.local.EventDao
import com.cadet.tracker.data.models.Event
import com.cadet.tracker.data.remote.ApiService
import com.cadet.tracker.utils.Result
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao
) {

    fun getUpcomingEvents(): Flow<List<Event>> {
        return eventDao.getUpcomingEvents()
    }

    fun getActiveEvents(): Flow<List<Event>> {
        return eventDao.getActiveEvents()
    }

    fun getEventById(id: String): Flow<Event?> {
        return eventDao.getEventByIdFlow(id)
    }

    suspend fun refreshEvents(): Result<Unit> {
        return try {
            val events = apiService.getEvents()
            eventDao.insertEvents(events)
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.e("Error refreshing events: ${e.message}")
            Result.Error(e.message ?: "Failed to refresh events")
        }
    }

    suspend fun registerForEvent(eventId: String): Result<Unit> {
        return try {
            apiService.registerEvent(eventId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.e("Error registering for event: ${e.message}")
            Result.Error(e.message ?: "Failed to register for event")
        }
    }
}
