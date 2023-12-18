package com.sdkclient.api.cleint;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yunini.apicommon.model.entity.User;

import java.util.HashMap;

/**
 * 获取随机数客户端SDK服务
 */
public class RandomNumberApiClient extends CommonApiClient {


    public RandomNumberApiClient(String access, String secret) {
        super(access,secret);
    }

    public String getRandomNumber(User user){
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(LOCAL_URL+"/random/number")
                .addHeaders(setHeader(json,access,secret))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return "";
    }
}
