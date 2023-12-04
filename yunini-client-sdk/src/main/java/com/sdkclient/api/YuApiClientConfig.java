package com.sdkclient.api;

import com.sdkclient.api.cleint.YuniniApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Data
@ComponentScan
@Configuration
@ConfigurationProperties("yunini.client")
public class YuApiClientConfig {
    private String access;
    private String secret;
    @Bean
    public YuniniApiClient yuniniApiClient(){
        return new YuniniApiClient(access,secret);
    }
}
