package liyihuan.app.android.androidpractice.video

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.view.KeyEvent
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import liyihuan.app.android.androidpractice.R

class VoiceActivity : AppCompatActivity() {

    private var seekBar: SeekBar? = null
    private var tv_sound: TextView? = null
    private lateinit var mAudioManager: AudioManager
    private var maxVolume = 0
    private  var currentVolume:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice)

        seekBar = findViewById(R.id.seekbar)
        tv_sound = findViewById(R.id.tv_sound)


        //获取系统的Audio管理者
        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        //最大音量
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        //当前音量

        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        //seekBar设置最大值为最大音量，这样设置当前进度时不用换算百分比了
        seekBar!!.max = maxVolume
        //seekBar设置当前进度为当前音量
        setView()

        //seekBar设置拖动监听

        seekBar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                //设置媒体音量为当前seekBar进度
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
                currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                setView()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })



    }

    /*
     * onkeydown 事件会在用户按下一个键盘按键时发生
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                if (--currentVolume >= 0) {
                    setView()
                } else {
                    currentVolume = 0
                }
                return true
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                if (++currentVolume <= maxVolume) {
                    setView()
                } else {
                    currentVolume = maxVolume
                }
                return true
            }
            KeyEvent.KEYCODE_VOLUME_MUTE -> {
                setView()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun setView() {
        tv_sound!!.text = currentVolume.toString() + ""
        seekBar!!.progress = currentVolume
    }

}