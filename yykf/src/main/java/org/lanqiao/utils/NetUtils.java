package org.lanqiao.utils;

import java.io.*;
import java.net.*;
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
}
