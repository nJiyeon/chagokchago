package com.capibara

import android.app.Application
import com.capibara.chagokchago.R
import com.kakao.vectormap.KakaoMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoMapSdk.init(this, getString(R.string.KAKAO_API_KEY))
    }
}