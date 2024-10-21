package com.example.data.api

import com.example.data.dto.ComponentDTO
import retrofit2.http.GET

interface ContentInfoService {
    @GET("interview/list")
    fun getComponentList(): ComponentDTO
}