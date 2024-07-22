package com.store.api.dtos

data class ResponseModel(
    val status: String,
    val message: String,
    val data: Any? = null
)