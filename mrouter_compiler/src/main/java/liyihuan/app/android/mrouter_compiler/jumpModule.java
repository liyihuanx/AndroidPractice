package liyihuan.app.android.mrouter_compiler;

import java.util.HashMap;
import java.util.Map;

import liyihuan.app.android.mrouter_annotation.RouterBean;

/**
 * @author created by liyihuanx
 * @date 2021/1/25
 * description: 类的描述
 */
public class jumpModule {


    // Path模板
//    public class ARouter$$Path$$app implements ARouterPath {
//        @Override
//        public Map<String, RouterBean> getPathMap() {
//            Map<String, RouterBean> pathMap = new HashMap<>();
//            pathMap.put("/app/MainActivity", RouterBean.create(RouterBean.TypeEnum.ACTIVITY, MainActivity.class, "/app/MainActivity", "app"));
//            return pathMap;
//        }
//    }

    // Group模板
//    public class ARouter$$Group$$order implements ARouterGroup {
//        @Override
//        public Map<String, Class<? extends ARouterPath>> getGroupMap() {
//            Map<String, Class<? extends ARouterPath>> groupMap = new HashMap<>();
//            groupMap.put("order", ARouter$$Path$$order.class);
//            return groupMap;
//        }
//    }
    // HashMap<Group,path.class>  ----> 一个Group,对应多个path，一个path对应一个RouterBean
    //  <app,HashMap<MainActivity,info>>
    //  <app,HashMap<Main2Activity,info2>>
    //  <app,HashMap<Main3Activity,info3>>
    //  <order,HashMap<OrderActivity,info>>
    //  <order,HashMap<OrderActivityActivity,info2>>
    //  <order,HashMap<OrderActivityActivity,info3>>

    // path -->
    // 1. result = new HashMap<String,RouterBean> RouterBean?怎么来的，存放什么信息？
    // 2. result.put(path,RouterBean)
    // 3. return result


    // Group
    // 1. result = new HashMap<String,path.class>
    // 2. result.put(group,path.class) -->group?,path.class? 怎么来的
    // 3. return result
}
