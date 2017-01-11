package com.yjk.eventdemo.base;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.kelvin.eclibrary.BaseEventActivity;
import com.yjk.eventdemo.BuildConfig;
import com.yjk.eventdemo.service.EventCollectService;

public class BaseActivity extends BaseEventActivity {
    @Override
    protected final void onViewClickEvent(View view) {
        final CharSequence content = view.getContentDescription();
        if (!TextUtils.isEmpty(content)) {
            if (BuildConfig.DEBUG) {
                Toast.makeText(this, "点击了" + content, Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(this, EventCollectService.class);
            intent.putExtra(EventCollectService.INTENT_KEY_CONTENT, content);
            intent.putExtra(EventCollectService.INTENT_KEY_ACTIVITY, this.getClass().getName());
            startService(intent);
        }
    }
}
