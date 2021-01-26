package liyihuan.app.android.mrouter_api;

import java.util.List;
import java.util.Map;

import liyihuan.app.android.mrouter_annotation.RouterBean;

/**
 * @ClassName: MRouterPath
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/1/25 21:33
 */
public interface MRouterPath {

    Map<String, RouterBean> getPathMap();
}
