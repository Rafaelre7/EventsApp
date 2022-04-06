package com.example.eventosapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "event")
data class Event(
    @PrimaryKey
    @SerializedName("id") val id : Int,
    @SerializedName("date") val date : Long,
    @SerializedName("description") val description : String,
    @SerializedName("image") val image : String,
    @SerializedName("longitude") val longitude : Double,
    @SerializedName("latitude") val latitude : Double,
    @SerializedName("price") val price : Double,
    @SerializedName("title") val title : String
)
