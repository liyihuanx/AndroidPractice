package com.example.danmu.newdanmu.control.dispatcher;

import com.example.danmu.newdanmu.model.DanMuModel;
import com.example.danmu.newdanmu.model.channel.DanMuChannel;

/**
 * Created by android_ls on 2016/12/7.
 */
public interface IDanMuDispatcher {

    void dispatch(DanMuModel iDanMuView, DanMuChannel[] danMuChannels, int position);

}
