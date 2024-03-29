package liyihuan.app.android.androidpractice.star;


public class Utils {
    // 映射到下一个域
    public static double mapValueFromRangeToRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return toLow + ((value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow));
    }

    // 中间值, value<low, 返回low, value>high, 返回high.
    public static double clamp(double value, double low, double high) {
        return Math.min(Math.max(value, low), high);
    }
}
