package liyihuan.app.android.androidpractice.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * @author created by liyihuanx
 * @date 2020/11/26
 * description: 获取屏幕宽高
 */
public class ScreenUtils {
    // 获取Display
    public static Display getDisplay(Context context){
        WindowManager wm = (WindowManager) (context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE));
        return wm.getDefaultDisplay();
    }

    public static Display getDisplay(Activity activity){
        return activity.getWindowManager().getDefaultDisplay();
    }


    // 通过DisPlay获取应用屏幕宽高:不包括了状态栏(刘海屏，水滴屏等)，导航栏等系统装饰UI所占用的空间
    public static int getScreenWidth(Context context){
        return getDisplay(context).getWidth();
    }

    public static int getNewScreenWidth(Context context){
        Point point=new Point();
        getDisplay(context).getSize(point);
        return point.x;
    }

    public static int getScreenHeight(Context context){
        return getDisplay(context).getHeight();
    }

    public static int getNewScreenHeight(Context context){
        Point point=new Point();
        getDisplay(context).getSize(point);
        return point.y;
    }

    // 获取实际屏幕宽高：包括了状态栏(刘海屏，水滴屏等)，导航栏等系统装饰UI所占用的空间
    public static int getRealScreenWidth(Context context){
        Point point=new Point();
        getDisplay(context).getRealSize(point);
        return point.x;
    }

    public static int getRealScreenHeight(Context context){
        Point point=new Point();
        getDisplay(context).getRealSize(point);
        return point.y;
    }

//    // 获取DisplayMetrics
//    public DisplayMetrics getDisplayMetrics(Context context){
//        DisplayMetrics metrics=new DisplayMetrics();
//        getDisplay(context).getMetrics(metrics);
//        return metrics;
//    }
//    // 应用
//    public int getScreenWidth(Context context){
//        return getDisplayMetrics(context).widthPixels;
//    }
//    // 真实
//    public int getScreenWidth(Context context){
//        DisplayMetrics metrics=new DisplayMetrics();
//        getDisplay(context).getRealMetrics(metrics);
//        return metrics.widthPixels;
//        //return metrics.heightPixels;
//    }
}
