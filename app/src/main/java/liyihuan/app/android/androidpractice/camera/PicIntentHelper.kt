package liyihuan.app.android.androidpractice.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException

/**
 * @ClassName: PicIntentHelper
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/6/1 22:01
 */
object PicIntentHelper {


    var PROVIDER_KEY = "xxx.android7.fileProvider"

    val originPath: String
        get() = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
                + File.separator + "origin_" + System.currentTimeMillis() + ".jpg")


    val cameraFilePath: String
        get() = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
                + File.separator + System.currentTimeMillis() + ".jpg")

    val cropFilePath: String
        get() = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
                + File.separator + "crop_" + System.currentTimeMillis() + ".jpg")

    val compressFilePath: String
        get() = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
                + File.separator + "compress_" + System.currentTimeMillis() + ".jpg")

    internal fun init(context: Context) {
        PROVIDER_KEY = context.packageName + ".android7.fileProvider"
    }


    fun pickImageIntent(): Intent {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        return intent
    }

    /**
     * take photo
     * https://developer.android.com/training/camera/photobasics
     */
    fun takePhotoIntent(context: Activity, outputFilePath: String): Intent {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(context.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile(outputFilePath)
                } catch (ex: IOException) {
                    ex.printStackTrace()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            context,
                            PROVIDER_KEY,
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                }
            }
        }
    }

    private fun createImageFile(outputFilePath: String): File? {
        val photoFile = File(outputFilePath)
        if (!photoFile.parentFile.exists()) {
            photoFile.parentFile.mkdirs()
        }
        return photoFile
    }

    /**
     * crop image
     */
    fun cropImageIntent(context: Activity, imageUri: Uri, cropInfo: CropInfo, outPutFilePath: String): Intent {
        val outFile = File(outPutFilePath)
        if (!outFile.parentFile.exists()) {
            outFile.parentFile.mkdirs()
        }
        val outputUri = FileProvider.getUriForFile(context, PROVIDER_KEY, outFile)
        //通过FileProvider创建一个content类型的Uri
        val intent = Intent("com.android.camera.action.CROP")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.setDataAndType(imageUri, "image/*")
        intent.putExtra("crop", "true")
        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX", cropInfo.aspectX)
        intent.putExtra("aspectY", cropInfo.aspectY)
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 600)
        intent.putExtra("outputY", 600)
        //裁剪时是否保留图片的比例
        intent.putExtra("scale", true)
        // 如果没有 intent.putExtra("scaleUpIfNeeded", true);，
        // 图片将保存为裁剪过程中修改的尺寸，添加了该语句后，图片将会放大到设定的裁剪尺寸。
        intent.putExtra("scaleUpIfNeeded", true) // 去黑边   使图片支持缩放
        //是否是圆形裁剪区域，设置了也不一定有效
        //intent.putExtra("circleCrop", true);
        //是否将数据保留在Bitmap中返回
//        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true) // no face detection

        /* https://juejin.im/entry/586dbd798d6d8100586ac8e2
           fix java.lang.SecurityException: Permission Denial: opening provider android.support.v4.content.FileProvider from ProcessRecord{42725078 24872:com.android.camera/u0a14} (pid=24872, uid=10014) that is not exported from uid 10310
         */
        val resInfoList = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        for (resolveInfo in resInfoList) {
            val packageName = resolveInfo.activityInfo.packageName
            context.grantUriPermission(packageName, outputUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
        return intent
    }

}