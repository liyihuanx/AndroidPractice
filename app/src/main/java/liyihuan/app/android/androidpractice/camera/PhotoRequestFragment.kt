package liyihuan.app.android.androidpractice.camera

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import java.io.File

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

    var mTempFilePath: String? = null
    var mCameraFilePath: String? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            PictureHelper.REQUEST_CODE_CHOOSE_LOCAL -> {
                if (data?.data != null) {
                    if (isNeedCrop()) {
                        val filePath = ContentUriUtil.getDataFromUri(
                                context!!,
                                data.data!!,
                                ContentUriUtil.ContentType.image
                        )
                        val imageUri = FileProvider.getUriForFile(
                                context!!,
                                PicIntentHelper.PROVIDER_KEY,
                                File(filePath!!)
                        )
                        mTempFilePath = PicIntentHelper.cropFilePath

                        val intent = PicIntentHelper.cropImageIntent(
                                activity!!,
                                imageUri!!,
                                cropInfo!!,
                                mTempFilePath!!
                        )
                        startActivityForResult(intent, PictureHelper.REQUEST_CODE_CROP)
                    } else {
                        ContentUriUtil.getDataFromUri(
                                context!!,
                                data.data!!,
                                ContentUriUtil.ContentType.image
                        )?.let {
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
                if (isNeedCrop()) {
                    val imageUri = FileProvider.getUriForFile(
                            context!!,
                            PicIntentHelper.PROVIDER_KEY,
                            File(mCameraFilePath!!)
                    )
                    mTempFilePath = PicIntentHelper.cropFilePath
                    val intent = PicIntentHelper.cropImageIntent(
                            activity!!,
                            imageUri,
                            cropInfo!!,
                            mTempFilePath!!
                    )
                    Log.d("QWER", "isNeedCrop: ${PicIntentHelper.cameraFilePath}")
                    Log.d("QWER", "isNeedCrop - mCameraFilePath: $mCameraFilePath")

                    startActivityForResult(intent, PictureHelper.REQUEST_CODE_CROP)
                } else {
                    Log.d("QWER", "Normal: ${PicIntentHelper.cameraFilePath}")
                    Log.d("QWER", "Normal - mCameraFilePath: $mCameraFilePath")

                    ImageCompression(context!!)
                            .setOutputFilePath(PicIntentHelper.compressFilePath)
                            .setCompressCallback(object : PicCallback {
                                override fun getPicSucc(pathList: MutableList<String>) {
                                    callback?.getPicSucc(pathList)
                                }
                            }).execute(mCameraFilePath)
                }

            }
            PictureHelper.REQUEST_CODE_CROP -> {
                ImageCompression(context!!)
                        .setOutputFilePath(PicIntentHelper.compressFilePath)
                        .setCompressCallback(object : PicCallback {
                            override fun getPicSucc(pathList: MutableList<String>) {
                                callback?.getPicSucc(pathList)
                            }
                        }).execute(mTempFilePath)

            }
        }


    }
}