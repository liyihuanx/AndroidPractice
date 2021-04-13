package liyihuan.app.android.mrouter_api;

import android.content.Context;
import android.content.Intent;
import android.util.LruCache;

import java.util.Map;

import liyihuan.app.android.mrouter_annotation.RouterBean;

/**
 * @ClassName: RouterManager
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/1/27 21:15
 */
public class RouterManager {
    // 正常的跳转
//    Intent intent = Integer(this,target.class)
//    startActivity(intent)


    // 路由的跳转
    /*MRouter$$PATH$$app path$$app = new MRouter$$PATH$$app();
    Map<String, RouterBean> pathMap = path$$app.getPathMap();
    MRouter$$GROUP$$app group$$app = new MRouter$$GROUP$$app();
    Map<String, Class<? extends MRouterPath>> groupMap = group$$app.getGroupMap();
    Class<? extends MRouterPath> app = groupMap.get("app");
    MRouter$$PATH$$app mRouterPath = (MRouter$$PATH$$app) app.newInstance();
    Map<String, RouterBean> pathMap1 = mRouterPath.getPathMap();
    Intent intent = new Intent(this,pathMap1.get(MRouterConfig.module_app.MainActivity).getMyClass());
    startActivity(intent);*/

    // path，group分别存储了什么东西

    // path --> <"/app/MainActivity",RouterBean>  ---> RouterBean ==> 使用了注解的类的一些信息
    // group --> <"app", "path.class"> 因为一个group对应很多个 path,所以保存path.class
    // 所以只要有 组名(app)--> 组名下的所有path文件 ---> 根据 路径名(/app/MainActivity) ---> 获取对应RouterBean，RouterBean存放着class

    // 所以要跳转到使用了注解的页面，就需传入 group和path

    // 怎样拿到对应的Group和Path对象呢？
    //  apt 生成的 MRouter$$GROUP$$，MRouter$$PATH$$ 都是一样的，拼接起来就是类名，有了类名就可以反射！

    private String group;
    private String path;

    private static final String GROUP_TITLE = "MRouter$$GROUP$$";

    // 性能  LRU缓存
    private LruCache<String, MRouterGroup> groupLruCache;
    private LruCache<String, MRouterPath> pathLruCache;

    // 单例模式
    public static RouterManager instance;

    public static RouterManager getInstance() {
        if (instance == null) {
            synchronized (RouterManager.class) {
                if (instance == null) {
                    instance = new RouterManager();
                }
            }
        }
        return instance;
    }

    public RouterManager() {
        groupLruCache = new LruCache<>(100);
        pathLruCache = new LruCache<>(100);
    }



    public Object openUrl(Context context, BundleManager bundleManager) {

        try {
            // 拼接成完成的apt生成的GROUP对象  liyihuan.app.android.androidpractice
            String groupClassName = context.getPackageName() + "." + GROUP_TITLE + group;

            MRouterGroup mRouterGroup = groupLruCache.get(groupClassName);
            // 列表没有对应的，用反射生成
            if (mRouterGroup == null) {
                Class<?> aClass = Class.forName(groupClassName);

                // 初始化类文件
                mRouterGroup = (MRouterGroup) aClass.newInstance();

                // 保存到缓存
                groupLruCache.put(group, mRouterGroup);
            }

            // 拿到了Group 找到对应的path
            MRouterPath mRouterPath = pathLruCache.get(path);
            if (mRouterPath == null) {
                Class<? extends MRouterPath> clazz = mRouterGroup.getGroupMap().get(group);
                mRouterPath = clazz.newInstance();
                pathLruCache.put(path, mRouterPath);
            }
            RouterBean routerBean = mRouterPath.getPathMap().get(path);
            switch (routerBean.getTypeEnum()) {
                case ACTIVITY:
                    Class<?> myClass = routerBean.getMyClass();
                    Intent intent = new Intent(context, myClass);
                    intent.putExtras(bundleManager.getBundle());
                    context.startActivity(intent, bundleManager.getBundle());
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public BundleManager build(String path) {
        if (!checkPath(path)) {
            throw new IllegalArgumentException("传入的Path路径不对");
        }
        return new BundleManager();
    }

    /**
     * 检查一下传入的path是不是正确的
     *
     * @param path
     * @return false不正确，抛出错误
     */
    private boolean checkPath(String path) {
        // 开头不是 [/] 符号
        if (path.isEmpty() || !path.startsWith("/")) {
            return false;
        }
        // 只有一个 [/] 符号
        if (path.lastIndexOf("/") == 0) {
            return false;
        }

        // 截取Group
        String finalGroup = path.substring(1, path.indexOf("/", 1));

        if (finalGroup.isEmpty()) {
            return false;
        }

        this.group = finalGroup;
        this.path = path;

        return true;
    }

}
