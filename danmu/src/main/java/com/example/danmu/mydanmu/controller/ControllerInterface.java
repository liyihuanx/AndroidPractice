package com.example.danmu.mydanmu.controller;

import android.graphics.Canvas;

import com.example.danmu.mydanmu.bean.DmInfo;

import java.util.ArrayList;

/**
 * @author created by liyihuanx
 * @date 2021/1/4
 * description: 类的描述
 */
public interface ControllerInterface {

    void startThread();

    void addDmList(ArrayList<DmInfo> data);

    void start();

    void pause();

    void resume();

    void destroy();
}
