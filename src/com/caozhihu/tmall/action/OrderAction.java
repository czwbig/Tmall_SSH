package com.caozhihu.tmall.action;

import com.caozhihu.tmall.service.OrderService;
import com.caozhihu.tmall.util.Page;
import org.apache.struts2.convention.annotation.Action;

import java.util.Date;

public class OrderAction extends Action4Result {

    @Action("admin_order_list")
    public String list() {
        if (page == null) {
            page = new Page();
        }
        int total = orderService.total();
        page.setTotal(total);
        orders = orderService.listByPage(page);
        orderItemService.fill(orders);
        return "listOrder";
    }

    @Action("admin_order_delivery")
    public String delivery(){
        t2p(order);//开始order只有id值,持久化后，返回的order对象是根据id从数据库取出来的，再进行操作
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.waitConfirm);
        orderService.update(order);
        return "listOrderPage";
    }
}
