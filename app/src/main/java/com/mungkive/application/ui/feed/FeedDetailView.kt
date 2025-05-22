package com.mungkive.application.ui.feed

import android.R.attr.singleLine
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.mungkive.application.R

@Composable
fun FeedDetailView(
    feedId: String,
    viewModel: FeedViewModel = viewModel(),
    navController: NavHostController? = null,
    modifier: Modifier = Modifier
) {
    val feed = viewModel.getFeedById(feedId)
    val comments by remember { derivedStateOf { viewModel.getComments(feedId) } }
    var commentText by remember { mutableStateOf("") }


    if (feed == null) {
        // 피드가 없을 경우 처리
        Text(text = "피드를 찾을 수 없습니다.")
        return
    }

    Box(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // 상단 바
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navController?.let {
                        it.popBackStack()
                    }
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                        contentDescription = "뒤로 가기"
                    )
                }
                Text(
                    text = "피드",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.W700,
                    fontSize = 24.sp
                )
            }

            // 피드 내용
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 11.dp, start = 10.dp, end = 9.dp, bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 프로필 사진
                AsyncImage(
                    model = feed.userProfileUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                )
                Spacer(modifier = Modifier.width(10.dp))

                // 이름, 품종, 위치
                Column(modifier = Modifier.weight(1f)) {
                    Text(feed.userName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(feed.userBreed, fontSize = 10.sp, color = Color(0xFF7B7B7B))
                }
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = null,
                    modifier = Modifier.size(10.dp),
                    tint = Color.Gray
                )
                Text(feed.location, fontSize = 10.sp, color = Color(0xFF7B7B7B))
            }

            AsyncImage(
                model = feed.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )

            Row(
                Modifier.padding(start = 10.dp, top = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TODO: 좋아요 로직
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(22.dp)
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.ic_heart),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(
                    text = "${feed.likeCount}",
                    fontSize = 10.sp,
                    modifier = Modifier.padding(start = 2.dp, end = 12.dp)
                )

                // TODO: 댓글 로직 
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(22.dp)
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.ic_comment),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(
                    "${feed.commentCount}",
                    fontSize = 10.sp,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }

            Text(
                text = feed.date,
                fontSize = 8.sp,
                color = Color(0xFF7B7B7B),
                modifier = Modifier.padding(start = 10.dp, top = 2.dp)
            )
            Text(
                text = feed.content,
                fontWeight = FontWeight.W500,
                fontSize = 13.sp,
                modifier = Modifier.padding(start = 10.dp, bottom = 17.dp, end = 9.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis // ... 표시
            )

            // 구분선
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFECECEC))
            )
            // 댓글 리스트
            Column(
                Modifier
                    .weight(1f)
                    .padding(top = 12.dp)
            ) {

                comments.forEach { comment ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = comment.userProfileUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            comment.userName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            comment.content,
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        // 댓글 입력창
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.White)
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = commentText,
                onValueChange = { commentText = it },
                modifier = Modifier
                    .weight(1f),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                placeholder = { Text("댓글을 입력하세요", fontSize = 12.sp) },
                singleLine = true,
                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFEBF3FF),
                    unfocusedBorderColor = Color(0xFFBDBDBD)
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                // TODO: 댓글 작성 함수 추가
                onClick = {},   // 여기에 댓글 등록 로직 함수 작성하시면 됩니다
                enabled = commentText.isNotBlank(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp),
                modifier = Modifier.height(50.dp)
            ) {
                Text("입력", fontWeight = FontWeight.Bold)
            }
        }
    }
}


fun getPreviewFeedViewModel(): FeedViewModel {
    // Preview에서 ViewModel을 생성하고, 더미 데이터 채우기
    val viewModel = FeedViewModel()
    viewModel.fetchFeeds()
    return viewModel
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800)
@Composable
fun FeedDetailViewPreview() {
    val navController = rememberNavController()
    val viewModel = remember { getPreviewFeedViewModel() }

    // FeedId "1" 또는 "2" 등 fetchFeeds에서 사용한 값 사용
    FeedDetailView(
        feedId = "1",
        viewModel = viewModel,
        navController = navController,
        modifier = Modifier
    )
}