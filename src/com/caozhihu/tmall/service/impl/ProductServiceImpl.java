package com.caozhihu.tmall.service.impl;

import com.caozhihu.tmall.pojo.Category;
import com.caozhihu.tmall.pojo.OrderItem;
import com.caozhihu.tmall.pojo.Product;
import com.caozhihu.tmall.service.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("productService")
public class ProductServiceImpl extends BaseServiceImpl implements ProductService {

    @Autowired
    ProductImageService productImageService;//fill(Category category)中需要用到productImageService的setFirstProductImage(product)方法为product设置图片
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;

    @Override
    public void fill(List<Category> categories) {
        for (Category category : categories) {
            fill(category);
        }
    }

    @Override
    public void fill(Category category) {
        List<Product> products = listByParent(category);//查询product.class中对应category的所有product，为他们设置图片
        for (Product product : products) {
            productImageService.setFirstProductImage(product);
        }

        category.setProducts(products);
    }

    @Override
    public void fillByRow(List<Category> categories) {
        int productNumberEachRow = 8;
        for (Category category : categories) {
            List<Product> products = category.getProducts();//对每个category获取其所有的product集合,下面被拆成多行
            List<List<Product>> productsByRow = new ArrayList<>();
            for (int i = 0; i < products.size(); i += productNumberEachRow) {
                int size = i + productNumberEachRow;
                size = size > products.size() ? products.size() : size; //size取products.size()和i+productNumberEachRow之小
                List<Product> productsOfEachRow = products.subList(i, size);//从所有product集合中截取i~size
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    @Override
    public void setSaleAndReviewNumber(Product product) {
        //select count(*) from orderitem bean where bean.product = product
        //从orderitem表里面查询orderitem.product=product的orderitem数据条数，也就是这个product所有的orderitem数即销量，但是一个orderitem的number不为1啊，这样是不是错的？
//        int saleCount = orderItemService.total(product);
        //改为我自己的实现，已修正这个bug，并新增了一个bug，解决思路根据order状态进行查询，或者更应该在orderService新增方法
        int saleCount = 0;
        List<OrderItem> ois = orderItemService.list("product", product);
        for (OrderItem oi : ois) {
            saleCount += oi.getNumber();
        }
        product.setSaleCount(saleCount);
        int reviewCount = reviewService.total(product);//从review表里面查询review.product=product的review数据条数，也就是这个product所有的review数
        product.setReviewCount(reviewCount);
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product product : products) {
            setSaleAndReviewNumber(product);
        }
    }

    @Override
    public List<Product> search(String keyword, int start, int count) {
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        dc.add(Restrictions.like("name", "%" + keyword + "%"));
        return (List<Product>) findByCriteria(dc, start, count);
    }
}
