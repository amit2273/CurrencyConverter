package com.revidd.did.usecase

import com.revidd.did.model.Video

interface VideoDetailsUseCase {
    suspend operator fun invoke(): Result<Pair<List<Video>, List<Video>>>
}
