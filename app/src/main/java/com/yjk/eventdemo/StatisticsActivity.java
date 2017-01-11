package com.yjk.eventdemo;

import android.os.Bundle;
import android.widget.ListView;

import com.yjk.eventdemo.adapter.EventsAdapter;
import com.yjk.eventdemo.base.BaseActivity;

public class StatisticsActivity extends BaseActivity {

    private EventsAdapter mAdapter;

    private ListView mListView;

    private EventManager mEventManager = EventManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        initUI();
        initData();
    }

    private void initUI() {
        mListView = (ListView) findViewById(R.id.id_statistics_activity_list_view);
    }

    private void initData() {
        mAdapter = new EventsAdapter(this);
        mAdapter.setData(mEventManager.getData());
        mListView.setAdapter(mAdapter);
    }
}
