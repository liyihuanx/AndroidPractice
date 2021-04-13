package liyihuan.app.android.mrouter_api;

import android.app.Activity;
import android.content.Context;
import android.util.LruCache;

/**
 * @ClassName: ParameterManager
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/3/22 21:25
 */
public class ParameterManager {
    public static ParameterManager instance;

    public static ParameterManager getInstance() {
        if (instance == null) {
            synchronized (ParameterManager.class) {
                if (instance == null) {
                    instance = new ParameterManager();
                }
            }
        }
        return instance;
    }

    public static final String FINAL_TITLE = "$$Parameter";
    private LruCache<String, ParameterGet> parameterCache;

    public ParameterManager() {
        parameterCache = new LruCache<>(100);
    }

    /**
     *
     * @param activity
     */
    public void register(Activity activity) {
        String className = activity.getClass().getName(); // className == MainActivity
        String finalName = className + FINAL_TITLE; // MainActivity$$Parameter

        ParameterGet parameterGet = parameterCache.get(finalName);
        try {
            if (parameterGet == null) {
                Class<?> clazz = Class.forName(finalName);
                parameterGet = (ParameterGet) clazz.newInstance();
                parameterCache.put(finalName,parameterGet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        parameterGet.getParameter(activity);

    }

}
