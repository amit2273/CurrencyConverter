package com.revidd.did.presentation.composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.revidd.did.model.Video

@Composable
fun VideoListContent(
    videos: List<Video>,
    carouselVideos: List<Video>,
    onVideoClick: (Video) -> Unit
) {
    val pagerState = rememberPagerState(
        pageCount = { carouselVideos.size }
    )
    val clickVideo = remember(onVideoClick) {
        { v: Video -> onVideoClick(v) }
    }
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        if (carouselVideos.isNotEmpty()) {
            item(key = "featured_header") {
                Text(
                    text = "Featured",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            item(key = "featured_carousel") {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) { page ->
                    CarouselItem(
                        video = carouselVideos[page],
                        onClick = { clickVideo(carouselVideos[page]) }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        item(key = "all_header") {
            Text(
                text = "All Videos",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        items(
            items = videos,
            key = { it.id }
        ) { video ->
            VideoListItem(
                video = video,
                onClick = { clickVideo(video) }
            )
        }
    }
}
