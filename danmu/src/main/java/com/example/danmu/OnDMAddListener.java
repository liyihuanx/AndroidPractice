package com.example.danmu;


import com.example.danmu.entity.BaseDmEntity;

public interface OnDMAddListener {
    void added(BaseDmEntity dmEntity);
    void addedAll();
}
