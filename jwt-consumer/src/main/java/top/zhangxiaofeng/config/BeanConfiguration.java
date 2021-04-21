package top.zhangxiaofeng.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class BeanConfiguration {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    /**
     * 此@LoadBalanced注解是从eureka拿服务，在请求的时候直接访问[服务名](localhost:8081)
     * 没有这个注解不经过eureka,直接访问ip：port
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(tokenInterceptor));
        return restTemplate;
    }

}
