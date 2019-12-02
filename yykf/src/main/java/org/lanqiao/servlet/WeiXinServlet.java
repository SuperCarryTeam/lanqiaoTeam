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
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        PrintWriter out = resp.getWriter();

        //将xml转换为map
        try {
            String message=null;
            Map<String, String> map = MessageUtils.xml2Map(req);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");

            System.out.println(map);
            if(MessageUtils.MESSAGE_EVENT.equals(msgType)){
                //事件
                String event = map.get("Event");
                if(MessageUtils.MESSAGE_EVENT_SUBSCRIBE.equals(event)){
                    //订阅事件
                    message = MessageUtils.getMessage(fromUserName,toUserName,MessageUtils.subscribeText());
                }else if(MessageUtils.MESSAGE_EVENT_CLICK.equalsIgnoreCase(event)){
                    //点击菜单
                    String  eventKey = map.get("EventKey");
                    if(eventKey.equals("32")){
                        //点击客服热线
                        message = MessageUtils.getMessage(fromUserName,toUserName,"如有【公众号】商务合作，请发邮件至：\n" +

                                "1937489099@qq.com，邮件注明商务合作与介绍。我们看到后会第一时间联系您。");
                            System.out.println(message);
                    }
                }
            }

            out.write(message);
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally {
            out.close();
        }
    }
}
