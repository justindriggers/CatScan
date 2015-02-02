package com.justindriggers.android.catscan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;

public class LogHandler extends Handler {

    public static final String PAYLOAD_KEY = "payload";

    private ArrayAdapter<LogEntity> mAdapter;

    public LogHandler(ArrayAdapter<LogEntity> adapter) {
        this.mAdapter = adapter;
    }

    public static Message buildMessage(String payload) {
        Message result = new Message();
        Bundle bundle = new Bundle();
        bundle.putString(LogHandler.PAYLOAD_KEY, payload);
        result.setData(bundle);
        return result;
    }

    @Override
    public void handleMessage(Message msg) {
        String payload = msg.getData().getString(PAYLOAD_KEY);

        if(payload != null) {
            mAdapter.add(new LogEntity(payload));
        }
    }
}
