package com.hendi.newsapiandroid.Client

import com.hendi.newsapiandroid.Model.Headline.HeadlineResponse
import com.hendi.newsapiandroid.Model.Source.SourceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface BaseApi {

    @Headers("Content-Type: application/json")
    @GET("/v2/top-headlines")
    fun getTopHeadline(
        @QueryMap data: Map<String, String>): Call<HeadlineResponse>

    @Headers("Content-Type: application/json")
    @GET("/v2/sources")
    fun getSource(): Call<SourceResponse>
}