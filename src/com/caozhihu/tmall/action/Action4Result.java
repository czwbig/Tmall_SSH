package com.caozhihu.tmall.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@Namespace("/")
@ParentPackage("basicstruts")
@Results(
        {/*全局的*/
                @Result(name = "success.jsp", location = "/success.jsp"),
                @Result(name = "fail.jsp", location = "/fail.jsp"),

                /*分类管理*/
                @Result(name = "listCategory", location = "/admin/listCategory.jsp"),
                @Result(name = "editCategory", location = "/admin/editCategory.jsp"),
                @Result(name = "listCategoryPage", type = "redirect", location = "/admin_category_list"),

                /*属性管理*/
                //在设置location参数时，可以在参数值中使用OGNL表达式。
                @Result(name = "listProperty", location = "/admin/listProperty.jsp"),
                @Result(name = "editProperty", location = "/admin/editProperty.jsp"),
                @Result(name = "listPropertyPage", type = "redirect", location = "/admin_property_list?category.id=${property.category.id}"),

                /*产品管理*/
                @Result(name = "listProduct", location = "/admin/listProduct.jsp"),
                @Result(name = "editProduct", location = "/admin/editProduct.jsp"),
                @Result(name = "listProductPage", type = "redirect", location = "/admin_product_list?category.id=${product.category.id}"),

                /*产品图片管理*/
                @Result(name = "listProductImage", location = "/admin/listProductImage.jsp"),
                @Result(name = "listProductImagePage", type = "redirect", location = "/admin_productImage_list?product.id=${productImage.product.id}"),

                /*属性值管理*/
                @Result(name = "editPropertyValue", location = "/admin/editPropertyValue.jsp"),

                /*用户管理*/
                @Result(name = "listUser", location = "/admin/listUser.jsp"),

                /*订单管理*/
                @Result(name = "listOrder", location = "/admin/listOrder.jsp"),
                @Result(name = "listOrderPage", type = "redirect", location = "/admin_order_list"),

                /*前台 服务端跳转*/
                @Result(name = "home.jsp", location = "/home.jsp"),
                @Result(name = "register.jsp", location = "/register.jsp"),
                @Result(name = "login.jsp", location = "/login.jsp"),
                @Result(name = "product.jsp", location = "/product.jsp"),
                @Result(name = "category.jsp", location = "/category.jsp"),
                @Result(name = "searchResult.jsp", location = "/searchResult.jsp"),
                @Result(name = "buy.jsp", location = "/buy.jsp"),
                @Result(name = "cart.jsp", location = "/cart.jsp"),
                @Result(name = "alipay.jsp", location = "/alipay.jsp"),
                @Result(name = "payed.jsp", location = "/payed.jsp"),
                @Result(name = "bought.jsp", location = "/bought.jsp"),
                @Result(name = "confirmPay.jsp", location = "/confirmPay.jsp"),
                @Result(name = "orderConfirmed.jsp", location = "/orderConfirmed.jsp"),
                @Result(name = "review.jsp", location = "/review.jsp"),

                /*前台 客户端跳转*/
                @Result(name = "registerSuccessPage", type = "redirect", location = "/registerSuccess.jsp"),
                @Result(name = "homePage", type = "redirect", location = "forehome"),
                @Result(name = "buyPage", type = "redirect", location = "forebuy?oiids=${oiid}"),
                @Result(name = "alipayPage", type = "redirect", location = "forealipay?order.id=${order.id}&total=${total}"),
                @Result(name = "reviewPage", type = "redirect", location = "forereview?order.id=${order.id}&showonly=${showonly}"),
        })
public class Action4Result extends Action4Parameter {
}
