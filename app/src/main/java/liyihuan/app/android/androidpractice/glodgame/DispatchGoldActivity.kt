package liyihuan.app.android.androidpractice.glodgame

import android.animation.ValueAnimator
import android.graphics.Path
import android.graphics.PathMeasure
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.PatternPathMotion
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_dispatch_gold.*
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.utils.SizeUtils
import java.util.*

class DispatchGoldActivity : AppCompatActivity() {
    private var goldX = -1
    private var goldY = -1
    private var betX = -1
    private var betY = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispatch_gold)

        btn_start_bet.setOnClickListener {

            repeat(10) {
                calculateGoldLocation()
                calculateBetLocation()
                var propView = ImageView(this)
                startAnimation(propView)
            }

        }
    }

    private fun startAnimation(propView: ImageView) {
        val path = Path()
        propView.setImageResource(R.mipmap.game_prop_gold)

        val lp = RelativeLayout.LayoutParams(
                SizeUtils.dp2px(this, 28f),
                SizeUtils.dp2px(this, 28f),
        )
        propView.layoutParams = lp
        ll_root.addView(propView, 0)

        path.moveTo(goldX.toFloat(), goldY.toFloat())
        path.quadTo(goldX / 2f, goldY.toFloat(), betX.toFloat(), betY.toFloat())

        // 总长度
        val mPathMeasure = PathMeasure(path, false)
        // 构建动画
        val valueAnimation = ValueAnimator.ofFloat(0f, mPathMeasure.length)
        valueAnimation.duration = 3000
        valueAnimation.addUpdateListener {
            val location = FloatArray(2)
            mPathMeasure.getPosTan(it.animatedValue as Float, location, null)
            propView.translationX = location[0]
            propView.translationY = location[1]

        }

        valueAnimation.start()
    }

    private fun calculateBetLocation() {

        val parentLocation = IntArray(2)
        ll_root.getLocationInWindow(parentLocation)

        val location = IntArray(2)
        view_put_gold.getLocationInWindow(location)
        val width = Random().nextInt(view_put_gold.width)
        val height = Random().nextInt(view_put_gold.height)

        betX = location[0] + width - parentLocation[0]
        betY = location[1] + height - parentLocation[1]
    }

    private fun calculateGoldLocation() {
        val parentLocation = IntArray(2)
        ll_root.getLocationInWindow(parentLocation)

        val location = IntArray(2)
        iv_gold.getLocationInWindow(location)
        goldX = location[0] - parentLocation[0]
        goldY = location[1] - parentLocation[1]

//
//
//        view_red.translationX = goldX.toFloat()
//        view_red.translationY = goldY.toFloat()
        // getX,getY 平移后的坐标
        // left，top 还是起始坐标
//        Log.d("QWER", "view_red.x: ${view_red.x}")
//        Log.d("QWER", "view_red.y: ${view_red.y}")
//
//        Log.d("QWER", "view_red.left: ${view_red.left}")
//
//        Log.d("QWER", "view_red.top: ${view_red.top}")


    }
}