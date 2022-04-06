package com.example.eventosapp.utils

data class NetworkState<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): NetworkState<T> {
            return NetworkState(Status.SUCCESS, data, null)
        }

        fun <T> error(data: T? = null): NetworkState<T> {
            return NetworkState(Status.ERROR, data, null)
        }

        fun <T> loading(data: T? = null): NetworkState<T> {
            return NetworkState(Status.LOADING, data, null)
        }
    }
}