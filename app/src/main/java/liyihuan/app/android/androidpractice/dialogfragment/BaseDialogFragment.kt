package liyihuan.app.android.androidpractice.dialogfragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import liyihuan.app.android.androidpractice.R
import java.lang.reflect.Field


/**
 * @author created by liyihuanx
 * @date 2021/9/17
 * @description: 类的描述
 */
abstract class BaseDialogFragment : DialogFragment() {

    var mDefaultListener: BaseDialogListener? = null
    private val INVALID_LAYOUT_ID = -1

    private var mCancelable: Boolean = true
    private var mGravityEnum:Int = Gravity.CENTER

    lateinit var rootView: View

    @StyleRes
    private var animationStyleId: Int? = null

    fun setDefaultListener(defaultListener: BaseDialogListener): BaseDialogFragment {
        mDefaultListener = defaultListener
        return this
    }


    fun applyCancelable(cancelable: Boolean): BaseDialogFragment {
        mCancelable = cancelable
        return this
    }

    fun applyGravityStyle(gravity: Int): BaseDialogFragment {
        mGravityEnum = gravity
        return this
    }

    fun applyAnimationStyle(@StyleRes resId: Int): BaseDialogFragment {
        animationStyleId = resId
        return this
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout to use as dialog or embedded fragment
        rootView = inflater.inflate(getViewLayoutId(), container, false)
        return rootView
    }


    override fun onStart() {
        super.onStart()
        //STYLE_NO_FRAME设置之后会调至无法自动点击外部自动消失，因此添加手动控制
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.applyGravityStyle(mGravityEnum, animationStyleId)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //取消系统对dialog样式上的干扰，防止dialog宽度无法全屏
        setStyle(STYLE_NO_FRAME, R.style.baseDialogFragment)
    }

    override fun onResume() {
        super.onResume()
        dialog?.setCanceledOnTouchOutside(mCancelable)
        if(!mCancelable){
            dialog?.setOnKeyListener { v, keyCode, event -> keyCode == KeyEvent.KEYCODE_BACK }
        }
    }

    abstract fun getViewLayoutId(): Int


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObservable()
        initView()
    }

    abstract fun initView()
    open fun initObservable(){}


    open fun show(manager: FragmentManager){
//        if (isAdded) return
        try {
            val mDismissed: Field = this.javaClass.superclass!!.getDeclaredField("mDismissed")
            val mShownByMe: Field = this.javaClass.superclass!!.getDeclaredField("mShownByMe")
            mDismissed.isAccessible = true
            mShownByMe.isAccessible = true
            mDismissed.setBoolean(this, false)
            mShownByMe.setBoolean(this, true)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        val ft: FragmentTransaction = manager.beginTransaction()
//        if (!this.isAdded) {
//            ft.add(this, javaClass.simpleName)
//        }
        ft.add(this, javaClass.simpleName)
        ft.commitAllowingStateLoss()
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mDefaultListener?.onDismiss(this)
    }

    open class BaseDialogListener {
        /**
         * 点击确定，并携带指定类型参数
         */
        open fun onDialogPositiveClick(dialog: DialogFragment, any: Any = Any()) {}

        open fun onDialogNegativeClick(dialog: DialogFragment, any: Any = Any()) {}
        open fun onDismiss(dialog: DialogFragment, any: Any = Any()) {}
    }




}

fun Window.applyGravityStyle(gravity: Int, @StyleRes resId: Int?, width: Int =  ViewGroup.LayoutParams.MATCH_PARENT,
                             height: Int =  ViewGroup.LayoutParams.WRAP_CONTENT, x: Int = 0, y: Int = 0) {
    val attributes = this.attributes
    attributes.gravity = gravity
    attributes.width =  width
    attributes.height = height
    attributes.x = x
    attributes.y = y
    this.attributes = attributes
    resId?.let { this.setWindowAnimations(it) }
}