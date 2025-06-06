package com.mungkive.application.ui.feed

import android.util.Log
import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mungkive.application.network.NetworkModule
import com.mungkive.application.repository.PostRepository
import com.mungkive.application.ui.feed.data.CommentData
import com.mungkive.application.ui.feed.data.FeedData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {
    // 피드 데이터
    private val _feedList = MutableStateFlow<List<FeedData>>(emptyList())
    val feedList: StateFlow<List<FeedData>> = _feedList

    // 피드별 댓글
    private val _commentsMap = MutableStateFlow<Map<String, List<CommentData>>>(emptyMap())
    val commentsMap: StateFlow<Map<String, List<CommentData>>> = _commentsMap

    // 하드 코딩 토큰
    val hardCodedToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiLtjIDtlIwiLCJpc3MiOiLrqqjtlIQiLCJ1c2VySWQiOiJ0ZXN0ZXIiLCJleHAiOjE3NDkyMTQzMjV9.qwJrqMXGdOroZbnWSp86toPeF3vIQQOj2uy4RUt7aAc"
    val apiService = NetworkModule.provideApiServiceWithoutInterceptor()
    val postRepository = PostRepository(apiService, hardCodedToken)  // 하드코딩 토큰은 함수 파라미터로


    fun fetchFeeds() {
        viewModelScope.launch {
            try {
                val response = postRepository.getFeeds()
                val feedList = response.map { post ->
                    val userPicUrl = if (post.userPic.startsWith("http")) {
                        post.userPic
                    } else {
                        "http://34.47.102.235:8080/${post.userPic}"
                    }
                    val feedPicture = if (post.picture.startsWith("http")) {
                        post.picture
                    } else {
                        "http://34.47.102.235:8080/${post.picture}"
                    }
                    FeedData(
                        id = post.id.toString(),
                        userPic = userPicUrl,
                        userName = post.userName,
                        userBreed = post.userBreed,
                        locName = post.locName,
                        picture = feedPicture,     // 게시글 사진
                        likes = post.likes,
                        commentCount = post.commentCount, // 새 필드 반영
                        date = post.date,
                        content = post.content
                    )
                }
                _feedList.value = feedList
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }

    // 서버에서 댓글 불러오기
    fun fetchComments(feedId: String) {
        viewModelScope.launch {
            try {
                val postId = feedId.toLongOrNull() ?: return@launch
                val response = postRepository.getComments(postId)
                val commentList = response.map { res ->
                    // userPic이 "uploads/..."면 URL로 조립
                    val userPicUrl = if (res.userPic.startsWith("http")) {
                        res.userPic
                    } else {
                        "http://34.47.102.235:8080/${res.userPic}"
                    }
                    CommentData(
                        userProfileUrl = userPicUrl,
                        userName = res.userName,
                        content = res.content
                    )
                }
                // StateFlow에 저장 (화면 자동 갱신)
                val currentMap = _commentsMap.value.toMutableMap()
                currentMap[feedId] = commentList
                _commentsMap.value = currentMap
            } catch (e: Exception) {
                Log.d("FeedVIewModel", "Error fetching comments: ${e.message}")
            }
        }
    }

    // id로 FeedData 찾기
    fun getFeedById(feedId: String): FeedData? {
        return feedList.value.find { it.id == feedId }
    }

    // 댓글 반환 함수
    fun getComments(feedId: String): List<CommentData> {
        return commentsMap.value[feedId] ?: emptyList()
    }
}
