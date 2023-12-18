package com.sdkclient.api.cleint;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yunini.apicommon.model.entity.User;

import java.util.HashMap;

/**
 * 测试客户端SDK服务
 */
public class YuniniApiClient extends CommonApiClient {


    public YuniniApiClient(String access, String secret) {
        super(access,secret);
    }


    public String getName(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        String result3= HttpUtil.get(LOCAL_URL+"/user/", paramMap);
        System.out.println(result3);
        return "GET 获得的名称为"+name;
    }

    public String postName(String name){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result= HttpUtil.post(LOCAL_URL+"/user/", paramMap);
        return "POST 获得的名字为"+name;
    }
    public String postByName(User user){
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(LOCAL_URL+"/user/name")
                .addHeaders(setHeader(json,access,secret))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return "";
    }
}
