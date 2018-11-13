package com.caozhihu.tmall.service;

import com.caozhihu.tmall.pojo.Order;
import com.caozhihu.tmall.pojo.Review;

public interface ReviewService  extends BaseService{
     public void saveReviewAndUpdateOrderStatus(Review review, Order order);
}
