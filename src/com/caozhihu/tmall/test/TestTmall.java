package com.caozhihu.tmall.test;

import com.caozhihu.tmall.dao.impl.DAOImpl;
import com.caozhihu.tmall.pojo.Category;
import org.hibernate.criterion.DetachedCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestTmall {


    @Autowired
    DAOImpl dao;

    @Test
    @Transactional
    public void delete(){
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Category.class);
        List<Category> categories = (List<Category>) dao.findByCriteria(detachedCriteria);
        for (Category category : categories) {
            dao.delete(category);
        }
    }

    @Test
    @Transactional
    public void test(){
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Category.class);
        List<Category> cs = (List<Category>) dao.findByCriteria(detachedCriteria);
        if (cs.isEmpty()) {
            for (int i = 0; i < 10; i++) {
                Category c = new Category();
                c.setName("测试分类" + (++i));
                dao.save(c);
            }
            System.out.println("成功添加10个测试类");
        }

    }
}
