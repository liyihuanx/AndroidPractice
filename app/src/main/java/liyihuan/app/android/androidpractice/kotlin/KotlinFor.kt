package liyihuan.app.android.androidpractice.kotlin

/**
 * @author created by liyihuanx
 * @date 2021/3/2
 * description: 类的描述
 */
class KotlinFor {

}

fun main() {
    val luckyNumbers = intArrayOf(1,2,3,4,5,6,7,8,9,10)

    luckyNumbers.forEach {
        if (it == 5) return
        println(it)
    }




    for (i in luckyNumbers){
        if (i == 5) break
        println(i)
    }
    println("---- ----")
}