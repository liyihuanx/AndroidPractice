package liyihuan.app.android.androidpractice.aop



/**
 * @author liyihuan
 * @date 2022/01/13
 * @Description
 */
@Target(AnnotationTarget.FUNCTION,AnnotationTarget.PROPERTY_GETTER,AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class LogBehavior(val value: String)
