package com.revidd.did.usecase

import com.revidd.did.model.Video
import com.revidd.did.repository.VideoRepository

class VideoDetailsUseCaseImpl(private val videoRepository: VideoRepository) : VideoDetailsUseCase {

    override suspend fun invoke() : Result<Pair<List<Video>, List<Video>>> {
        return videoRepository.getVideos().map {
            Pair(it, getRandomVideosForCarousel(it))
        }
    }

    private fun getRandomVideosForCarousel(videos: List<Video>): List<Video> {
        return if (videos.size <= 4) {
            videos
        } else {
            videos.shuffled().take(4)
        }
    }
}