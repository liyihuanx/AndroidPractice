package liyihuan.app.android.mrouter_api;

import java.util.Map;

/**
 * @ClassName: MRouterGroup
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/1/25 21:33
 */
public interface MRouterGroup {

    Map<String, Class<? extends MRouterPath>> getGroupMap();

}
