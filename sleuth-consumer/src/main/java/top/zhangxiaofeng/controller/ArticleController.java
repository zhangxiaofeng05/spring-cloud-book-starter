package top.zhangxiaofeng.controller;

import brave.Tracer;
import com.netflix.discovery.EurekaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ArticleController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    Tracer tracer;

    @GetMapping("/article/callHello")
    public String callHello() {
        String res = restTemplate.getForObject("http://sleuth-provider/user/hello", String.class);
        logger.info("ArticleController /article/callHello res:{}", res);
        tracer.currentSpan().tag("用户", "张晓峰");
        return res;
    }

    /**
     * 通过EurekaClient取provider的信息
     *
     * @return
     */
    @GetMapping("/article/infos")
    public Object serviceUrl() {
        return eurekaClient.getInstancesByVipAddress("sleuth-consumer", false);
    }

    /**
     * 除了上边通过EurekaClient取provider的信息
     * 还可以通过Spring Cloud封装的
     *
     * @return
     */
    @GetMapping("/article/infoCloud")
    public Object serviceUrlCloud() {
        return discoveryClient.getInstances("sleuth-consumer");
    }

}
