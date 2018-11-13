package com.caozhihu.tmall.service.impl;

import com.caozhihu.tmall.service.CategoryService;
import org.springframework.stereotype.Service;

@Service("categoryService")
public class CategoryServiceImpl extends BaseServiceImpl implements CategoryService{
    //CategoryService接口仅仅继承了BaseService接口，无新增方法
    //这里CategoryServiceImpl只需要继承BaseServiceImpl类，无新增方法
}
