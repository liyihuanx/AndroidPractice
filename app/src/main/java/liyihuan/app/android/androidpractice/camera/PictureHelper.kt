package liyihuan.app.android.androidpractice.camera

import android.app.Dialog
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import liyihuan.app.android.androidpractice.R
import java.lang.ref.WeakReference

/**
 * @ClassName: PictureHelper
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/31 21:36
 */

class PictureHelper(activity: FragmentActivity) {
    companion object {
        val REQUEST_CODE_CHOOSE_LOCAL = 11223
        val REQUEST_CODE_CAMERA = 11224
        val REQUEST_CODE_CROP = 11225

        val TAG = "PhotoRequestFragment"
    }

    // 弱引用持有一个activity
    private val activity by lazy { WeakReference(activity) }
    private val photoRequestFragment by lazy { getPhotoRequestFragment(activity) }


    private fun getPhotoRequestFragment(activity: FragmentActivity): PhotoRequestFragment {
        // 查找是否创立过这个fragment
        var findPhotoRequestFragment = findPhotoRequestFragment(activity)
        if (findPhotoRequestFragment == null) {
            findPhotoRequestFragment = PhotoRequestFragment()
            val fragmentManager = activity.supportFragmentManager

            fragmentManager
                    .beginTransaction()
                    .add(findPhotoRequestFragment, TAG)
                    .commitAllowingStateLoss()
            fragmentManager.executePendingTransactions()
        }
        return findPhotoRequestFragment!!
    }

    // 利用tag去找是否已经有该Fragment的实例
    private fun findPhotoRequestFragment(activity: FragmentActivity): PhotoRequestFragment? {
        return activity.fragmentManager.findFragmentByTag(TAG) as PhotoRequestFragment?
    }

    /**
     * 选择相册或者相机的dialog
     */
    fun showOperate(cropInfo: CropInfo? = null, callback: PicCallback) {
        photoRequestFragment.callback = callback
        photoRequestFragment.cropInfo = cropInfo

        val operateDialog = activity.get()?.let { Dialog(it, R.style.BottomViewWhiteMask) }
        val contentView = LayoutInflater.from(activity.get())
                .inflate(R.layout.dialog_choose_pic, null)
        operateDialog?.setCancelable(true)
        operateDialog?.setCanceledOnTouchOutside(true)
        operateDialog?.setContentView(contentView)
        operateDialog?.show()

        val imageChooseListener = View.OnClickListener { v ->
            operateDialog?.dismiss()
            when (v.id) {
                R.id.btnCamera -> {
                    openCamera()
                }
                R.id.btnAlbum -> {
                    openLocalAlbum()
                }
                R.id.btnCancel -> {
                    operateDialog?.dismiss()
                }
            }
        }

        contentView.findViewById<TextView>(R.id.btnCamera).setOnClickListener(imageChooseListener)
        contentView.findViewById<TextView>(R.id.btnAlbum).setOnClickListener(imageChooseListener)
        contentView.findViewById<TextView>(R.id.btnCancel).setOnClickListener(imageChooseListener)

        val attributes = operateDialog?.window?.attributes
        attributes?.width = WindowManager.LayoutParams.MATCH_PARENT
        attributes?.height = WindowManager.LayoutParams.WRAP_CONTENT
        attributes?.gravity = Gravity.BOTTOM
        operateDialog?.window?.attributes = attributes
    }

    private fun openLocalAlbum() {
        activity.get()?.let {
            photoRequestFragment.startActivityForResult(
                    PicIntentHelper.pickImageIntent(),
                    REQUEST_CODE_CHOOSE_LOCAL)
        }
    }

    private fun openCamera() {
        activity.get()?.let {
            val mCameraFilePath = PicIntentHelper.cameraFilePath
            photoRequestFragment.mCameraFilePath = mCameraFilePath
            Log.d("QWER", "openCamera: ${PicIntentHelper.cameraFilePath}")
            photoRequestFragment.startActivityForResult(
                    PicIntentHelper.takePhotoIntent(
                            it, mCameraFilePath
                    ),
                    REQUEST_CODE_CAMERA)
        }
    }

}