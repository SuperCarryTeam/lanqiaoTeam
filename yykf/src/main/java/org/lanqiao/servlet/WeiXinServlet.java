package org.lanqiao.servlet;

import org.dom4j.DocumentException;
import org.lanqiao.config.APP;
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
        req.setCharacterEncoding("utf-8");
        resp.setContentType("textml;charset=utf-8");
        PrintWriter out = resp.getWriter();
        String message=null;
        //将xml转换为map
        Map<String, String> map = null;
        try {
            map = NetUtils.xml2Map(req);
            System.out.println("map"+map);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");

            String event = map.get("Event");
            if(NetUtils.MESSAGE_EVENT.equals(map.get("MsgType"))) {
                if (NetUtils.MESSAGE_SUBSCRIBE.equals(event)) {
                    message = NetUtils.getMessage(fromUserName,toUserName,NetUtils.subscribeText());
                }
            }
            out.print(message);


        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
