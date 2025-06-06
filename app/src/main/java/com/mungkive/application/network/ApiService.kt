package com.mungkive.application.network

import com.mungkive.application.network.dto.CommentCreateRequest
import com.mungkive.application.network.dto.CommentResponse
import com.mungkive.application.network.dto.LoginRequest
import com.mungkive.application.network.dto.MsgResponse
import com.mungkive.application.network.dto.PostCreateRequest
import com.mungkive.application.network.dto.PostResponse
import com.mungkive.application.network.dto.ProfileEditRequest
import com.mungkive.application.network.dto.ProfileResponse
import com.mungkive.application.network.dto.RegisterRequest
import com.mungkive.application.network.dto.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    /* 인증 */
    @POST("register")
    suspend fun register(@Body req: RegisterRequest): TokenResponse

    @POST("login")
    suspend fun login(@Body req: LoginRequest): TokenResponse

    /* 프로필 */
    @GET("profile")
    suspend fun getProfile(): ProfileResponse

    @POST("profile/edit")
    suspend fun editProfile(@Body req: ProfileEditRequest): MsgResponse

    /* 게시글 */
    // 게시글 작성 시 Authorization 헤더를 수동으로 추가하게 임의로 수정
    // TODO: 헤더에 토큰 넣는거 빼기
    @POST("post")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Body req: PostCreateRequest
    ): Response<Void>

    @GET("posts")
    suspend fun listPosts(): List<PostResponse>

    @GET("posts/mypost")
    suspend fun myPosts(): List<PostResponse>

    @DELETE("post/{id}")
    suspend fun deletePost(@Path("id") id: Long): MsgResponse

    /* 댓글 & 좋아요 */
    @POST("post/{id}/comment")
    suspend fun addComment(
        @Path("id") postId: Long,
        @Body req: CommentCreateRequest
    ): MsgResponse

    @GET("post/{id}/comments")
    suspend fun getComments(
        @Header("Authorization") token: String,
        @Path("id") postId: Long
    ): List<CommentResponse>

    @POST("post/{id}/like")
    suspend fun likePost(@Path("id") postId: Long): MsgResponse

    @POST("post/{id}/unlike")
    suspend fun unlikePost(@Path("id") postId: Long): MsgResponse
}
