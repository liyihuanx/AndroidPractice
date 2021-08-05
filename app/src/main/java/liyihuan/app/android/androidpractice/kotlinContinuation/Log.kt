package liyihuan.app.android.androidpractice.kotlinContinuation

import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

val dateFormat = SimpleDateFormat("HH:mm:ss:SSS")

val now = {
    dateFormat.format(Date(System.currentTimeMillis()))
}

fun log(vararg msg: Any?) = println("${now()} [${Thread.currentThread().name}] ${msg.joinToString(" ")}")

fun stackTrace(){
    Throwable().printStackTrace(System.out)
}

fun <T> Continuation<T>.log(vararg msg: Any?) = context.log(*msg)

fun CoroutineContext.log(vararg msg: Any?) = println("${now()} [${Thread.currentThread().name} ${this[Job]}] ${msg.joinToString(" ")}")