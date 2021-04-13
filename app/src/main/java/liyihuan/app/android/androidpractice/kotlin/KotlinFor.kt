package liyihuan.app.android.androidpractice.kotlin

/**
 * @author created by liyihuanx
 * @date 2021/3/2
 * description: 类的描述
 */
class KotlinFor {

}

fun main() {
    /**
     *
     */
    val luckyNumbers = intArrayOf(1,2,3,4,5,6,7,8,9,10)
    val list = luckyNumbers.map {
        it + 1
    }.reversed()
    println(list)

    /**
     * 自定义转换
     */
    luckyNumbers.forEach {
        it.convert(object : convertInterface {
            override fun realConvert() {

            }
        })
    }


    /**
     * 累计两种方式
     */
//    val arr1 = intArrayOf(1, 2, 3)
//    val num1 = arr1.fold(2) { product, element ->
//        product * element // 2*1+ 2*2+ 2*3
//    }
//    println(num1)

//    val arr = intArrayOf(1, 2, 4)
//    val total = arr.reduce { product, element ->
//        product * element // 1* 2* 4
//    }
//    println(total)


    /**
     * for 循环
     */
//    luckyNumbers.forEach {
//        if (it == 5) return
//        println(it) // 退出for循环，输出1，2,3,4
//    }
//
//    luckyNumbers.forEach {
//        if (it == 5) return@forEach
//        println(it) // 退出if语句，除了5 都输出
//    }
//
//    for (i in luckyNumbers){
//        if (i == 5) break // 退出for
//        println(i)
//    }

}