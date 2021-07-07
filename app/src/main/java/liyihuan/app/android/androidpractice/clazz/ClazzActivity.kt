package liyihuan.app.android.androidpractice.clazz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import liyihuan.app.android.androidpractice.R
import java.util.*

class ClazzActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clazz)

        getClazz(A::class.java)
    }


    fun getClazz(clazz: Class<*>) {
        // TODO 拿到所有方法
//        val methods = clazz.methods
//        methods.forEach {
//            Log.d("QWER", "methods: $it")
//        }

        // TODO 拿到类 输出 [class liyihuan.app.android.androidpractice.clazz.A]
        val forName = Class.forName("liyihuan.app.android.androidpractice.clazz.A")
        Log.d("QWER", "forName: $forName")

        // TODO 拿到类的对象,执行构造函数  输出 [liyihuan.app.android.androidpractice.clazz.A@9194c97]
//        val newInstance = clazz.newInstance()
//        Log.d("QWER", "getClazz: $newInstance")

        // TODO 构造函数方法集合
        val constructors = clazz.constructors
        constructors.forEach {
            Log.d("QWER", "constructors: $it")
        }

        // TODO 显式构造函数
        val declaredConstructors = clazz.declaredConstructors
        Log.d("QWER", "declaredConstructors: ${declaredConstructors.size}")

        declaredConstructors.forEach {
            Log.d("QWER", "declaredConstructors: $it")
        }

        // TODO 输出【liyihuan.app.android.androidpractice.clazz.A】
        val name = clazz.name
        Log.d("QWER", "name: $name")

        val classes = clazz.classes
        classes.forEach {
            val methods = it.methods
            it.methods.forEach {
                Log.d("QWER", "methods: $it")

            }
            Log.d("QWER", "classes: $it")

        }
    }
}


class A @JvmOverloads constructor(var aa: String = "1", var b: String, var c: String) {

    var paA = 1
    var paB = "2"

    public fun methodsA() {

    }

    companion object {
        fun methodsB() {

        }

    }
}