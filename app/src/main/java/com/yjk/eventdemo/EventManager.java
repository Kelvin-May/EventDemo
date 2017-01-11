package com.yjk.eventdemo;

import android.text.TextUtils;

import com.yjk.eventdemo.base.Singleton;
import com.yjk.eventdemo.model.EventData;
import com.yjk.eventdemo.util.FileUtil;
import com.yjk.eventdemo.xml.SAXParserTool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EventManager {

    private static Singleton<EventManager, Void> sEventManager = new Singleton<EventManager, Void>() {
        @Override
        protected EventManager create(Void var1) {
            return new EventManager();
        }
    };

    public static EventManager getInstance() {
        return sEventManager.get(null);
    }

    private static final String SAVE_PATH = FileUtil.getCachePath() + "/" + "Events.xml";

    private SAXParserTool mSaxParserTool = new SAXParserTool();
    /**
     * 用map更方便查询
     */
    private HashMap<String, EventData> mEventMap;

    private EventManager() {
        try {
            mSaxParserTool.readXML(new FileInputStream(SAVE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mEventMap = mSaxParserTool.getEventData();
        if (null == mEventMap) {
            mEventMap = new HashMap<>();
        }
    }

    /**
     * 添加点击事件统计
     *
     * @param
     */
    public void addClick(String activityName, String viewContent) {
        //  tag 采用activityName + ViewContent的形式
        final String tag = activityName + viewContent;
        if (!TextUtils.isEmpty(tag)) {
            EventData eventData = mEventMap.get(tag);
            if (null == eventData) {
                eventData = new EventData();
                eventData.setActivityName(activityName);
                eventData.setViewContent(viewContent);
                mEventMap.put(tag, eventData);
            }
            eventData.addNum();

            saveData();
        }
    }

    private void saveData() {
        mSaxParserTool.setEventData(mEventMap);
        mSaxParserTool.writeXML(SAVE_PATH);
    }

    public List<EventData> getData() {
        if (null != mEventMap && mEventMap.size() > 0) {
            HashMap<String, EventData> map = new HashMap<>(mEventMap);
            List<EventData> list = new ArrayList<>();
            Iterator<Map.Entry<String, EventData>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, EventData> entry = it.next();
                EventData eventData = entry.getValue();
                list.add(eventData);
            }
            return list;
        }
        return null;
    }
}
