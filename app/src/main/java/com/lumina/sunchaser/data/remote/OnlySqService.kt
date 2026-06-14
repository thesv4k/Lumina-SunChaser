package com.lumina.sunchaser.data.remote

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

data class OnlySqRequest(
    val model: String = "gpt-4",
    val messages: List<Message>
)

data class Message(val role: String, val content: String)

data class OnlySqResponse(
    val choices: List<Choice>
)

data class Choice(val message: Message)

interface OnlySqService {
    @POST("v1/chat/completions")
    suspend fun getRecommendation(
        @Header("Authorization") apiKey: String,
        @Body request: OnlySqRequest
    ): OnlySqResponse

    companion object {
        const val BASE_URL = "https://api.onlysq.ru/"
    }
}
