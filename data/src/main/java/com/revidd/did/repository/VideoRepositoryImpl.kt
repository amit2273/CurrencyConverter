package com.revidd.did.repository
import com.revidd.did.api.VideoApiService
import com.revidd.did.model.Video
import com.revidd.did.repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoRepositoryImpl(private val apiService: VideoApiService,) : VideoRepository {
    override suspend fun getVideos(): Result<List<Video>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = apiService.getVideos()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.map {
                    Video(
                     id = it.id,
                     title= it.title,
                     thumbnailUrl= it.thumbnailUrl,
                     duration= it.duration,
                     uploadTime= it.uploadTime,
                     views= it.views,
                     author= it.author,
                     videoUrl= it.videoUrl,
                     description= it.description,
                     subscriber= it.subscriber,
                     isLive= it.isLive
                    )
                })
            } else {
                Result.failure(Exception("Failed to fetch videos: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}