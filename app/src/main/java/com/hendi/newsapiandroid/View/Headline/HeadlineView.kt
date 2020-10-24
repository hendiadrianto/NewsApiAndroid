package com.hendi.newsapiandroid.View.Headline

import com.hendi.newsapiandroid.Model.Headline.HeadlineResponse

interface HeadlineView {
    fun headlineResponse(data : HeadlineResponse)
    fun headlineError(string: String)
}