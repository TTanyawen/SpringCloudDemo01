package com.example.service.impl;

import com.example.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
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
