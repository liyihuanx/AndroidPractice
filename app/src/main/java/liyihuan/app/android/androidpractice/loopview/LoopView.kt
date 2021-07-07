package liyihuan.app.android.androidpractice.loopview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.danmu.DanMuBean
import liyihuan.app.android.androidpractice.danmu.DanMuFlowLayout

/**
 * @author created by liyihuanx
 * @date 2021/6/18
 * description: 类的描述
 */
class LoopView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : HorizontalScrollView(context, attrs, defStyleAttr){

    private lateinit var mLoopView1: DanMuFlowLayout
    private lateinit var mLoopView2: DanMuFlowLayout

    init {
        initView()
    }

    private fun initView() {
//        setOnTouchListener { v, event ->
//            true
//        }
        val wallContent = FrameLayout(context)
        mLoopView1 = DanMuFlowLayout(context)
        mLoopView2 = DanMuFlowLayout(context)
        wallContent.addView(mLoopView1, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        wallContent.addView(mLoopView2, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        addView(wallContent, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

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
                DanMuBean("10", "内容"),
                DanMuBean("11", "内容xxx11"),
                DanMuBean("12", "内容xxx12"),
                DanMuBean("13", "内容xxx13"),
                DanMuBean("14", "内容xxx14"),
                DanMuBean("15", "内容"),
                DanMuBean("16", "内容xxx16"),
                DanMuBean("17", "内容xxx17"),
                DanMuBean("18", "内容xxx18"),
                DanMuBean("19", "内容"),
                DanMuBean("20", "内容xxx20"),
                DanMuBean("21", "内容xxx21")
        )

        mLoopView1.removeAllViews()
        val mInflater = LayoutInflater.from(context)

        arrayListOf.forEach {
            val tv1 = mInflater.inflate(R.layout.tag_view, mLoopView1, false) as TextView
            val tv2 = mInflater.inflate(R.layout.tag_view, mLoopView1, false) as TextView
            tv1.text = "${it.name} -- ${it.content}"
            tv2.text = "${it.name} -- ${it.content}"

            mLoopView1.addView(tv1)
            mLoopView2.addView(tv2)
        }
    }





}