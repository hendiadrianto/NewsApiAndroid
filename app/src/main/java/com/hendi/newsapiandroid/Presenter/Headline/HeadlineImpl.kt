package com.hendi.newsapiandroid.Presenter.Headline

import android.app.Dialog
import android.content.Context
import com.hendi.newsapiandroid.Client.ClientApi
import com.hendi.newsapiandroid.Helper.mIsNetworkAvailable
import com.hendi.newsapiandroid.Helper.mProgress
import com.hendi.newsapiandroid.Model.Headline.HeadlineResponse
import com.hendi.newsapiandroid.View.Headline.HeadlineView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeadlineImpl(view : HeadlineView) : HeadlinePrest {
    var headlineView : HeadlineView? = null

    init {
        headlineView  = view
    }
    override fun headlineGet(context: Context,data: Map<String, String>) {
        if (!mIsNetworkAvailable(context)){
            return headlineView!!.headlineError("Aktifkan koneksi internet terlebih dahulu !")
        }
        val progress = Dialog(context)
        mProgress(progress)
        ClientApi.createAPI(context).getTopHeadline(data).enqueue(object : Callback<HeadlineResponse> {
            override fun onFailure(call: Call<HeadlineResponse>, t: Throwable) {
                progress.dismiss()
                headlineView!!.headlineError(t.message!!)
            }

            override fun onResponse(call: Call<HeadlineResponse>, response: Response<HeadlineResponse>
            ) {
                progress.dismiss()
                if (response.isSuccessful && (response.body()!!.status == "ok" || response.body()!!.status == "OK" )){
                    headlineView!!.headlineResponse(response.body()!!)
                } else {
                    headlineView!!.headlineError("Respon gagal, mohon coba lagi.")
                }
            }
        })
    }
}