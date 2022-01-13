package liyihuan.app.android.androidpractice.aop

import android.util.Log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

/**
 * @author liyihuan
 * @date 2022/01/13
 * @Description
 */
@Aspect
class LogBehaviorAspect {


    /**
     * 找到处理的切点
     * [* *(..)  可以处理这个类所有的方法]
     */
    @Pointcut("execution(@liyihuan.app.android.androidpractice.aop.LogBehavior *  *(..))")
    fun methodAnnotationWithLogBehavior() {

    }

    @Around("methodAnnotationWithLogBehavior()")
    @Throws(Throwable::class)
    fun weaveJoinPointWithLogBehavior(joinPoint: ProceedingJoinPoint): Any {
        // 方法sign
        val methodSignature = joinPoint.signature as MethodSignature
        val className = methodSignature.declaringType.simpleName
        val methodName = methodSignature.name

        val value: String = methodSignature.method.getAnnotation(LogBehavior::class.java).value

        // 执行的方法
        val result = joinPoint.proceed()

        Log.d("QWER", String.format("%s功能：%s类的%s方法执行了 结果为%s",
            value, className, methodName, result.toString()))
        return result
    }
}