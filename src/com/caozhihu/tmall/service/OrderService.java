package com.caozhihu.tmall.service;

import com.caozhihu.tmall.pojo.Order;
import com.caozhihu.tmall.pojo.OrderItem;
import com.caozhihu.tmall.pojo.User;
import com.caozhihu.tmall.service.impl.BaseServiceImpl;

import java.util.List;

public interface OrderService extends BaseService {

    public static final String waitPay = "waitPay";
    public static final String waitDelivery = "waitDelivery";
    public static final String waitConfirm = "waitConfirm";
    public static final String waitReview = "waitReview";
    public static final String finish = "finish";
    public static final String delete = "delete";

    public float createOrder(Order order, List<OrderItem> ois);

    public List<Order> listByUserWithoutDelete(User user);
}
