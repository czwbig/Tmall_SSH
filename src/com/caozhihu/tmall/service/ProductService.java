package com.caozhihu.tmall.service;

import com.caozhihu.tmall.pojo.Category;
import com.caozhihu.tmall.pojo.Product;
import com.caozhihu.tmall.service.BaseService;

import java.util.List;

public interface ProductService extends BaseService {
    public void fill(List<Category> categories);
    public void fill(Category category);
    public void fillByRow(List<Category> categories);
    public void setSaleAndReviewNumber(Product product);
    public void setSaleAndReviewNumber(List<Product> products);
    public List<Product> search(String keyword, int start, int count);
}
