package com.example.core_module.di

class ApiKeyProvider(
    val statesApiKey: String,
    val weatherApiKey: String,
    val statesBaseUrl: String,
    val weatherBaseUrl: String,
    val isDebug: Boolean
)