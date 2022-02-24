package liyihuan.app.android.androidpractice.dialogfragment

import androidx.lifecycle.ViewModel

/**
 * @author liyihuan
 * @date 2022/01/20
 * @Description
 */
class TestViewModel : ViewModel() {

    val test by lazy { LiveEvent<Boolean>() }


    fun test(){
        test.value = true
    }
}