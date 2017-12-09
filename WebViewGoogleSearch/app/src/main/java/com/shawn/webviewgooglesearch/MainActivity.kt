package com.shawn.webviewgooglesearch

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import com.shawn.webviewgooglesearch.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    var binding:ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)

        val webViewClient = WebViewClient()
        binding?.webView?.setWebViewClient(webViewClient)

        binding?.webView?.loadUrl("https://www.google.com.tw")
        binding?.searchButton?.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {

                val input:String = binding?.searchEditText?.text.toString()
                binding?.webView?.loadUrl("https://www.google.com.tw/search?q=$input")
            }
        })
    }

}

