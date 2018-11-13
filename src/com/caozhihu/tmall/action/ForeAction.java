package com.caozhihu.tmall.action;

import com.caozhihu.tmall.pojo.OrderItem;
import com.caozhihu.tmall.pojo.Product;
import com.caozhihu.tmall.pojo.User;
import com.caozhihu.tmall.service.OrderService;
import com.caozhihu.tmall.service.ProductImageService;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.RandomUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class ForeAction extends Action4Result {

    @Action("forehome")
    public String home() {
        categories = categoryService.list();
        productService.fill(categories);//为每个category来设置setFirstProductImage()
        productService.fillByRow(categories);//为每个product设置setFirstProductImage()，再为每个category来setProductsByRow(products)
        return "home.jsp";
    }

    @Action("foreregister")
    public String register() {
        //进行html转义，防止使用诸如 <script>alert('papapa')</script> 这样的名称，
        // 登录成功后跳转到home，这行代码就会被注入js，<a href="login.jsp">${user.name}</a>
        // 会导致网页打开就弹出一个对话框
        user.setName(HtmlUtils.htmlEscape(user.getName()));
        boolean exist = userService.isExit(user.getName());

        if (exist) {
            msg = "用户名已经被使用，不能使用";
            return "register.jsp";
        }
        userService.save(user);
        return "registerSuccessPage";
    }

    @Action("forelogin")
    public String login() {
        user.setName(HtmlUtils.htmlEscape(user.getName()));
        User user_session = userService.get(user.getName(), user.getPassword());

        if (user_session == null) {
            msg = "账户密码错误";
            return "login.jsp";
        }
        ActionContext.getContext().getSession().put("user", user_session);
        return "homePage";
    }

    @Action("forelogout")
    public String logout() {
        ActionContext.getContext().getSession().remove("user");
        return "homePage";
    }

    @Action("foreproduct")
    public String product() {
        t2p(product);

        productImageService.setFirstProductImage(product);
        productSingleImages = productImageService.list("product", product, "type", ProductImageService.type_single);
        productDetailImages = productImageService.list("product", product, "type", ProductImageService.type_detail);
        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);

        propertyValues = propertyValueService.listByParent(product);

        reviews = reviewService.listByParent(product);

        productService.setSaleAndReviewNumber(product);

        return "product.jsp";
    }

    @Action("forecheckLogin")
    public String checkLogin() {
        User user = (User) ActionContext.getContext().getSession().get("user");
        if (user == null) {
            return "fail.jsp";
        } else {
            return "success.jsp";
        }
    }

    @Action("foreloginAjax")
    public String loginAjax() {
        user.setName(HtmlUtils.htmlEscape(user.getName()));
        User user_session = userService.get(user.getName(), user.getPassword());

        if (user_session == null) {
            return "fail.jsp";
        }
        ActionContext.getContext().getSession().put("user", user_session);
        return "success.jsp";
    }

    @Action("forecategory")
    public String category() {
        t2p(category);
        productService.fill(category);//category.setProducts(products);
        productService.setSaleAndReviewNumber(category.getProducts());

        if (sort != null) {
            switch (sort) {
                case "review"://评价数量多的放前面
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return o2.getReviewCount() - o1.getReviewCount();
                        }
                    });
                    break;
                case "date"://新创建的放前面，即创建日期比较晚的在前
                    Collections.sort(category.getProducts(), ((o1, o2) -> o1.getCreateDate().compareTo(o2.getCreateDate())));
                    break;
                case "saleCount"://销量高的放前面
                    Collections.sort(category.getProducts(), ((o1, o2) -> o2.getSaleCount() - o1.getSaleCount()));
                    break;
                case "price"://价格低的放前面
                    Collections.sort(category.getProducts(), ((o1, o2) -> (int) (o1.getPromotePrice() - o2.getPromotePrice())));
                    break;
                case "all"://把 销量*评价 高的放前面
                    Collections.sort(category.getProducts(), ((o1, o2) -> o2.getReviewCount() * o2.getSaleCount() - o1.getReviewCount() * o1.getSaleCount()));
                    break;
            }
        }

        return "category.jsp";

    }

    @Action("foresearch")
    public String search() {
        products = productService.search(keyword, 0, 20);
        productService.setSaleAndReviewNumber(products);
        for (Product product : products) {
            productImageService.setFirstProductImage(product);
        }

        return "searchResult.jsp";
    }

    @Action("forebuyone")
    public String buyone() {
        User user = (User) ActionContext.getContext().getSession().get("user");
        boolean found = false;
        List<OrderItem> ois = orderItemService.list("user", user, "order", null);
        for (OrderItem oi : ois) { //先看看数据库里有没有orderitem这个产品
            if (oi.getProduct().getId() == product.getId()) {
                oi.setNumber(oi.getNumber() + num);
                orderItemService.update(oi);
                found = true;
                oiid = oi.getId();
                break;
            }
        }
        if (!found) {
            OrderItem oi = new OrderItem();
            oi.setUser(user);
            oi.setNumber(num);
            oi.setProduct(product);
            orderItemService.save(oi);
            oiid = oi.getId();
        }
        return "buyPage";
    }

    @Action("forebuy")
    public String buy() {
        orderItems = new ArrayList<>();
        //forebuy?oiids=x1&oiids=x2这种形式
        //自动将数据封装称一个数组传给了后台action，也就是说，
        // 只需要我们设置同名的数组，在设置对应的get、set方法就能获取到来自前端传来的数据
        for (int oiid : oiids) {
            OrderItem oi = (OrderItem) orderItemService.get(oiid);
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
            orderItems.add(oi);
            productImageService.setFirstProductImage(oi.getProduct());
        }
        ActionContext.getContext().getSession().put("orderItems", orderItems);//后续生成订单还要使用，放session上
        return "buy.jsp";
    }

    @Action("foreaddCart")
    public String addCart() {
        User user = (User) ActionContext.getContext().getSession().get("user");
        boolean found = false;

        List<OrderItem> ois = orderItemService.list("user", user, "order", null);
        for (OrderItem oi : ois) {
            if (oi.getProduct().getId() == product.getId()) {
                oi.setNumber(oi.getNumber() + num);
                orderItemService.update(oi);
                found = true;
                break;
            }
        }
        if (!found) {
            OrderItem oi = new OrderItem();
            oi.setUser(user);
            oi.setNumber(num);
            oi.setProduct(product);
            orderItemService.save(oi);
        }
        return "success.jsp";
    }

    @Action("forecart")
    public String cart() {
        User user = (User) ActionContext.getContext().getSession().get("user");
        orderItems = orderItemService.list("user", user, "order", null);
        for (OrderItem orderItem : orderItems) {
            productImageService.setFirstProductImage(orderItem.getProduct());
        }
        return "cart.jsp";
    }

    @Action("forechangeOrderItem")
    public String changeOrderItem() {
        User user = (User) ActionContext.getContext().getSession().get("user");
        List<OrderItem> ois = orderItemService.list("user", user, "order", null);
        for (OrderItem oi : ois) {
            if (oi.getProduct().getId() == product.getId()) {
                oi.setNumber(num);
                orderItemService.update(oi);
                break;
            }
        }
        return "success.jsp";
    }

    @Action("foredeleteOrderItem")
    public String deleteOrderItem() {
        orderItemService.delete(orderItem);
        return "success.jsp";
    }

    @Action("forecreateOrder")
    public String createOrder() {
        List<OrderItem> ois = (List<OrderItem>) ActionContext.getContext().getSession().get("orderItems");//buy()把订单项集合被放在session上
        if (ois.isEmpty()) {
            return "login.jsp";
        }
        User user = (User) ActionContext.getContext().getSession().get("user");

        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(System.currentTimeMillis());


        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUser(user);
        order.setStatus(OrderService.waitPay);

        total = orderService.createOrder(order, ois);
        return "alipayPage";

    }

    @Action("forealipay")
    public String forealipay() {
        return "alipay.jsp";
    }

    @Action("forepayed")
    public String payed() {
        t2p(order);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);
        return "payed.jsp";
    }

    @Action("forebought")
    public String bought() {
        User user = (User) ActionContext.getContext().getSession().get("user");
        orders = orderService.listByUserWithoutDelete(user);
        orderItemService.fill(orders); //为order们设置orderItem
        return "bought.jsp";
    }

    @Action("foreconfirmPay")
    public String confirmPay() {
        t2p(order);
        orderItemService.fill(order);
        return "confirmPay.jsp";
    }

    @Action("foreorderConfirmed")
    public String orderConfirmed() {
        t2p(order);
        order.setStatus(OrderService.waitReview);
        order.setConfirmDate(new Date());
        orderService.update(order);
        return "orderConfirmed.jsp";
    }

    @Action("foredeleteOrder")
    public String deleteOrder() {
        t2p(order);
        order.setStatus(OrderService.delete);
        orderService.update(order);
        return "success.jsp";
    }

    @Action("forereview")
    public String revice() {
        t2p(order);
        orderItemService.fill(order);
        product = order.getOrderItems().get(0).getProduct();
        reviews = reviewService.listByParent(product);
        productService.setSaleAndReviewNumber(product);
        return "review.jsp";
    }

    @Action("foredoreview")
    public String doreview() {
        t2p(order);
        t2p(product);

        order.setStatus(OrderService.finish);
        String content = review.getContent();
        content = HtmlUtils.htmlEscape(content);
        User user = (User) ActionContext.getContext().getSession().get("user");
        review.setContent(content);
        review.setProduct(product);
        review.setCreateDate(new Date());
        review.setUser(user);

        reviewService.saveReviewAndUpdateOrderStatus(review, order);

        showonly = true;
        return "reviewPage";
    }


}
