package com.justindriggers.android.catscan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

public class LogHandler extends Handler {

    public static final String PAYLOAD_KEY = "payload";

    private List<LogEntity> mCachedEntities;
    private LogEntityAdapter mAdapter;

    public LogHandler() {
        mCachedEntities = new ArrayList<>();
    }

    @Override
    public void handleMessage(Message msg) {
        String payload = msg.getData().getString(PAYLOAD_KEY);

        if(payload != null) {
            LogEntity entity = new LogEntity(payload);

            if (mAdapter != null) {
                mAdapter.add(entity);
            } else {
                mCachedEntities.add(entity);
            }
        }
    }

    public void setAdapter(LogEntityAdapter adapter) {
        adapter.addAll(mCachedEntities);
        this.mAdapter = adapter;
        mCachedEntities.clear();
    }

    public static Message buildMessage(String payload) {
        Message result = new Message();
        Bundle bundle = new Bundle();
        bundle.putString(LogHandler.PAYLOAD_KEY, payload);
        result.setData(bundle);
        return result;
    }
}
