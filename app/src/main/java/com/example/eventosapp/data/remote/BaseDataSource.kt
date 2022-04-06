package com.example.eventosapp.data.remote

import com.example.eventosapp.utils.NetworkState
import retrofit2.Response

open class BaseDataSource {
    suspend fun <T> getResult(call: suspend () -> Response<T>): NetworkState<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return NetworkState.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
}