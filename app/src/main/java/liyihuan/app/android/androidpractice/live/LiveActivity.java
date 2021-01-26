package liyihuan.app.android.androidpractice.live;

import androidx.appcompat.app.AppCompatActivity;
import liyihuan.app.android.androidpractice.R;
import liyihuan.app.android.mrouter_annotation.MRouter;

import android.os.Bundle;
import android.util.Log;

import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

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
        findViewById(R.id.btn_start).setOnClickListener(v ->{
            mLivePusher.startCameraPreview(mPusherView);
            int ret = mLivePusher.startPusher(rtmp.trim());
            if (ret == -5) {
                Log.i("QWER", "startRTMPPush: license 校验失败");
            }
        } );
    }
}