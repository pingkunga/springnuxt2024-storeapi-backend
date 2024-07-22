package com.store.api.dto

data class ResponseModel(
    val status: String,
    val message: String,
    val data: Any? = null
)