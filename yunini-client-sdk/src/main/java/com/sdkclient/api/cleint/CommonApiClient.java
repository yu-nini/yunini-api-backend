package com.sdkclient.api.cleint;

import cn.hutool.core.util.RandomUtil;
import com.sdkclient.api.utils.SignCreate;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共的SDK客户端
 */
public class CommonApiClient {

    public static final String LOCAL_URL = "http://localhost:7053/api";
    public String access;
    public String secret;

    public CommonApiClient(String access, String secret) {
        this.access = access;
        this.secret = secret;
    }

    public Map<String,String> setHeader(String body, String access, String secret){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("access",access);
        hashMap.put("random", RandomUtil.randomNumbers(3));
        hashMap.put("body",body);
        hashMap.put("sign", SignCreate.getSign(body,secret));
        hashMap.put("timeStamp",String.valueOf(System.currentTimeMillis() /100));
        return hashMap;
    }
}
