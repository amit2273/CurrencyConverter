package states

import com.revidd.did.model.Video

sealed interface HomeIntent {
    data object LoadVideos : HomeIntent
    data class OpenVideo(val video: Video) : HomeIntent
    data object Retry : HomeIntent
}
