package liyihuan.app.android.mrouter_compiler;

public interface ProcessorConfig {


    String MROUTER_PACKAGE = "liyihuan.app.android.mrouter_annotation";

    String MROUTER = MROUTER_PACKAGE + ".MRouter";

    // 接收参数的TAG标记
    String OPTIONS = "moduleName"; //接收每个module名称
    String APT_PACKAGE = "packageNameForAPT"; //接收 包名（APT 存放的包名）

    // Activity全类名
    public static final String ACTIVITY_PACKAGE = "android.app.Activity";

    String MROUTER_API = "liyihuan.app.android.mrouter_api";

    // TODO PATH的
    // 生成的PATH文件的类名
    public static final String CLASS_PATH = "MRouter$$PATH$$";

    // 生成的PATH方法名
    public static final String FUN_PATH = "getPathMap";

    // 生成的PATH方法的返回值
    public static final String RETURN_PATH = "pathMap";

    public static final String API_PATH = MROUTER_API + ".MRouterPath";

    // TODO GROUP的
    // 生成的GROUP文件的类名
    public static final String CLASS_GROUP = "MRouter$$GROUP$$";

    // 生成的GROUP方法名
    public static final String FUN_GROUP = "getGroupMap";

    // 生成的GROUP方法的返回值
    public static final String RETURN_GROUP = "groupMap";

    public static final String API_GROUP = MROUTER_API + ".MRouterGroup";
}