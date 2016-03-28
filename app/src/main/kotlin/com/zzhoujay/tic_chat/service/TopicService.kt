package com.zzhoujay.tic_chat.service

import com.zzhoujay.tic_chat.data.Topic
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by zhou on 16-3-27.
 */
interface TopicService {

    @GET("/1/classes/topic")
    fun listTopic(@Query("limit") limit: Int, @Query("skip") skip: Int, @Query("order") order: String): Call<List<Topic>>
}