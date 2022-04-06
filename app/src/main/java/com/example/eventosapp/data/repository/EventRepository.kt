package com.example.eventosapp.data.repository

import com.example.eventosapp.data.local.EventDao
import com.example.eventosapp.data.remote.EventRemoteDataSource
import com.example.eventosapp.utils.performGetOperation
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val remoteDataSource: EventRemoteDataSource,
    private val localDataSource: EventDao
) {
    fun getEvents() = performGetOperation(
        { localDataSource.getEvents() },
        { remoteDataSource.getEvents() },
        { localDataSource.insertAll(it) }
    )
    fun getEvent(id: Int) = performGetOperation(
        { localDataSource.getEvent(id) },
        { remoteDataSource.getEvent(id) },
        { localDataSource.insert(it) }
    )
}