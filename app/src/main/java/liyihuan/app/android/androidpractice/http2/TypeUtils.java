package liyihuan.app.android.androidpractice.http2;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author created by liyihuanx
 * @date 2021/4/15
 * description: 类的描述
 */
public class TypeUtils {
    /**
     * find the type by interfaces
     *
     * @param cls
     * @param <R>
     * @return
     */
    public static <R> Type findNeedType(Class<R> cls) {
        List<Type> typeList = getMethodTypes(cls);
        if (typeList == null || typeList.isEmpty()) {
            return null;
        }
        return typeList.get(0);
    }

    /**
     * MethodHandler
     */
    public static <T> List<Type> getMethodTypes(Class<T> cls) {
        Type typeOri = cls.getGenericSuperclass();
        List<Type> needtypes = null;
        // if Type is T
        if (typeOri instanceof ParameterizedType) {
            needtypes = new ArrayList<>();
            Type[] parentypes = ((ParameterizedType) typeOri).getActualTypeArguments();
            for (Type childtype : parentypes) {
                needtypes.add(childtype);
                if (childtype instanceof ParameterizedType) {
                    Type[] childtypes = ((ParameterizedType) childtype).getActualTypeArguments();
                    for (Type type : childtypes) {
                        needtypes.add(type);
                    }
                }
            }
        }
        return needtypes;
    }
}
