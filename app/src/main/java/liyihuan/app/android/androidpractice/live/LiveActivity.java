package liyihuan.app.android.androidpractice.live;

import androidx.appcompat.app.AppCompatActivity;
import liyihuan.app.android.androidpractice.MRouterConfig;
import liyihuan.app.android.androidpractice.R;

import liyihuan.app.android.androidpractice.apt.MRouter$$GROUP$$app;
import liyihuan.app.android.androidpractice.apt.MRouter$$PATH$$app;
import liyihuan.app.android.mrouter_annotation.MRouter;
import liyihuan.app.android.mrouter_annotation.RouterBean;
import liyihuan.app.android.mrouter_api.MRouterPath;

import android.content.Intent;
import android.os.Bundle;

import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.Map;

@MRouter(path = "/app/LiveActivity")
public class LiveActivity extends AppCompatActivity {

    public static final String LicenseKey = "942f2e63c63754cb17f5da87fa50dfd4";
    public static final String LicenseUrl = "http://license.vod2.myqcloud.com/license/v1/46adfbc2f22b4869ab8c18fb69e6d51e/TXLiveSDK.licence";
    public static final String rtmp = "rtmp://127821.livepush.myqcloud.com/AndroidPractice/liyihuanx?txSecret=6905cd12971eedf4a30d65d9bd186b39&txTime=60083FEE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        TXLiveBase.getInstance().setLicence(this, LicenseUrl, LicenseKey);

        TXLivePushConfig mLivePushConfig = new TXLivePushConfig();
        TXLivePusher mLivePusher = new TXLivePusher(this);
        // 一般情况下不需要修改 config 的默认配置
        mLivePusher.setConfig(mLivePushConfig);

        //启动本地摄像头预览
        TXCloudVideoView mPusherView = (TXCloudVideoView) findViewById(R.id.pusher_tx_cloud_view);
//        findViewById(R.id.btn_start).setOnClickListener(v ->{
//            mLivePusher.startCameraPreview(mPusherView);
//            int ret = mLivePusher.startPusher(rtmp.trim());
//            if (ret == -5) {
//                Log.i("QWER", "startRTMPPush: license 校验失败");
//            }
//        } );

        MRouter$$PATH$$app path$$app = new MRouter$$PATH$$app();
        Map<String, RouterBean> pathMap = path$$app.getPathMap();
        // /app/MainActivity,RouterBean

        MRouter$$GROUP$$app group$$app = new MRouter$$GROUP$$app();
        Map<String, Class<? extends MRouterPath>> groupMap = group$$app.getGroupMap();
        Class<? extends MRouterPath> app = groupMap.get("app");
        try {
            MRouter$$PATH$$app mRouterPath = (MRouter$$PATH$$app) app.newInstance();
            Map<String, RouterBean> pathMap1 = mRouterPath.getPathMap();

            findViewById(R.id.btn_start).setOnClickListener(v ->{
                Intent intent = new Intent(this,pathMap1.get(MRouterConfig.module_app.MainActivity).getMyClass());
                startActivity(intent);
            } );
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        // app, MRouter$$PATH$$app
//
//

    }
}