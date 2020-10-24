package com.hendi.newsapiandroid.Client

import android.content.Context
import com.hendi.newsapiandroid.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ClientApi {
    fun createAPI(context: Context): BaseApi{
        val base_url = context.resources.getString(R.string.base_url)
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Accept", "Application/JSON")
                    .addHeader("X-Api-Key", context.resources.getString(R.string.api_key))
                    .build()
                chain.proceed(request)
            }.build()

        return create(base_url, client)
    }

    private fun create(base_url: String, client: OkHttpClient): BaseApi {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(base_url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        return retrofit.create(BaseApi::class.java)
    }
}