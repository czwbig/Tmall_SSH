package com.caozhihu.tmall.action;

import com.caozhihu.tmall.service.*;
import com.caozhihu.tmall.util.ImageUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

@Component
public class Action4Service extends Action4Pojo {


    @Autowired
    CategoryService categoryService; //自动装载的是CategoryService实现类CategoryServiceImpl的对象

    @Autowired
    PropertyService propertyService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    PropertyValueService propertyValueService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ReviewService reviewService;

    /**
     * transient to persistent
     * 瞬时对象转换为持久对象
     * 作用是从数据库取出对应的category对象，并设置在Action的category属性上
     * 传进来的对象只有id属性，直接进行保存的话Action4Pojo.setCategory(category)，这样category.name是null
     * 此方法根据id从数据库获取对应id的category对象，此时是完整对象，没有null
     * 再获取对象对应的类名Category，拼凑成setCategory(),
     * 使用反射进行调用Action4Pojo.setCategory(category)
     *
     * @param o
     */
    public void t2p(Object o) {//假设是cagegory对象,传进来的时候应该只有id属性，本来是要根据id从数据库获取category对象进行下一步使用的
        try {
            Class clazz = o.getClass();//Category.class
            int id = (Integer) clazz.getMethod("getId").invoke(o);//id = category.getId();
            Object persistentBean = categoryService.get(clazz, id);//persistentBean 是从数据库中通过category id取得的对象

            String beanName = clazz.getSimpleName(); //Category
            // Action4Service.setCategory(),其实是父类的 Action4Pojo.setCategory() 方法，并制定方法参数为 clazz
            Method setMethod = getClass().getMethod("set" + beanName, clazz);
            setMethod.invoke(this, persistentBean);//Action4Service.setCategory(persistenBean)
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //传入file对象，会根据上传的img信息，把img写到file里面

    public void saveWithJpg(File file) {
        try {
            FileUtils.copyFile(img, file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PropertyService getPropertyService() {
        return propertyService;
    }

    public void setPropertyService(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public ProductImageService getProductImageService() {
        return productImageService;
    }

    public void setProductImageService(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    public PropertyValueService getPropertyValueService() {
        return propertyValueService;
    }

    public void setPropertyValueService(PropertyValueService propertyValueService) {
        this.propertyValueService = propertyValueService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrderItemService getOrderItemService() {
        return orderItemService;
    }

    public void setOrderItemService(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    public ReviewService getReviewService() {
        return reviewService;
    }

    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
}
