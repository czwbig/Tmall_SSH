package com.caozhihu.tmall.service.impl;

import com.caozhihu.tmall.pojo.Order;
import com.caozhihu.tmall.pojo.Review;
import com.caozhihu.tmall.service.OrderService;
import com.caozhihu.tmall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("reviewService")
public class ReviewServiceImpl extends BaseServiceImpl implements ReviewService {

    @Autowired
    OrderService orderService;

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public void saveReviewAndUpdateOrderStatus(Review review, Order order) {
        orderService.update(order);
        save(review);
    }

}
