package liyihuan.app.android.androidpractice.http

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * @ClassName: DefaultConverterFactory
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/19 22:15
 */
//class DefaultConverterFactory(gson: Gson) : Converter.Factory() {
//
//    private val mGson = gson
//
//    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
//
//        val adapter: TypeAdapter<*> = mGson.getAdapter(TypeToken.get(type))
//        return GsonResponseBodyConverter<Any>(mGson, type, adapter)
//    }
//
//    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody>? {
//        val adapter = mGson.getAdapter(TypeToken.get(type))
//        return GsonRequestBodyConverter<Any>(mGson, adapter)
//
//    }
//
//}
//
///**
// * 把数据转换成自己想要的格式
// */
//class GsonResponseBodyConverter<T> : Converter<ResponseBody, T>{
//    override fun convert(value: ResponseBody): T {
//        TODO("Not yet implemented")
//    }
//
//}
//
///**
// * 把自己的数据转换成 请求的格式
// */
//class GsonRequestBodyConverter<T> : Converter<T, RequestBody>{
//    override fun convert(value: T): RequestBody {
//        TODO("Not yet implemented")
//    }
//}