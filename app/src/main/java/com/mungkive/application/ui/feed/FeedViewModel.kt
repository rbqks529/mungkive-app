package com.mungkive.application.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {
    private val _feedList = MutableStateFlow<List<FeedData>>(emptyList())
    val feedList: StateFlow<List<FeedData>> = _feedList

    // 서버에서 데이터 불러오기 (repository/network 사용)
    fun fetchFeeds() {
        /*viewModelScope.launch {
            val data = repository.getFeeds() // suspend fun
            _feedList.value = data
        }*/
        _feedList.value = listOf(
            FeedData(
                id = "1",
                userProfileUrl = "https://media.istockphoto.com/id/1422682177/ko/%EC%82%AC%EC%A7%84/%EA%B3%B5%EC%9B%90%EC%97%90%EC%9E%88%EB%8A%94-%EA%B7%80%EC%97%AC%EC%9A%B4-%ED%8F%AC%EB%A9%94%EB%9D%BC%EB%8B%88%EC%95%84-%EA%B0%9C-%EB%B3%B5%EC%82%AC-%EA%B3%B5%EA%B0%84.jpg?s=612x612&w=is&k=20&c=aZTHlC4tpxQUQf-ZVpWaxTm7UEBV7P6TNAEvS__v5cQ=",
                userName = "보리",
                userBreed = "포메라니안",
                location = "올림픽공원",
                imageUrl = "https://media.istockphoto.com/id/1422682177/ko/%EC%82%AC%EC%A7%84/%EA%B3%B5%EC%9B%90%EC%97%90%EC%9E%88%EB%8A%94-%EA%B7%80%EC%97%AC%EC%9A%B4-%ED%8F%AC%EB%A9%94%EB%9D%BC%EB%8B%88%EC%95%84-%EA%B0%9C-%EB%B3%B5%EC%82%AC-%EA%B3%B5%EA%B0%84.jpg?s=612x612&w=is&k=20&c=aZTHlC4tpxQUQf-ZVpWaxTm7UEBV7P6TNAEvS__v5cQ=",
                likeCount = 25,
                commentCount = 3,
                date = "2025. 5. 15. 14:32",
                content = "오늘 즐거운 산책~"
            ),
            FeedData(
                id = "2",
                userProfileUrl = "https://media.istockphoto.com/id/1388796467/ko/%EC%82%AC%EC%A7%84/%EC%82%AC%EB%9E%91%EC%8A%A4%EB%9F%AC%EC%9A%B4-%EC%A0%8A%EC%9D%80-%EB%B9%84%EC%88%91%EC%9D%80-%ED%99%94%EC%B0%BD%ED%95%9C-%EB%B4%84-%EC%9E%94%EB%94%94%EB%B0%AD-%EC%A3%BC%EC%9C%84%EB%A5%BC-%EC%82%B0%EC%B1%85-%EA%B0%95%EC%95%84%EC%A7%80%EB%A5%BC-frise-%ED%99%9C%EC%84%B1-%EA%B7%80%EC%97%AC%EC%9A%B4-%EA%B0%95%EC%95%84%EC%A7%80.jpg?s=612x612&w=is&k=20&c=zKGyF2w2sdCQF8BIAFBAMgC_RMcrWLfp8Be1FDlHcO4=",
                userName = "구름이",
                userBreed = "비숑",
                location = "강남구",
                imageUrl = "https://media.istockphoto.com/id/1388796467/ko/%EC%82%AC%EC%A7%84/%EC%82%AC%EB%9E%91%EC%8A%A4%EB%9F%AC%EC%9A%B4-%EC%A0%8A%EC%9D%80-%EB%B9%84%EC%88%91%EC%9D%80-%ED%99%94%EC%B0%BD%ED%95%9C-%EB%B4%84-%EC%9E%94%EB%94%94%EB%B0%AD-%EC%A3%BC%EC%9C%84%EB%A5%BC-%EC%82%B0%EC%B1%85-%EA%B0%95%EC%95%84%EC%A7%80%EB%A5%BC-frise-%ED%99%9C%EC%84%B1-%EA%B7%80%EC%97%AC%EC%9A%B4-%EA%B0%95%EC%95%84%EC%A7%80.jpg?s=612x612&w=is&k=20&c=zKGyF2w2sdCQF8BIAFBAMgC_RMcrWLfp8Be1FDlHcO4=",
                likeCount = 98,
                commentCount = 23,
                date = "2025. 5. 15. 11:19",
                content = "오늘은 예쁘게 미용한 날!!"
            )
        )
    }
}
