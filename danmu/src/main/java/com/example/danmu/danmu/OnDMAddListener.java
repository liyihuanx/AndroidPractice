package com.example.danmu.danmu;


import com.example.danmu.danmu.entity.BaseDmEntity;

public interface OnDMAddListener {
    void added(BaseDmEntity dmEntity);
    void addedAll();
}
