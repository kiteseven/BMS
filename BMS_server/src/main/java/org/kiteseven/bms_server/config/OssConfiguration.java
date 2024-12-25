package org.kiteseven.bms_server.config;



import org.kiteseven.bms_common.properties.AliOssProperties;
import org.kiteseven.bms_common.utils.AliOssUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        return new AliOssUtil(aliOssProperties.getEndpoint(),aliOssProperties.getAccessKeyId()
        ,aliOssProperties.getAccessKeySecret(),aliOssProperties.getBucketName());
    }
}
