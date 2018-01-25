package biz.riverone.mondokoro

import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.AudioManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import biz.riverone.mondokoro.views.InrouActivity
import biz.riverone.mondokoro.views.SerifActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

/**
 * この紋所が目に入らぬか！？
 * Copyright (C) 2018 J.Kawahara
 * 2018.1.24 J.Kawahara 新規作成
 * 2018.1.24 J.Kawahara ver.1.00
 * 2018.1.25 J.Kawahara ver.1.03 デバイスのコントローラで音量を調節できるようにした
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 画面をポートレートに固定する
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // デバイスのコントローラで音量を調節できるようにする
        volumeControlStream = AudioManager.STREAM_MUSIC

        initializeControls()

        // AdMob
        MobileAds.initialize(this, "ca-app-pub-1882812461462801~8373541594")
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    private fun initializeControls() {
        val buttonStart = findViewById<Button>(R.id.buttonStart)
        buttonStart.setOnClickListener {
            val intent = Intent(this, InrouActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_serif) {
            val intent = Intent(this, SerifActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
