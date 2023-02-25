package com.example.surfease.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.surfease.WebViewViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@SuppressLint("RememberReturnType")
@Composable
fun DuckDuckGoWebView(viewModel: WebViewViewModel = viewModel()) {
    val isLoading by viewModel.isLoading.observeAsState(true)
    val context = LocalContext.current
    val webView = remember { viewModel.createWebView(context) }
    Column {
        Box(modifier = Modifier) {
            IconButton(
                onClick = {
                    viewModel.onBackPressed()
                },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    viewModel.createWebView(context) // Create WebView using the passed context
                    viewModel.webView!! // Return the created WebView
                },
                update = {
                    it.loadUrl("https://duckduckgo.com") // Update WebView with new URL
                }
            )
            if (isLoading) {
                // Show progress bar while loading
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    strokeWidth = 2.dp
                )
            }

        }
    }
}
