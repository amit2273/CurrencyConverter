package viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revidd.did.usecase.VideoDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import states.HomeIntent
import states.VideoListState

class VideoViewModel(
    private val videoDetailsUseCase: VideoDetailsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<VideoListState>(VideoListState.Loading)
    val uiState: StateFlow<VideoListState> = _uiState.asStateFlow()
    
    init {
        loadVideos()
    }

    fun handleIntent(homeIntent: HomeIntent){
        when(homeIntent){
            HomeIntent.LoadVideos,
            HomeIntent.Retry -> loadVideos()
            else -> Unit
        }
    }
    
    private fun loadVideos() {
        _uiState.value = VideoListState.Loading
        
        viewModelScope.launch {
            val result = videoDetailsUseCase.invoke()
            
            when {
                result.isSuccess -> {
                    val videos = result.getOrNull()?.first ?: emptyList()
                    val carouselVideos = result.getOrNull()?.second ?: emptyList()
                    _uiState.update { 
                        VideoListState.Success(videos, carouselVideos)
                    }
                }
                else -> {
                    _uiState.update {
                        VideoListState.Error(
                            result.exceptionOrNull()?.message ?: "Unknown error occurred"
                        )
                    }
                }
            }
        }
    }
}