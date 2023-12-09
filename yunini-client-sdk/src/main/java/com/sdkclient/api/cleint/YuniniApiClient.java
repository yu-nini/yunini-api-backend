package com.sdkclient.api.cleint;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.sdkclient.api.utils.SignCreate;
import com.yunini.apicommon.model.entity.User;

import java.util.HashMap;
import java.util.Map;

public class YuniniApiClient {

    public YuniniApiClient(String access, String secret) {
        this.access = access;
        this.secret = secret;
    }

    private String access;
    private String secret;

    public String getName(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name",name);

        String result3= HttpUtil.get("http://localhost:7051/api/user/", paramMap);
        System.out.println(result3);
        return "GET 获得的名称为"+name;
    }

    public String postName(String name){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result= HttpUtil.post("http://localhost:7051/api/user/", paramMap);
        return "POST 获得的名字为"+name;
    }


    public String postByName(User user){
        String json = JSONUtil.toJsonStr(user);
        String result2 = HttpRequest.post("http://localhost:7051/api/user/name")
                .body(json)
                .addHeaders(setHeader(json))
                .execute().body();
        System.out.println(result2);
        return "POSTBYNAME 名字为"+user.getUserName();
    }
    public Map<String,String> setHeader(String body){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("access",access);
        hashMap.put("random", RandomUtil.randomNumbers(3));
        hashMap.put("body",body);
        hashMap.put("sign", SignCreate.getSign(body,secret));
        hashMap.put("timeStamp",String.valueOf(System.currentTimeMillis() /100));
        return hashMap;
    }
}
