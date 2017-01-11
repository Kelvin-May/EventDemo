package com.yjk.eventdemo.model;

public class EventData {

    public static final String EVENTS = "events";
    public static final String EVENT = "event";
    public static final String ACTIVITY = "activityName";
    public static final String VIEW = "view";
    public static final String NUM = "num";

    private String activityName;

    private String viewContent;

    private int clickNum = 0;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getViewContent() {
        return viewContent;
    }

    public void setViewContent(String viewContent) {
        this.viewContent = viewContent;
    }

    public int getClickNum() {
        return clickNum;
    }

    public void setClickNum(int clickNum) {
        this.clickNum = clickNum;
    }

    public void addNum() {
        this.clickNum++;
    }

    @Override
    public String toString() {
//        return super.toString();
        return activityName + viewContent;
    }
}
