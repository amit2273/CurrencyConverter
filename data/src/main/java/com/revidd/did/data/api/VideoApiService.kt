package com.revidd.did.data.api
import com.revidd.did.data.VideoDto
import retrofit2.Response
import retrofit2.http.GET

interface VideoApiService {
    @GET("poudyalanil/ca84582cbeb4fc123a13290a586da925/raw/videos.json")
    suspend fun getVideos(): Response<List<VideoDto>>
}