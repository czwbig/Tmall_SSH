package com.caozhihu.tmall.service.impl;

import com.caozhihu.tmall.pojo.Product;
import com.caozhihu.tmall.pojo.ProductImage;
import com.caozhihu.tmall.service.ProductImageService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productImageService")
public class ProductImageServiceImpl extends BaseServiceImpl implements ProductImageService {

    @Override
    public void setFirstProductImage(Product product) {
        if (product.getFirstProductImage() != null) {
            return;
        }
        //取得productImage.class中对应product和type的所有productImage对象，并把第一个设置给product
        List<ProductImage> productImages = list("product", product, "type", ProductImageService.type_single);
        if (!productImages.isEmpty()) {
            product.setFirstProductImage(productImages.get(0));
        }
    }

}
