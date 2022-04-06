package com.example.eventosapp.data.repository

import com.example.eventosapp.data.entity.CheckIn
import com.example.eventosapp.data.remote.EventRemoteDataSource
import javax.inject.Inject

class CheckInRepository @Inject constructor(private val remoteDataSource: EventRemoteDataSource) {
    suspend fun checkIn(body: CheckIn): Boolean {
        return try {
            remoteDataSource.checkIn(body)
            return true
        } catch (e: Exception) {
            false
        }
    }
}