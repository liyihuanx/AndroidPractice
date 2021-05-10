package liyihuan.app.android.androidpractice.refresh;

public interface IEmptyView {
    public static final int HIDE_LAYOUT = 3;
    public static final int NETWORK_ERROR = 1;
    public static final int NODATA = 2;
    public static final int NODATA_ENABLE_CLICK = 4;


    void setErrorType(int type);
}
