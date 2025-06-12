package com.mungkive.application.ui.map

import androidx.compose.foundation.BorderStroke
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
fun MapFeed(
    feed: FeedData,
    modifier: Modifier = Modifier,
    onDetailClick: () -> Unit,
    onToggleLike: () -> Unit
    ) {
    Card(
        shape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        modifier = modifier
            .padding(horizontal = 2.dp, vertical = 0.dp)
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            ),
        elevation = CardDefaults.cardElevation(4.dp),
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
                Spacer(Modifier.size(3.dp))
                AsyncImage(
                    model = feed.userPic,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                )
                Spacer(modifier = Modifier.width(10.dp))

                // 이름, 품종, 위치
                Column(modifier = Modifier.weight(1f)) {
                    Text(feed.userName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(feed.userBreed, fontSize = 10.sp, color = Color(0xFF7B7B7B))
                }
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = null,
                    modifier = Modifier.size(10.dp),
                    tint = Color.Gray
                )
                Text(feed.locName,
                    fontSize = 10.sp,
                    color = Color(0xFF7B7B7B)
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 6.dp, end = 5.dp)
                    .clickable { onDetailClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(

                ) {
                    Row {
                        Text(
                            text = feed.date,
                            fontSize = 8.sp,
                            color = Color(0xFF7B7B7B),
                            modifier = Modifier.padding(start = 10.dp, top = 2.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier.padding(end = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            // TODO: 좋아요 로직
                            IconButton(
                                onClick = {onToggleLike()},
                                modifier = Modifier
                                    .size(22.dp)
                            ) {
                                Icon(
                                    ImageVector.vectorResource(R.drawable.ic_heart),
                                    contentDescription = null,
                                    tint = if(feed.isLiked) Color.Red else Color.Black,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = "${feed.likes}",
                                fontSize = 10.sp,
                                modifier = Modifier.padding(start = 2.dp, end = 2.dp)
                                    . width(25.dp)
                            )

                            Icon(
                                    ImageVector.vectorResource(R.drawable.ic_comment),
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(20.dp)
                                        .padding(end = 3.dp)
                            )
                            Text(
                                "${feed.commentCount}",
                                fontSize = 10.sp,
                                modifier = Modifier.padding(start = 2.dp, end = 2.dp)
                                    .width(25.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(3.dp))
                    Text(
                        text = feed.content,
                        fontWeight = FontWeight.W500,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 10.dp, bottom = 17.dp, end = 9.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis // ... 표시
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MapFeedPreview() {
    val temp = FeedData(
        id = "1",
        userPic = "https://images.unsplash.com/photo-1518717758536-85ae29035b6d?auto=format&fit=facearea&w=256&h=256",
        userName = "보리",
        userBreed = "포메라니안",
        locName = "올림픽공원",
        locate = "",
        picture = "https://images.unsplash.com/photo-1518717758536-85ae29035b6d?auto=format&fit=crop&w=600&q=80",
        likes = 100,
        commentCount = 10,
        date = "2025. 5. 15. 14:32",
        content = "오늘 즐거운 산책~",
        isLiked = false
    )
    MapFeed(temp, onDetailClick = {}, onToggleLike = {})
}