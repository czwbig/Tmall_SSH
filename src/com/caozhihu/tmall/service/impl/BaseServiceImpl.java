package com.caozhihu.tmall.service.impl;

import com.caozhihu.tmall.service.BaseService;
import com.caozhihu.tmall.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

//定义一个BaseServiceImpl类型的bean，未指定name，默认以类名首字母小写的方式自动命名
@Service
public class BaseServiceImpl extends ServiceDelegateDAO implements BaseService {//继承 ServiceDelegateDAO 是为了使用其方法。另外实现 BaseService 接口的方法

    protected Class clazz;

    //clazz对应继承了BaseServiceImpl的pojoServiceImpl的pojo类对象
    public BaseServiceImpl() {
        try {
            throw new Exception();
        } catch (Exception e) {
            StackTraceElement stackTraceElement[] = e.getStackTrace();//获得异常栈对象数组，这个StackTraceElement是ERROR的每一个cause by的信息
            String serviceImpleCalssName = stackTraceElement[1].getClassName();//第2个异常类的类名，注意是ServiceImpl类的类名，第1个BaseServiceImpl类的类名
            try {
                Class serviceImplClazz = Class.forName(serviceImpleCalssName);//获得异常类，注意是ServiceImpl类的类对象
                String serviceImpleClassSimpleName = serviceImplClazz.getSimpleName();//ServiceImpl类的简化类名，即去除包名前缀,例如CategoryServiceImpl
                String pojoSimpleName = serviceImpleClassSimpleName.replaceAll("ServiceImpl", "");//去掉ServiceImpl后缀，得到Category
                //获得pojo类包名，serviceimpl的全限定类名中取得包名，然后替换后缀，
                //建立在服务实现类是放在xxx.service.impl包下的，而实体类是放在xxx.pojo包下的
                // 例如com.how2java.tmall.service.impl变成com.how2java.tmall.pojo；
                String pojoPackageName = serviceImplClazz.getPackage().getName().replaceAll(".service.impl", ".pojo");
                String pojoFullName = pojoPackageName + "." + pojoSimpleName;
                clazz = Class.forName(pojoFullName);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public List list() {
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        dc.addOrder(Order.desc("id"));
        return findByCriteria(dc);
    }

    @Override
    public List listByPage(Page page) {
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        dc.addOrder(Order.desc("id"));
        return (List<Object>) findByCriteria(dc, page.getStart(), page.getCount());
    }

    @Override
    public int total() {
        String hql = "select count(*) from " + clazz.getName();
        List<Long> l = (List<Long>) find(hql);
        if (l.isEmpty()) {
            return 0;
        }
        Long result = l.get(0);
        return result.intValue();
    }

    @Override
    //查询父类下的所有子类对象，如查询某个分类下的所有属性。
    //例如orderItem.class中查询order=order的所有orderItems
    public List listByParent(Object parent) {
        String parentName = parent.getClass().getSimpleName();
        String parentNameWithFirstLetterLower = StringUtils.uncapitalize(parentName);
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        dc.add(Restrictions.eq(parentNameWithFirstLetterLower, parent));
        dc.addOrder(Order.desc("id"));
        return findByCriteria(dc);

    }

    @Override
    public List list(Page page, Object parent) {
        String parentName = parent.getClass().getSimpleName();
        String parentNameWithFirstLetterLower = StringUtils.uncapitalize(parentName);
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        dc.add(Restrictions.eq(parentNameWithFirstLetterLower, parent));
        dc.addOrder(Order.desc("id"));
        return findByCriteria(dc, page.getStart(), page.getCount());
    }

    @Override
    public int total(Object parent) {
        String parentName = parent.getClass().getSimpleName();
        String parentNameWithFirstLetterLower = StringUtils.uncapitalize(parentName);

        String sqlFormat = "select count(*) from %s bean where bean.%s = ?0";
        String hql = String.format(sqlFormat, clazz.getName(), parentNameWithFirstLetterLower);

        List<Long> l = (List<Long>) this.find(hql, parent);
        if (l.isEmpty()) {
            return 0;
        }
        return l.get(0).intValue();
    }

    /*
    调用的是HibernateTemplate的public Serializable save(final Object entity)方法，
    Number类实现了Serializable接口，并提供了一些类型转换方法，包括Integer在内的9个包装类都继承了它
     */
    @Override
    @Transactional
    public Integer save(Object object) {
        return ((Integer) super.save(object));
    }

    @Override
    public Object get(Class clazz, int id) {
        return super.get(clazz, id);
    }

    @Override
    public Object get(int id) {
        return get(clazz, id);
    }

    @Override
    public List list(Object... pairParams) {
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < pairParams.length; i = i + 2) {
            map.put(pairParams[i].toString(), pairParams[i + 1]);
        }
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);

        Set<String> ks = map.keySet();
        for (String key : ks) {
            if (map.get(key) == null) {
                dc.add(Restrictions.isNull(key));
            } else {
                dc.add(Restrictions.eq(key, map.get(key)));
            }
        }
        dc.addOrder(Order.desc("id"));
        return this.findByCriteria(dc);
    }
}
