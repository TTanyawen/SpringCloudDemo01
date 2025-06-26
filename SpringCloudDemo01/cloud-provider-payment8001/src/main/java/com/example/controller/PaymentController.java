package com.example.controller;


import com.example.entity.CommonResult;
import com.example.entity.Payment;
import com.example.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;
    @PostMapping(value = "/payment/create")
    public CommonResult<Payment> create(Payment payment){ //埋雷
        int result = paymentService.create(payment);
        log.info("*****插入结果kk："+result);
        if (result>0){  //成功
            return new CommonResult(200,"插入数据库成功",result);
        }else {
            return new CommonResult(444,"插入数据库失败",null);
        }
    }
//    @GetMapping(value = "/payment/get/{id}")
//    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
//        Payment payment = paymentService.getPaymentById(id);
//        log.info("*****查询结果："+payment);
//        if (payment!=null){  //说明有数据，能查询成功
//            return new CommonResult(200,"查询成功",payment);
//        }else {
//            return new CommonResult(444,"没有对应记录，查询ID："+id,null);
//        }
//    }

    @Value("${server.port}")
    private String port;
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);

        log.info("*****查询结果："+payment);
        if (payment!=null){  //说明有数据，能查询成功
            return new CommonResult(200,"查询成功~~~~~~,端口号："+port,payment);
        }else {
            return new CommonResult(444,"没有对应记录，查询ID："+id+"，端口号："+port,null);
        }
    }


    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        log.info("*****paymentFeignTimeout from port："+port);
        try {
            TimeUnit.SECONDS.sleep(3);  //单位秒
        }catch (Exception e) {e.printStackTrace();}
        return port;
    }
}
