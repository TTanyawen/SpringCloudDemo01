package com.example.service.impl;

import cn.hutool.core.util.IdUtil;
import com.example.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;
@Service
public class PaymentServiceImpl implements PaymentService {
    //成功
    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "   paymentInfo_OK,id：  " + id + "\t" + "哈哈哈";
    }

    //超时降级演示
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")           //5秒钟以内就是正常的业务逻辑
    })
    public String paymentInfo_Timeout(Integer id) {
        int timeNumber = 15;//超过5s，走配置的降级方法，有异常也是走降级方法
//        int i=1/0;  //除0错误
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "   paymentInfo_TimeOut,id：  " + id + "\t" + "呜呜呜" + " 耗时(秒)" + timeNumber;
    }

    //兜底方法，上面方法出问题,我来处理，返回一个出错信息
    public String paymentInfo_TimeoutHandler(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_TimeoutHandler,系统繁忙,请稍后再试\t o(╥﹏╥)o ";
    }


    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),  //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
//当在配置时间（10s）内达到此数量的失败后，打开断路，默认20个,此处设置为10个
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            //断路多久以后开始尝试是否恢复，默认5s,这里设置为10000毫秒（10秒）
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),
//出错百分比阈值，当出错百分比达到此阈值后，开始短路。默认50%
    })
    public String paymentCircuitBreaker(Integer id){
        if (id < 0){
            throw new RuntimeException("*****id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();//hutool.cn工具包,pom中以及引入起依赖管理器

        return Thread.currentThread().getName()+"\t"+"调用成功,流水号："+serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能负数，请稍候再试,(┬＿┬)/~~     id: " +id;
    }

}

//
//import com.example.service.PaymentService;
//import org.springframework.stereotype.Service;
//import java.util.concurrent.TimeUnit;
//@Service
//public class PaymentServiceImpl implements PaymentService {
//    //成功
//    public String paymentInfo_OK(Integer id) {
//        return "线程池：" + Thread.currentThread().getName() + "   paymentInfo_OK,id：  " + id + "\t" + "访问成功，哈哈哈~~~";
//    }
//
//    //失败
//    public String paymentInfo_Timeout(Integer id) {
//        int timeNumber = 3;
//        try {
//            TimeUnit.SECONDS.sleep(timeNumber);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "线程池：" + Thread.currentThread().getName() + "   paymentInfo_TimeOut,id：  " + id + "\t" + "访问失败，呜呜呜~~" + " 耗时(秒)" + timeNumber;
//    }
//}
