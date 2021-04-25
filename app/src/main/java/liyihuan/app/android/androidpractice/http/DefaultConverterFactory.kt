package liyihuan.app.android.androidpractice.http

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.nio.charset.Charset

/**
 * @ClassName: DefaultConverterFactory
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/19 22:15
 */
class DefaultConverterFactory(gson: Gson) : Converter.Factory() {

    // 模仿GsonResponseBodyConverter写的,可以在responseBodyConverter返回自己想要的格式
    private val mGson = gson

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        val adapter = mGson.getAdapter(TypeToken.get(type))
        return GsonResponseBodyConverter<Any>(mGson, type, adapter)
    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody>? {
        val adapter = mGson.getAdapter(TypeToken.get(type))
        return GsonRequestBodyConverter<Any>(mGson, adapter)

    }

}

/**
 * 把数据转换成自己想要的格式
 */
class GsonResponseBodyConverter<T>(gson: Gson, type: Type, adapter: TypeAdapter<*>) : Converter<ResponseBody, T> {

    private val mGson = gson
    private val type = type
    private val adapter = adapter

    override fun convert(value: ResponseBody): T {
        TODO("Not yet implemented")
    }

}

/**
 * 把自己的数据转换成 请求的格式
 */
class GsonRequestBodyConverter<T>(gson: Gson, adapter: TypeAdapter<*>) : Converter<T, RequestBody>{
    private val MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8")
    private val UTF_8 = Charset.forName("UTF-8")

    private val gson = gson
    private val adapter = adapter

    override fun convert(value: T): RequestBody {
        TODO("Not yet implemented")
    }
}