package com.sdkclient.api.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 获取签名
 */
public class SignCreate {
    public static String getSign(String body,String secret){
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String digestHex = md5.digestHex(body+"."+secret);
        return digestHex;
    }
}
