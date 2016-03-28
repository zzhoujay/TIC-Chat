package com.zzhoujay.tic_chat.net

import com.google.gson.Gson
import com.zzhoujay.tic_chat.App
import com.zzhoujay.tic_chat.common.ApiKey
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Created by zhou on 16-3-27.
 */
object NetworkManager {

    const val header_type = "Content-Type"
    const val header_type_value = "application/json"
    const val header_api_key = "X-Bmob-Application-Id"
    const val header_rest_key = "X-Bmob-REST-API-Key"

    private val client: OkHttpClient by lazy {
        val c = OkHttpClient.Builder()
        c.cache(Cache(App.app.cacheDir, 1024 * 1024 * 4))
        c.build()
    }

    val gson: Gson by lazy { Gson() }

    fun generateRequestBuilder(): Request.Builder {
        val builder = Request.Builder()
        builder.addHeader(header_type, header_type_value)
                .addHeader(header_api_key, ApiKey.apiKey)
                .addHeader(header_rest_key, ApiKey.restKey)
        return builder
    }

    fun requestStringSync(request: Request): String {
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            return response.body().string()
        } else {
            throw RuntimeException("request failure")
        }
    }

    inline fun <reified T : Any> requestObjectSync(request: Request): T {
        val result = requestStringSync(request)
        return gson.fromJson(result, T::class.java)
    }

}