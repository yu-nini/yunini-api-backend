package com.yunini.gateway;

import com.sdkclient.api.cleint.YuniniApiClient;
import com.sdkclient.api.utils.SignCreate;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class CustomerGlobalFilter implements GlobalFilter {
    public static final List<String> LOCAL_HOST = Arrays.asList("127.0.0.1");

    private static final String INTERFACE_HOST = "http://localhost:7053";
    @Resource
    private YuniniApiClient yuniniApiClient;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1、记录请求日志
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = request.getHeaders();
        String id = request.getId();
        RequestPath path = request.getPath();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        String hostString = request.getLocalAddress().getHostString();
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        Flux<DataBuffer> bodyRequest = request.getBody();
        HttpMethod method = request.getMethod();
        log.info("请求来源地址为"+ hostString);
        log.info("请求来源地址为："+remoteAddress);
        log.info("请求地址为："+INTERFACE_HOST+path);
        log.info("请求方法为"+method);
        log.info("请求参数为"+queryParams);
        //2、黑白名单
        if (LOCAL_HOST.equals(hostString)){
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        //3、用户鉴权
        String access = headers.getFirst("access");
        String random = headers.getFirst("random");
        String timestamp = headers.getFirst("timeStamp");
        String body = headers.getFirst("body");
        String sign = headers.getFirst("sign");
        long timeMillis = System.currentTimeMillis()/100;
        if (!"ak".equals(access)){
            throw new RuntimeException("无权限！");
        }
        String realSign = SignCreate.getSign(body, "sk");
        if (!realSign.equals(sign)){
            throw new RuntimeException("无权限！");
        }
        Long time = Long.valueOf(timestamp);
        //不超过一分钟
        if ((timeMillis-time) > 600){
            throw new RuntimeException("已过期！");
        }
        //4、请求的模拟接口是否存在
        //todo 查询数据库接口是否存在
        // todo 是否还有调用次数
        // 5. 请求转发，调用模拟接口 + 响应日志

        return handleResponse(exchange, chain, 1,1);

    }

    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里写数据
                            // 拼接字符串
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        // 7. 调用成功，接口调用次数 + 1 invokeCount
                                        try {
                                            //innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                        } catch (Exception e) {
                                            log.error("invokeCount error", e);
                                        }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8); //data
                                        sb2.append(data);
                                        // 打印日志
                                        log.info("响应结果：" + data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange); // 降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }
}
