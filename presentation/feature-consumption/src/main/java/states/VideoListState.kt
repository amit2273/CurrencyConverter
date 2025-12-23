package states

import androidx.compose.runtime.Immutable
import com.revidd.did.model.Video

sealed interface VideoListState {
    data object Loading : VideoListState
    @Immutable
    data class Success(
        val videos: List<Video>,
        val carouselVideos: List<Video>
    ) : VideoListState
    data class Error(val message: String) : VideoListState
}