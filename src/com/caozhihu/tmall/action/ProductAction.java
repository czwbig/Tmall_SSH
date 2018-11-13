package com.caozhihu.tmall.action;

import com.caozhihu.tmall.pojo.Product;
import com.caozhihu.tmall.util.Page;
import org.apache.struts2.convention.annotation.Action;

import java.util.Date;

public class ProductAction extends Action4Result {

    @Action("admin_product_list")
    public String list() {
        if (page == null) {
            page = new Page();
        }
        int total = propertyService.total(category);
        page.setTotal(total);
        page.setParam("&category.id=" + category.getId());
        products = productService.list(page, category);
        for (Product product : products) {
            productImageService.setFirstProductImage(product);
        }
        t2p(category);
        return "listProduct";
    }

    @Action("admin_product_add")
    public String add() {
        product.setCreateDate(new Date());
        productService.save(product);
        return "listProductPage";
    }

    @Action("admin_product_delete")
    public String delete() {
        t2p(product);
        productService.delete(product);
        return "listProductPage";
    }

    @Action("admin_product_edit")
    public String edit() {
        t2p(product);
        return "editProduct";
    }

    @Action("admin_product_update")
    public String update() {
        Product productFromDB = (Product) productService.get(product.getId());//从数据中取出对应id的product对象
        product.setCreateDate(productFromDB.getCreateDate());//因为product的日期信息是不改变的，页面传过来的produc是不带这个信息的
        productService.update(product);
        return "listProductPage";
    }
}
