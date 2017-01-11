package com.yjk.eventdemo.xml;

import com.yjk.eventdemo.model.EventData;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

public class SAXParserTool extends XMLParseFactory {

//  private static final String TAG = "SAXParserTool";

    private SAXHandler mHandler = new SAXHandler();

    private HashMap<String, EventData> mEventDataMap;

    @Override
    public void readXML(InputStream inputStream) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(inputStream, mHandler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void writeXML(String filePath) {
        SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance();
        try {
            TransformerHandler handler = factory.newTransformerHandler();
            Transformer transformer = handler.getTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

            FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Result result = new StreamResult(writer);
            handler.setResult(result);

            String uri = "";
            String localName = "";

            handler.startDocument();
            handler.startElement(uri, localName, EventData.EVENTS, null);
            AttributesImpl attrs = new AttributesImpl();
            char[] ch = null;

            Iterator<Map.Entry<String, EventData>> it = mEventDataMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, EventData> entry = it.next();
                EventData eventData = entry.getValue();
                attrs.clear();
                handler.startElement(uri, localName, eventData.EVENT, attrs);

                handler.startElement(uri, localName, eventData.ACTIVITY, null);
                ch = eventData.getActivityName().toCharArray();
                handler.characters(ch, 0, ch.length);
                handler.endElement(uri, localName, eventData.ACTIVITY);

                handler.startElement(uri, localName, eventData.VIEW, null);
                ch = String.valueOf(eventData.getViewContent()).toCharArray();
                handler.characters(ch, 0, ch.length);
                handler.endElement(uri, localName, eventData.VIEW);

                handler.startElement(uri, localName, eventData.NUM, null);
                ch = String.valueOf(eventData.getClickNum()).toCharArray();
                handler.characters(ch, 0, ch.length);
                handler.endElement(uri, localName, eventData.NUM);

                handler.endElement(uri, localName, eventData.EVENT);
            }

            handler.endElement(uri, localName, EventData.EVENTS);
            handler.endDocument();

        } catch (TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    /**
     * 获取EventDataList列表
     *
     * @return
     */
    public HashMap<String, EventData> getEventData() {
        if (mHandler == null) {
            return null;
        }

        return mHandler.getEventDataList();
    }

    /**
     * 设置EventDataList列表
     *
     * @param map
     */
    public void setEventData(HashMap<String, EventData> map) {
        mEventDataMap = new HashMap<>(map);
    }

    class SAXHandler extends DefaultHandler {

        private HashMap<String, EventData> mEventDataList;
        private EventData mEventData;
        private String mTargetName;

        public HashMap<String, EventData> getEventDataList() {
            return mEventDataList;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            mEventDataList = new HashMap<String, EventData>();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if (localName.equals(EventData.EVENTS)) {
                mEventData = new EventData();
            }

            mTargetName = localName;
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            super.endElement(uri, localName, qName);
            if (EventData.EVENTS.equals(localName)) {
                mEventDataList.put(mEventData.toString(), mEventData);
            }

            mTargetName = null;
        }

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            super.characters(ch, start, length);
            if (EventData.ACTIVITY.equals(mTargetName)) {
                mEventData.setActivityName(new String(ch, start, length));
            } else if (EventData.VIEW.equals(mTargetName)) {
                mEventData.setViewContent(new String(ch, start, length));
            } else if (EventData.NUM.equals(mTargetName)) {
                mEventData.setClickNum(Integer.valueOf(new String(ch, start, length)));
            }
        }
    }
}