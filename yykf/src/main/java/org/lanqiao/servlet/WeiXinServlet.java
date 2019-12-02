package org.lanqiao.servlet;

import org.dom4j.DocumentException;
import org.lanqiao.config.APP;
import org.lanqiao.utils.MessageUtils;
import org.lanqiao.utils.NetUtils;
import org.lanqiao.utils.WeiXinUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/WeixinServlet")
public class WeiXinServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String signature=req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");

        //将token、timestamp、nonce三个参数进行字典序排序
        String signature1 = WeiXinUtils.checkSignature(APP.WXTOKEN, timestamp, nonce);

        if(signature1.equals(signature)){
            resp.getWriter().print(echostr);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("textml;charset=utf-8");
        PrintWriter out = resp.getWriter();

        //将xml转换为map
        try {
            String message=null;
            Map<String, String> map = MessageUtils.xml2Map(req);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");

            if(MessageUtils.MESSAGE_EVENT.equals(msgType)){
                //事件
                String event = map.get("Event");
                if(MessageUtils.MESSAGE_EVENT_SUBSCRIBE.equals(event)){
                    //订阅事件
                    message = MessageUtils.getMessage(fromUserName,toUserName,MessageUtils.subscribeText());
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
