package com.mungkive.application.ui.feed

import android.R.attr.contentDescription
import android.R.attr.top
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil3.compose.AsyncImage
import com.mungkive.application.R
import com.mungkive.application.ui.feed.data.FeedData

@Composable
fun FeedCard(feed: FeedData, onClick: () -> Unit) {

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 18.dp, vertical = 6.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFECECEC))
    ) {
        Column {
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

            // 좋아요/댓글/날짜/내용
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
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 10.dp, bottom = 17.dp, end = 9.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis // ... 표시
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedCardPreview() {
    val sampleFeed = FeedData(
        id = "1",
        userProfileUrl = "https://images.unsplash.com/photo-1518717758536-85ae29035b6d?auto=format&fit=facearea&w=256&h=256",
        userName = "보리",
        userBreed = "포메라니안",
        location = "올림픽공원",
        imageUrl = "https://images.unsplash.com/photo-1518717758536-85ae29035b6d?auto=format&fit=crop&w=600&q=80",
        likeCount = 25,
        commentCount = 3,
        date = "2025. 5. 15. 14:32",
        content = "오늘 즐거운 산책~"
    )
    FeedCard(feed = sampleFeed, onClick = {})
}
