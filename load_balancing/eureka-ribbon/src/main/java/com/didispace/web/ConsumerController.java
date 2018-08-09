package com.didispace.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    RestTemplate restTemplate;

   /**
    *  http://service-A/add?a=10&b=20
    *  这里没有采用ip+端口的形式，因为Spring Cloud Ribbon有一个拦截器
    *  会自动去匹配service-A这个服务实例
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return restTemplate.getForEntity("http://service-A/add?a=10&b=20", String.class).getBody();
    }


}