package liyihuan.app.android.mrouter_compiler;

public interface ProcessorConfig {


    String MROUTER_PACKAGE = "liyihuan.app.android.mrouter_annotation.MRouter";

    // 接收参数的TAG标记
    String OPTIONS = "moduleName"; //接收每个module名称
    String APT_PACKAGE = "packageNameForAPT"; //接收 包名（APT 存放的包名）

    // Activity全类名
    public static final String ACTIVITY_PACKAGE = "android.app.Activity";
}