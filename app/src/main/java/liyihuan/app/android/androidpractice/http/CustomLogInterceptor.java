package liyihuan.app.android.androidpractice.http;


import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class CustomLogInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        ResponseBody body = response.body();
        String bodyString = body.string();
        Log.v("Http_Request", "intercept: " + String.format("result\nUrl=%s\nbody=%s", request.url(), bodyString));
//        response.newBuilder()
//                .headers(response.headers())
//                .body(ResponseBody.create(body.contentType(), bodyString))
//                .build();
        return response;
    }
}