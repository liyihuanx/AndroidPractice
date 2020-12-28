package com.example.danmu.newdanmu.model.channel;

import com.example.danmu.newdanmu.control.dispatcher.IDanMuDispatcher;
import com.example.danmu.newdanmu.control.speed.SpeedController;
import com.example.danmu.newdanmu.model.DanMuModel;

import java.util.List;

/**
 * Created by android_ls on 2016/12/7.
 */
interface IDanMuPoolManager {
    void setSpeedController(SpeedController speedController);

    void addDanMuView(int index, DanMuModel iDanMuView);

    void jumpQueue(List<DanMuModel> danMuViews);

    void divide(int width, int height);

    void setDispatcher(IDanMuDispatcher iDanMuDispatcher);

    void hide(boolean hide);

    void hideAll(boolean hideAll);

    void startEngine();
}
