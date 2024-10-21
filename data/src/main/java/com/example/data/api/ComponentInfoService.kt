package com.example.data.api

import com.example.data.dto.ComponentDTO
import retrofit2.http.GET

interface ComponentInfoService {
    @GET("interview/list.json")
    suspend fun getComponentList(): ComponentDTO
}