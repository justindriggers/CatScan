package com.justindriggers.android.catscan;

import android.os.AsyncTask;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogReaderTask extends AsyncTask<Void, String, Void> {

    private static final String[] LOGCAT_CMD = new String[]{"su", "-c", "logcat"};
    private static final int BUFFER_SIZE = 1024;

    private Handler mHandler;
    private Process mProcess;

    public LogReaderTask(Handler handler) {
        this.mHandler = handler;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            mProcess = Runtime.getRuntime().exec(LOGCAT_CMD);
        } catch(IOException e) {
            publishProgress("F/CatScan requires root to run properly.");
            e.printStackTrace();
            return null;
        }

        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()), BUFFER_SIZE);
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }

        String line;

        try {
            while((line = bufferedReader.readLine()) != null) {
                publishProgress(line);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        try {
            mProcess.waitFor();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if(mProcess.exitValue() == 1 || mProcess.exitValue() == 255) {
            publishProgress("E/CatScan requires root to run properly.");
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        if(values.length > 0 && values[0] != null && !values[0].isEmpty()) {
            mHandler.dispatchMessage(LogHandler.buildMessage(values[0]));
        }
    }

    public void stopTask() {
        if(mProcess != null) {
            mProcess.destroy();
        }
    }
}
