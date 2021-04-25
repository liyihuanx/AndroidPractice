package liyihuan.app.android.androidpractice.http;

import liyihuan.app.android.androidpractice.datasource.Utils;

/**
 * @ClassName: BaseRepository
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/21 21:55
 */
public abstract class BaseRepository<T> {

    protected T apiService;

    public BaseRepository() {
        apiService = Renovace.create((Class<? extends T>) Utils.findNeedType(getClass()));
    }

}
