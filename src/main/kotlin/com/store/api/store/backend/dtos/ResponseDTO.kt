package com.store.api.store.backend.dtos

data class ResponseModel(
    val status: String,
    val message: String,
    val data: Any? = null
)