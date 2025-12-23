package com.revidd.did.repository

import com.revidd.did.model.Video

interface VideoRepository {
    suspend fun getVideos(): Result<List<Video>>
}


