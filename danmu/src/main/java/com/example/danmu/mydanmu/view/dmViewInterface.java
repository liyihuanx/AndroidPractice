package com.example.danmu.mydanmu.view;


import com.example.danmu.mydanmu.bean.DmInfo;
import com.example.danmu.mydanmu.controller.Controller;

import java.util.ArrayList;

/**
 * @author created by liyihuanx
 * @date 2021/1/4
 * description: 类的描述
 */
public interface dmViewInterface {

    void dmItemDraw();

    Controller getController();

    void addDmList(ArrayList<DmInfo> data);

}
