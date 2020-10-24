package com.hendi.newsapiandroid.View.Source

import com.hendi.newsapiandroid.Model.Source.SourceResponse

interface SourceView {
    fun sourceResponse(data : SourceResponse)
    fun sourceError(string: String)
}