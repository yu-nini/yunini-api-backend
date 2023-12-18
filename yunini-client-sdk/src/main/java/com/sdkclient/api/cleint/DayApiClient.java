package com.sdkclient.api.cleint;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yunini.apicommon.model.entity.User;

import java.util.HashMap;

/**
 * 获取日期客户端SDK服务
 */
public class DayApiClient extends CommonApiClient {


    public DayApiClient(String access, String secret) {
        super(access,secret);
    }

    public String getDate(User user){
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(LOCAL_URL+"/day/date")
                .addHeaders(setHeader(json,access,secret))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return "";
    }
}
