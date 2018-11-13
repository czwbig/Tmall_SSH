package com.caozhihu.tmall.service.impl;

import com.caozhihu.tmall.pojo.Order;
import com.caozhihu.tmall.pojo.OrderItem;
import com.caozhihu.tmall.pojo.User;
import com.caozhihu.tmall.service.OrderItemService;
import com.caozhihu.tmall.service.OrderService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {

    @Autowired
    OrderItemService orderItemService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    public float createOrder(Order order, List<OrderItem> ois) {
        save(order);
        float total = 0;
        for (OrderItem oi : ois) {//为每个Orderitem设置其订单
            oi.setOrder(order);
            orderItemService.update(oi);
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
        }
        return total;
    }

    @Override
    public List<Order> listByUserWithoutDelete(User user) {
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        dc.add(Restrictions.eq("user", user));
        dc.add(Restrictions.ne("status", OrderService.delete));//非条件查询
        return (List<Order>) findByCriteria(dc);
    }
}
