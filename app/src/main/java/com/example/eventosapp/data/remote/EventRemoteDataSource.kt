package com.example.eventosapp.data.remote

import com.example.eventosapp.data.entity.CheckIn
import javax.inject.Inject

class EventRemoteDataSource @Inject constructor(
    private val eventService: EventService
) : BaseDataSource() {
    suspend fun getEvents() = getResult { eventService.getEvents() }
    suspend fun getEvent(id: Int) = getResult { eventService.getEvent(id) }
    suspend fun checkIn(body: CheckIn) = eventService.checkIn(body)
}