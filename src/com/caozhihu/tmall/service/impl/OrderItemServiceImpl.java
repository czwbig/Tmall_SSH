package com.caozhihu.tmall.service.impl;

import com.caozhihu.tmall.pojo.Order;
import com.caozhihu.tmall.pojo.OrderItem;
import com.caozhihu.tmall.service.OrderItemService;
import com.caozhihu.tmall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderItemService")
public class OrderItemServiceImpl extends BaseServiceImpl implements OrderItemService {

    @Autowired
    ProductImageService productImageService;

    @Override
    public void fill(List<Order> orders) {
        for (Order order : orders) {
            fill(order);
        }
    }

    @Override
    public void fill(Order order) {
        List<OrderItem> orderItems = this.listByParent(order);//orderItem.class中查询对应order的所有orderItems
        order.setOrderItems(orderItems);

        float total = 0;
        int totalNumber = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
            totalNumber += orderItem.getNumber();

            productImageService.setFirstProductImage(orderItem.getProduct());
        }
        order.setTotal(total);
        order.setOrderItems(orderItems);
        order.setTotalNumber(totalNumber);
    }
}
