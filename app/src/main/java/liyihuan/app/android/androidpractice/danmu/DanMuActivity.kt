package liyihuan.app.android.androidpractice.danmu

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_dan_mu.*
import liyihuan.app.android.androidpractice.R

class DanMuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dan_mu)



        initTag()
        btnStartDanmu.setOnClickListener {
//            rvDanmu.smoothScrollToPosition(Int.MAX_VALUE / 4)
            val mTranslationAnim1 = ObjectAnimator.ofFloat(llDanMu, "translationX", 0f, -llDanMu.width.toFloat())
            mTranslationAnim1.duration = (llDanMu.width / 0.2f).toLong()
            mTranslationAnim1.addUpdateListener {
                val i = it.animatedValue as Int

            }
            mTranslationAnim1.interpolator = LinearInterpolator()
            mTranslationAnim1.start()
        }
    }



    private fun initTag() {
        val arrayListOf = arrayListOf(
                DanMuBean("1", "内容xxx1"),
                DanMuBean("2", "内容xxx2"),
                DanMuBean("3", "内容xxx3"),
                DanMuBean("4", "内容xxx4"),
                DanMuBean("5", "内容xxx5"),
                DanMuBean("6", "内容xxx6"),
                DanMuBean("7", "内容xxx7"),
                DanMuBean("8", "内容xxx8"),
                DanMuBean("9", "内容xxx9"),
                DanMuBean("10", "内容xxx10"),
                DanMuBean("11", "内容xxx11"),
                DanMuBean("12", "内容xxx12"),
                DanMuBean("13", "内容xxx13"),
                DanMuBean("14", "内容xxx14"),
                DanMuBean("15", "内容xxx15"),
                DanMuBean("16", "内容xxx16"),
                DanMuBean("17", "内容xxx17"),
                DanMuBean("18", "内容xxx18"),
                DanMuBean("19", "内容xxx19"),
                DanMuBean("20", "内容xxx20"),
                DanMuBean("21", "内容xxx21")
        )
        val from = LayoutInflater.from(this)
        llDanMu.removeAllViews()
        arrayListOf.forEach {
            val inflate = from.inflate(R.layout.tag_view, llDanMu, false) as TextView
            inflate.text = "${it.name} --- ${it.content}"
            llDanMu.addView(inflate)
        }

        llDanMu1.removeAllViews()
        arrayListOf.forEach {
            val inflate = from.inflate(R.layout.tag_view, llDanMu1, false) as TextView
            inflate.text = "${it.name} --- ${it.content}"
            llDanMu1.addView(inflate)
        }




        // recyclerview 方式
        val splashAdapter = SplashAdapter(this, rvDanmu, arrayListOf)
        splashAdapter.setChildClickLis {
            Log.d("QWER", "ChildClick: $it")
        }
        rvDanmu.adapter = splashAdapter
        val scollLinearLayoutManager = ScollLinearLayoutManager(this)
        scollLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvDanmu.layoutManager = scollLinearLayoutManager
//        rvDanmu.smoothScrollToPosition(Int.MAX_VALUE / 4)

        rvDanmu.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.d("QWER", "onScrollStateChanged: $newState")

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }



}