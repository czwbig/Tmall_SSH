package com.caozhihu.tmall.service.impl;

import com.caozhihu.tmall.pojo.Category;
import com.caozhihu.tmall.service.PropertyService;
import com.caozhihu.tmall.util.Page;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("propertyService")
public class PropertyServiceImpl extends BaseServiceImpl implements PropertyService {

    @Override
    public List listByCategory(Category category) {
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);//BaseServiceImpl里面的clazz对象此时是Property
        dc.add(Restrictions.eq("category", category));//注意是根据对象操作，不是数据库字段名
        dc.addOrder(Order.desc("id"));
        return findByCriteria(dc);
    }

    @Override
    public List list(Page page, Category category) {
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        dc.add(Restrictions.eq("category", category));
        dc.addOrder(Order.desc("id"));
        return findByCriteria(dc, page.getStart(), page.getCount());
    }

    //查找出property表中property的category属性等于提供参数的个数
    @Override
    public int total(Category category) {
        String sqlFormat = "select count(*) from %s bean where bean.category = ?0";
        String hql = String.format(sqlFormat, clazz.getName());

        List<Long> l = (List<Long>) this.find(hql, category); //find方法返回的是List<?>泛型，此处需要cast
        if (l.isEmpty()) {
            return 0;
        }
        return l.get(0).intValue();

    }
}
