package com.caozhihu.tmall.service;

import com.caozhihu.tmall.pojo.Order;

import java.util.List;

public interface OrderItemService extends BaseService {

    public void fill(List<Order> orders);
    public void fill(Order order);
}
