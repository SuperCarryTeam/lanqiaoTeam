package org.lanqiao.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.lanqiao.config.APP;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class BaseUtils {
    public static String verifyCode(){
        String str = "";
        char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        Random random = new Random();
        for (int i = 0; i <4; i++){
            char num = ch[random.nextInt(ch.length)];
            str += num;
        }
        return str;
    }

    /*
        发送短信
     */
    public static boolean sendSMS(String phoneNum , String code){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", APP.AccessKeyID, APP.AccessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(APP.SMSURL);
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", APP.APPNAME);
        request.putQueryParameter("TemplateCode", APP.SMSCODE);
        request.putQueryParameter("TemplateParam", "{code:"+code+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = (Map<String, Object>)mapper.readValue(response.getData(), new TypeReference<Map<String, Object>>() {});

            String message = (String) map.get("Message");
            if("OK".equals(message)){
                System.out.println("发送成功");
                return true;
            }else{
                return false;
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
