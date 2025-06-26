package com.example.service;


import com.example.dao.PaymentDao;
import com.example.entity.Payment;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
@Service
public class PaymentServiceImpl implements PaymentService{
    @Resource
    //@Autowired
    private PaymentDao paymentDao;
    public int create(Payment payment){
        return paymentDao.create(payment);
    }
    public Payment getPaymentById( Long id){
        return paymentDao.getPaymentById(id);
    }
}