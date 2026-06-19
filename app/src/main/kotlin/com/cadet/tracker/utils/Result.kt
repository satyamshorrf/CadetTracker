package com.cadet.tracker.utils

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

suspend inline fun <T> Result<T>.onSuccess(block: suspend (T) -> Unit): Result<T> {
    if (this is Result.Success) block(this.data)
    return this
}

suspend inline fun <T> Result<T>.onError(block: suspend (String) -> Unit): Result<T> {
    if (this is Result.Error) block(this.message)
    return this
}
