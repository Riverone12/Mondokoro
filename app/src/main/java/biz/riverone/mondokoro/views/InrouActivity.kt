package biz.riverone.mondokoro.views

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import biz.riverone.mondokoro.R

/**
 * InrouActivity.kt: 印籠の画像表示アクティビティ
 * Copyright (C) 2018 J.Kawahara
 * 2018.1.24 J.Kawahara 新規作成
 * 2018.1.25 J.Kawahara デバイスのコントローラで音量を調節できるようにした
 */
class InrouActivity : AppCompatActivity(), SensorEventListener {

    companion object {
        private const val QUEUE_SENSE_VALUE = 15.0f
    }
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null

    private var queueStarted: Boolean = false
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ナビゲーションバーをなくす
        val decor = this.window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        setContentView(R.layout.activity_inrou)

        // 画面をポートレートに固定する
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // デバイスのコントローラで音量を調節できるようにする
        volumeControlStream = AudioManager.STREAM_MUSIC

        // センサーマネージャを取得する
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // センサーを取得する
        val sensorList = sensorManager?.getSensorList(Sensor.TYPE_ACCELEROMETER)
        if (sensorList != null && sensorList.size > 0) {
            accelerometer = sensorList[0]
        }
    }

    override fun onResume() {
        super.onResume()

        // 画面がスリープ状態にならないように設定する
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // 音楽の準備をする
        audioSetup()

        // センサー処理を開始する
        if (accelerometer != null) {
            // sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
            sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()

        // 音楽を止める
        audioStop()

        // センサー処理を停止する
        sensorManager?.unregisterListener(this)

        // 画面のスリープ状態を許可する
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && event.sensor == accelerometer) {

            if (!queueStarted) {
                // Z軸の加速度を取得する
                //val accY = event.values[1]
                val accZ = event.values[2]
                //if (accY > QUEUE_SENSE_VALUE || accZ > QUEUE_SENSE_VALUE) {
                if (accZ > QUEUE_SENSE_VALUE) {
                    queueStarted = true
                    // センサー処理を停止する
                    sensorManager?.unregisterListener(this)

                    // 音楽を流す
                    audioPlay()
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    private fun audioSetup() {
        mediaPlayer = MediaPlayer.create(this, R.raw.inro)
        mediaPlayer?.setOnCompletionListener {
            audioStop()
        }
    }

    private fun audioPlay() {
        if (mediaPlayer == null) {
            audioSetup()
        }
        mediaPlayer?.start()
    }

    private fun audioStop() {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.release()

        mediaPlayer = null
    }
}
