package com.yjk.eventdemo.xml;

import com.yjk.eventdemo.model.EventData;

import java.io.InputStream;
import java.util.HashMap;

public abstract class XMLParseFactory {
    /**
     * 读取指定的XML文件
     *
     * @param inputStream XML文件输入流
     */
    public abstract void readXML(InputStream inputStream);

    /**
     * 保存XML到指定的文件
     *
     * @param filePath 文件的绝对路径
     */
    public abstract void writeXML(String filePath);

    /**
     * 获取EventData对象列表
     *
     * @return
     */
    public abstract HashMap<String, EventData> getEventData();

    /**
     * 设置EventData对象列表
     */
    public abstract void setEventData(HashMap<String, EventData> bookList);
}
