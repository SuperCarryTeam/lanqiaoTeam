package org.lanqiao.utils;

import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.lanqiao.bean.TextMessage;

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

    public static String getMessage(String fromUserName , String toUserName , String content){
        TextMessage message = new TextMessage();

        message.setToUserName(fromUserName);
        message.setFromUserName(toUserName);
        message.setCreateTime(new Date().getTime()+"");
        message.setMsgType(MessageUtils.MESSAGE_TEXT);
        message.setContent(content);

        return MessageUtils.text2XML(message);
    }

    public static String text2XML(TextMessage message){
        XStream xStream = new XStream();
        xStream.alias("xml",TextMessage.class);
        return xStream.toXML(message);
    }

    /*
        订阅时回复用户的内容
     */
    public static String subscribeText(){
        StringBuilder sb = new StringBuilder();
        sb.append("感谢您对本公司的信任与支持\n");
        sb.append("我们将继续坚持信誉至上,为客户选择优质房源\n");
        return sb.toString();
    }

}
