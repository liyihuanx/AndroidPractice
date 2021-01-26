package liyihuan.app.android.androidpractice;

/**
 * @ClassName: MRouterConfig
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/1/26 22:29
 */
public class MRouterConfig {

    public static class module_app {
        public static String GROUP = "/app/";
        public static String MainActivity = GROUP + "MainActivity";
        public static String LiveActivity = GROUP + "LiveActivity";
    }

    public static class module_order {
        public static String GROUP = "/order/";

    }

    public static class module_personal {
        public static String GROUP = "/persona/";
        public static String MainActivity = GROUP + "MainActivity";
        public static String LiveActivity = GROUP + "LiveActivity";
    }
}
