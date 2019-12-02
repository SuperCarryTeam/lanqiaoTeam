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
