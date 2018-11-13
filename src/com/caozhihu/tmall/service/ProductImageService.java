package com.caozhihu.tmall.service;

import com.caozhihu.tmall.pojo.Product;


public interface ProductImageService extends BaseService {

    public static final String type_single = "type_single";
    public static final String type_detail = "type_detail";

    public void setFirstProductImage(Product product);
}
