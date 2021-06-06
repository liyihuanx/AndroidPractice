package liyihuan.app.android.androidpractice.camera

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * @ClassName: PhotoRequestFragment
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/5/31 22:28
 */
class PhotoRequestFragment : Fragment() {

    var callback: PicCallback? = null
    var cropInfo: CropInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Destroy生命周期并不会执行
//        this.retainInstance = true
    }


    private fun isNeedCrop(): Boolean {
        return cropInfo != null
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            PictureHelper.REQUEST_CODE_CHOOSE_LOCAL -> {
                if (data?.data != null) {
                    if (isNeedCrop()) {

                    } else {
                        val originFileData =
                                ContentUriUtil.getDataFromUri(context!!, data.data!!, ContentUriUtil.ContentType.image)
                        originFileData?.let {

                            ImageCompression(context!!)
                                    .setOutputFilePath(PicIntentHelper.compressFilePath)
                                    .setCompressCallback(object : PicCallback {
                                        override fun getPicSucc(pathList: MutableList<String>) {
                                            callback?.getPicSucc(pathList)
                                        }

                                    }).execute(it)
                        }
                    }
                }
            }
            PictureHelper.REQUEST_CODE_CAMERA -> {

            }
            PictureHelper.REQUEST_CODE_CROP -> {

            }
        }


    }

}