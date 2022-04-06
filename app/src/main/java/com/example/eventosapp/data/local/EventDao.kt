package com.example.eventosapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eventosapp.data.entity.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM event")
    fun getEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM event WHERE id = :id")
    fun getEvent(id: Int):LiveData<Event>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(events: List<Event>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(events: Event)

}