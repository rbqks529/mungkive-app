import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mungkive.application.ui.feed.FeedCard
import com.mungkive.application.ui.feed.FeedData
import com.mungkive.application.ui.feed.FeedViewModel

@Composable
fun FeedListView(
    viewModel: FeedViewModel = viewModel(),
    onWriteClick: () -> Unit,
    onFeedClick: (FeedData) -> Unit
) {
    val feedList by viewModel.feedList.collectAsState()

    // 최초 진입 시 서버에서 피드 데이터 가져오기
    LaunchedEffect(Unit) {
        viewModel.fetchFeeds()
    }

    if (feedList.isEmpty()) {
        // 1. 로딩 인디케이터 및 메시지
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
            }
        }
    } else {
        // 2. 피드 리스트 UI
        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                Text(
                    text = "피드",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.W700,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 19.dp, top = 32.dp, bottom = 4.dp)
                )
            }

            items(feedList) { feedData ->
                FeedCard(feed = feedData, onClick = { onFeedClick(feedData) })
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun FeedListViewPreview() {
    FeedListView(onFeedClick = {}, onWriteClick = {})
}
