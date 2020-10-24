package com.hendi.newsapiandroid.Presenter.Source

import android.app.Dialog
import android.content.Context
import com.hendi.newsapiandroid.Client.ClientApi
import com.hendi.newsapiandroid.Helper.mIsNetworkAvailable
import com.hendi.newsapiandroid.Helper.mProgress
import com.hendi.newsapiandroid.Model.Source.SourceResponse
import com.hendi.newsapiandroid.View.Source.SourceView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SourceImpl (view : SourceView) : SourcePrest {

    var sourceView : SourceView? = null
    init {
        sourceView = view
    }

    override fun sourceGet(context: Context) {
        if (!mIsNetworkAvailable(context)){
            return sourceView!!.sourceError("Aktifkan koneksi internet terlebih dahulu !")
        }

        val progress = Dialog(context)
        mProgress(progress)
        ClientApi.createAPI(context).getSource().enqueue(object : Callback<SourceResponse>{
            override fun onFailure(call: Call<SourceResponse>, t: Throwable) {
                progress.dismiss()
                sourceView!!.sourceError(t.message!!)
            }

            override fun onResponse(
                call: Call<SourceResponse>,
                response: Response<SourceResponse>
            ) {
                progress.dismiss()
                if (response.isSuccessful && (response.body()!!.status == "ok" || response.body()!!.status == "OK")){
                    sourceView!!.sourceResponse(response.body()!!)
                } else {
                    sourceView!!.sourceError("Respon gagal, mohon coba lagi.")
                }
            }
        })
    }
}