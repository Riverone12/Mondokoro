package biz.riverone.mondokoro.views

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import biz.riverone.mondokoro.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

/**
 * SerifActivity.kt: 台詞を確認するアクティビティ
 * Copyright (C) 2018 J.Kawahara
 * 2018.1.24 J.Kawahara 新規作成
 */
class SerifActivity : AppCompatActivity() {

    private lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serif)

        // 画面をポートレートに固定する
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setTitle(R.string.menuSerif)

        // AdMob
        MobileAds.initialize(this, "ca-app-pub-1882812461462801~8373541594")
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
}
