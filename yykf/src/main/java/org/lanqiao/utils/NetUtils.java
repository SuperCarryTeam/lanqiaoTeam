package org.lanqiao.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.lanqiao.bean.AccessToken;
import org.lanqiao.config.APP;
import org.lanqiao.config.NetConfig;
import org.lanqiao.menu.Button;
import org.lanqiao.menu.ClickButton;
import org.lanqiao.menu.Menu;
import org.lanqiao.menu.ViewButton;

import java.io.*;
import java.net.*;
import java.util.Map;

/*
    网络请求工具类
 */
public class NetUtils {

    public static String doGetStr(String urlPath) throws IOException {
        //创建URL对象，建立远程连接
        URL url = new URL(urlPath);
        //返回一个连接，连接后台和微信服务器
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        //请求方式
        urlConnection.setRequestMethod("GET");
        //打开输入流，读取微信服务器返回的数据
        urlConnection.setDoInput(true);
        //因为是get请求，所有关闭输出流，不需要向微信输出
        urlConnection.setDoOutput(false);
        //打开连接
        urlConnection.connect();

        //字节流包装成高效流
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));

        //读取返回的数据
        String len;
        StringBuilder sb = new StringBuilder();
        while((len=br.readLine())!=null){
            sb.append(len);
        }

        return sb.toString();
    }

    public static String doPostStr(String urlPath , String params) throws IOException {
        URL url = new URL(urlPath);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);
        //打开输出流，因为要向微信服务器传递参数
        urlConnection.setDoOutput(true);
        //关闭缓存
        urlConnection.setUseCaches(false);

        //向微信服务器传递参数
        PrintWriter printWriter = new PrintWriter(urlConnection.getOutputStream());
        printWriter.write(params);

        printWriter.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));

        String len;
        StringBuilder sb = new StringBuilder();
        while((len=br.readLine())!=null){
            sb.append(len);
        }

        return sb.toString();
    }

    public static AccessToken getAccessToken(){
        String url = NetConfig.ACCESS_TOKEN_URL.replace("APPID", APP.WXAPPID).replace("APPSECRET", APP.WXAPPSECRET);

        AccessToken token = new AccessToken();
        try {
            String json = doGetStr(url);

            ObjectMapper mapper = new ObjectMapper();
            token = mapper.readValue(json,AccessToken.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return token;
    }

    public static void createMenu(AccessToken accessToken) throws IOException {
        String url = NetConfig.CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken.getAccess_token());

        ViewButton button1 = new ViewButton();
        button1.setType(MessageUtils.MESSAGE_EVENT_CLICK);
        button1.setName("预约看房");
        button1.setUrl("");

        ViewButton button2 = new ViewButton();
        button2.setType(MessageUtils.MESSAGE_EVENT_VIEW);
        button2.setName("我的");
        button2.setUrl("");

        ViewButton button31 = new ViewButton();
        button31.setType(MessageUtils.MESSAGE_EVENT_VIEW);
        button31.setName("优惠券");
        button31.setUrl("");

        ClickButton button32 = new ClickButton();
        button32.setType(MessageUtils.MESSAGE_EVENT_CLICK);
        button32.setName("客服热线");
        button32.setKey("32");

        ClickButton button33 = new ClickButton();
        button33.setType(MessageUtils.MESSAGE_EVENT_CLICK);
        button33.setName("加入我们");
        button33.setKey("33");

        Button button3 = new Button();
        button3.setName("菜单三");
        button3.setSub_button(new Button[]{button31,button32,button33});

        Menu menu = new Menu();

        menu.setButton(new Button[]{button1,button2,button3});

        ObjectMapper mapper = new ObjectMapper();
        String params = mapper.writeValueAsString(menu);
        System.out.println(params);

        //{"errcode":0,"errmsg":"ok"}
        String response = doPostStr(url, params);
        System.out.println(response);
        Map<String, Object> map = (Map<String, Object>)mapper.readValue(response, new TypeReference<Map<String, Object>>() {});

        int errcode = -1;
        errcode = (int) map.get("errcode");

        if(errcode == 0){
            System.out.println("菜单创建成功");
        }else {
            System.out.println("菜单创建失败");
        }
    }
}
