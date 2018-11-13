package com.caozhihu.tmall.service;

import com.caozhihu.tmall.pojo.Category;
import com.caozhihu.tmall.util.Page;

import java.util.List;

public interface PropertyService extends BaseService {

    //BaseService只有通用方法，这里需要根据Category对象一对多查询所有对应的Property对象，因此增加如下方法
    public List listByCategory(Category category);

    public List list(Page page, Category category);

    public int total(Category category);
}
