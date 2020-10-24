package com.hendi.newsapiandroid.Presenter.Headline

import android.content.Context

interface HeadlinePrest {
    fun headlineGet(context: Context,data: Map<String, String>)
}