package org.lanqiao.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
/*
    微信消息解析类
 */
public class MessageUtils {
    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_EVENT_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_INEWS = "news";
    public static final String MESSAGE_MUSIC = "music";
    public static final String MESSAGE_EVENT_CLICK= "click";
    public static final String MESSAGE_EVENT_VIEW= "view";
    public static final String MESSAGE_EVENT_SCANCODE_PUSH= "scancode_push";
    public static final String MESSAGE_EVENT_LOCATION_SELECT= "location_select";
    public static final String MESSAGE_EVENT_LOCATION= "location";
    public static final String MESSAGE_EVENT_SCAN= "SCAN";

    public static Map<String , String> xml2Map(HttpServletRequest request) throws IOException, DocumentException {
        Map<String , String> map = new HashMap<>();
        ServletInputStream inputStream = request.getInputStream();
        //创建saxreader对象解析xml
        SAXReader reader = new SAXReader();
        //读取xml文件
        Document document = reader.read(inputStream);
        //获取根节点---xml
        Element root = document.getRootElement();
        //获取根节点里的子节点
        List<Element> elements = root.elements();

        for(Element e : elements){
            map.put(e.getName() , e.getText());
        }
        return map;
    }


}
