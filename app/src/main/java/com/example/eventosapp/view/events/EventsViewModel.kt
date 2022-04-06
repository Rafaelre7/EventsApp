package com.example.eventosapp.view.events

import androidx.lifecycle.ViewModel
import com.example.eventosapp.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(private val eventRepository: EventRepository) : ViewModel() {

    val events = eventRepository.getEvents()

}