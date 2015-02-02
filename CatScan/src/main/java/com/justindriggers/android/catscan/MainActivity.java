package com.justindriggers.android.catscan;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private LogEntityAdapter adapter;
    private List<LogEntity> logs;
    private LogReaderTask logReaderTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(android.R.id.list);
        listView.setOnItemClickListener(this);

        logs = new ArrayList<>();
        adapter = new LogEntityAdapter(this, logs);

        listView.setAdapter(adapter);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        logReaderTask = new LogReaderTask();
        logReaderTask.execute();
    }

    @Override
    protected void onDestroy() {
        logReaderTask.stopTask();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {

    }

    private class LogReaderTask extends AsyncTask<Void, String, Void> {
        private final String[] LOGCAT_CMD = new String[]{"su", "-c", "logcat"};
        private final int BUFFER_SIZE = 1024;

        private boolean isRunning = false;
        private Process logProcess;
        private BufferedReader reader;
        private String line;

        @Override
        protected Void doInBackground(Void... params) {
            isRunning = true;

            try {
                logProcess = Runtime.getRuntime().exec(LOGCAT_CMD);
            } catch (IOException e) {
                e.printStackTrace();
                isRunning = false;
            }

            try {
                reader = new BufferedReader(new InputStreamReader(logProcess.getInputStream()), BUFFER_SIZE);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                isRunning = false;
            }

            line = null;

            try {
                while (isRunning) {
                    line = reader.readLine();
                    publishProgress(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                isRunning = false;
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if (values.length > 0 && values[0] != null && !values[0].isEmpty())
                adapter.add(new LogEntity(values[0]));
        }

        public void stopTask() {
            isRunning = false;
            logProcess.destroy();
        }
    }
}
