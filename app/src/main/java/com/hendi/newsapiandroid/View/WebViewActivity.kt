package com.hendi.newsapiandroid.View

import android.annotation.SuppressLint
import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.hendi.newsapiandroid.Helper.Constant
import com.hendi.newsapiandroid.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        init()
        setClient()
        loadUrl()

        id_btn_close_wv.setOnClickListener{
            finish()
        }

    }

    private fun loadUrl() {
        val url = intent.getStringExtra(Constant.URL_STRING)
        val title = intent.getStringExtra(Constant.TITLE_STRING)

        if(url!!.isNotBlank()){
            id_webview_wv.loadUrl(url)
            if(title!!.isNotBlank()){
                id_title_wv.text = title
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
        id_webview_wv.settings.javaScriptEnabled = true
        id_webview_wv.settings.loadWithOverviewMode = true
        id_webview_wv.settings.useWideViewPort = true
        id_webview_wv.settings.domStorageEnabled = true

        id_webview_wv.webViewClient = object : WebViewClient() {
            override
            fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                //showErrorLayout()
            }

        }
    }

    fun setClient(){
        id_webview_wv.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(
                view: WebView,
                newProgress: Int
            ) {
                super.onProgressChanged(view, newProgress)
                id_progressbar_wv.progress = newProgress
                if (newProgress < Constant.MAX_PROGRESS && id_progressbar_wv.visibility == ProgressBar.GONE) {
                    id_progressbar_wv.visibility = ProgressBar.VISIBLE
                }

                if (newProgress == Constant.MAX_PROGRESS) {
                    id_progressbar_wv.visibility = ProgressBar.GONE
                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}