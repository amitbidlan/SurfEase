package com.example.surfease

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WebViewViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    @SuppressLint("StaticFieldLeak")
    var webView: WebView? = null

    @SuppressLint("SetJavaScriptEnabled")
    fun createWebView(context: Context) {
        webView = WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                @Deprecated("Deprecated in Java")
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url!!)
                    return true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    _isLoading.value = false
                }
            }
            loadUrl("https://duckduckgo.com")
        }
    }

    fun destroyWebView() {
        webView?.destroy()
        webView = null
    }

    fun onBackPressed(): Boolean {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
            return true
        }
        return false
    }
}
