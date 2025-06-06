package com.mungkive.application.ui.feed

import android.util.Log
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

    // 피드별 댓글 Map (id -> List<CommentData>)
    private val _commentsMap = MutableStateFlow<Map<String, List<CommentData>>>(emptyMap())
    val commentsMap: StateFlow<Map<String, List<CommentData>>> = _commentsMap

    // 하드코딩 토큰
    val hardCodedToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiLtjIDtlIwiLCJpc3MiOiLrqqjtlIQiLCJ1c2VySWQiOiJ0ZXN0ZXIiLCJleHAiOjE3NDkyMTQzMjV9.qwJrqMXGdOroZbnWSp86toPeF3vIQQOj2uy4RUt7aAc"
    val apiService = NetworkModule.provideApiServiceWithoutInterceptor()
    val postRepository = PostRepository(apiService, hardCodedToken)  // 하드코딩 토큰은 함수 파라미터로


    // TODO: 서버에서 피드 가져오기 추가
    fun fetchFeeds() {
        /*viewModelScope.launch {
            val data = repository.getFeeds() // suspend fun
            _feedList.value = data
        }*/
        _feedList.value = listOf(
            FeedData(
                id = "1",
                userPic = "https://media.istockphoto.com/id/1422682177/ko/%EC%82%AC%EC%A7%84/%EA%B3%B5%EC%9B%90%EC%97%90%EC%9E%88%EB%8A%94-%EA%B7%80%EC%97%AC%EC%9A%B4-%ED%8F%AC%EB%A9%94%EB%9D%BC%EB%8B%88%EC%95%84-%EA%B0%9C-%EB%B3%B5%EC%82%AC-%EA%B3%B5%EA%B0%84.jpg?s=612x612&w=is&k=20&c=aZTHlC4tpxQUQf-ZVpWaxTm7UEBV7P6TNAEvS__v5cQ=",
                userName = "보리",
                userBreed = "포메라니안",
                locName = "올림픽공원",
                imageUrl = "https://media.istockphoto.com/id/1422682177/ko/%EC%82%AC%EC%A7%84/%EA%B3%B5%EC%9B%90%EC%97%90%EC%9E%88%EB%8A%94-%EA%B7%80%EC%97%AC%EC%9A%B4-%ED%8F%AC%EB%A9%94%EB%9D%BC%EB%8B%88%EC%95%84-%EA%B0%9C-%EB%B3%B5%EC%82%AC-%EA%B3%B5%EA%B0%84.jpg?s=612x612&w=is&k=20&c=aZTHlC4tpxQUQf-ZVpWaxTm7UEBV7P6TNAEvS__v5cQ=",
                likeCount = 25,
                commentCount = 3,
                date = "2025. 5. 15. 14:32",
                content = "오늘 즐거운 산책~"
            ),
            FeedData(
                id = "2",
                userPic = "https://media.istockphoto.com/id/1388796467/ko/%EC%82%AC%EC%A7%84/%EC%82%AC%EB%9E%91%EC%8A%A4%EB%9F%AC%EC%9A%B4-%EC%A0%8A%EC%9D%80-%EB%B9%84%EC%88%91%EC%9D%80-%ED%99%94%EC%B0%BD%ED%95%9C-%EB%B4%84-%EC%9E%94%EB%94%94%EB%B0%AD-%EC%A3%BC%EC%9C%84%EB%A5%BC-%EC%82%B0%EC%B1%85-%EA%B0%95%EC%95%84%EC%A7%80%EB%A5%BC-frise-%ED%99%9C%EC%84%B1-%EA%B7%80%EC%97%AC%EC%9A%B4-%EA%B0%95%EC%95%84%EC%A7%80.jpg?s=612x612&w=is&k=20&c=zKGyF2w2sdCQF8BIAFBAMgC_RMcrWLfp8Be1FDlHcO4=",
                userName = "구름이",
                userBreed = "비숑",
                locName = "강남구",
                imageUrl = "https://media.istockphoto.com/id/1388796467/ko/%EC%82%AC%EC%A7%84/%EC%82%AC%EB%9E%91%EC%8A%A4%EB%9F%AC%EC%9A%B4-%EC%A0%8A%EC%9D%80-%EB%B9%84%EC%88%91%EC%9D%80-%ED%99%94%EC%B0%BD%ED%95%9C-%EB%B4%84-%EC%9E%94%EB%94%94%EB%B0%AD-%EC%A3%BC%EC%9C%84%EB%A5%BC-%EC%82%B0%EC%B1%85-%EA%B0%95%EC%95%84%EC%A7%80%EB%A5%BC-frise-%ED%99%9C%EC%84%B1-%EA%B7%80%EC%97%AC%EC%9A%B4-%EA%B0%95%EC%95%84%EC%A7%80.jpg?s=612x612&w=is&k=20&c=zKGyF2w2sdCQF8BIAFBAMgC_RMcrWLfp8Be1FDlHcO4=",
                likeCount = 98,
                commentCount = 23,
                date = "2025. 5. 15. 11:19",
                content = "오늘은 예쁘게 미용한 날!! 오늘은 예쁘게 미용한 날!! 오늘은 예쁘게 미용한 날!! 오늘은 예쁘게 미용한 날!! 오늘은 예쁘게 미용한 날!!"
            )
        )
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
