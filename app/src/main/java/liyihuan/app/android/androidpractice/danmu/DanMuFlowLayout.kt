package liyihuan.app.android.androidpractice.danmu

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * @author created by liyihuanx
 * @date 2021/6/16
 * description: 类的描述
 */
class DanMuFlowLayout @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(context, attributeSet, defStyleAttr) {


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 为每个子View计算测量的限制信息 Mode / Size
        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val modeWidth = MeasureSpec.getMode(widthMeasureSpec)

        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)
        val modeHeight = MeasureSpec.getMode(heightMeasureSpec)


        var width = 0
        var height = 0
        //记录每一行的宽度和高度
        var lineWidth = 0
        var lineHeight = 0
        //得到内部元素的个数
        val cCount = childCount

        for (i in 0 until cCount) {
            val child = getChildAt(i)
            //测量子View的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            //得到LayoutParams（子View的LayoutParams其实是父布局的LayouParams）
            val lp = child.layoutParams as MarginLayoutParams
            //子View的宽度和高度
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            //换行
            if (lineWidth + childWidth > sizeWidth * 2 - paddingLeft - paddingRight) {
                //当前行的宽度和已经记录的最大行的宽度进行对比留下最大宽度
                //布局的宽度width是最宽行的宽度
                width = Math.max(width, lineWidth)
                //重置行宽
                lineWidth = childWidth
                //累加行高
                height += lineHeight
                lineHeight = childHeight
                //不换行
            } else {
                //叠加行宽
                lineWidth += childWidth
                //以最宽的子View的高度为当前的行高
                lineHeight = Math.max(lineHeight, childHeight)
            }

            //最后一个控件特殊处理(最后一行的高度没有累加，宽度没有比较)
            //如果换行执行if就没有处理换行后的布局的宽度和高度
            //如果不换行执行else就没有叠加当前行的高度和比较最大的行宽
            if (i == cCount - 1) {
                width = Math.max(width, lineWidth)
                height += lineHeight
            }
        }

        setMeasuredDimension(
                if (modeWidth == MeasureSpec.EXACTLY) sizeWidth else width + paddingLeft + paddingRight,
                if (modeHeight == MeasureSpec.EXACTLY) sizeHeight else height + paddingTop + paddingBottom
        )
    }


    override fun generateLayoutParams(attributeSet: AttributeSet?): ViewGroup.LayoutParams? {
        return MarginLayoutParams(context, attributeSet) //这里只关注MarginLayoutParams
    }


    //    @Override为什么上面的方法不提示？只有下面的方法可选择
    //    protected LayoutParams generateLayoutParams(LayoutParams p) {
    //        return super.generateLayoutParams(p);
    //    }

    /**
     * 以行来存储所有的View
     */
    private val mAllViews: ArrayList<ArrayList<View>> = ArrayList()

    /**
     * 每行的高度
     */
    private val mLineHeight: ArrayList<Int> = ArrayList()


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //会多次调用onLayout，所以先clear
        mAllViews.clear()
        mLineHeight.clear()
        //当前ViewGroup的宽度
        val width = width //此时已经执行了onMeasure（），可以直接调用getWidth获得宽度

        var lineWidth = 0
        var lineHeight = 0
        var lineViews: ArrayList<View> = ArrayList()
        val cCount = childCount
        for (i in 0 until cCount) {
            val child = getChildAt(i)
            val lp = child.layoutParams as MarginLayoutParams
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width * 2 - paddingRight - paddingLeft) {
                mLineHeight.add(lineHeight)
                //记录当前行的Views
                mAllViews.add(lineViews)
                //重置宽和高
                lineWidth = 0
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin
                //重置行View集合
                lineViews = ArrayList()
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin)
            lineViews.add(child) //别丢了
        } //for end


        //处理最后一行
        mLineHeight.add(lineHeight)
        mAllViews.add(lineViews)


        //设置子View的位置
        var left = paddingLeft
        var top = paddingTop
        //行数
        val lineNum = mAllViews.size

        for (i in 0 until lineNum) {
            //当前行的所有View
            lineViews = mAllViews[i]
            lineHeight = mLineHeight[i]
            for (j in lineViews.indices) {
                val child: View = lineViews[j]
                if (child.visibility == GONE) {
                    continue
                }
                val lp = child.layoutParams as MarginLayoutParams
                val lc = left + lp.leftMargin
                val tc = top + lp.topMargin
                val rc = lc + child.measuredWidth
                val bc = tc + child.measuredHeight
                //对子View 进行布局
                child.layout(lc, tc, rc, bc)
                left += child.measuredWidth + lp.leftMargin + lp.rightMargin
            }
            //换行
            left = paddingLeft
            top += lineHeight
        }
    }


}