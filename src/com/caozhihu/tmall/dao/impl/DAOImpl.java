package com.caozhihu.tmall.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

//定义了一个name = "dao" 的bean
@Repository("dao")
public class DAOImpl extends HibernateTemplate {

    //为该方法注入了一个name = "sf" 的bean
    @Resource(name = "sf")
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
