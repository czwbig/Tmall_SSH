package com.caozhihu.tmall.service.impl;

import com.caozhihu.tmall.pojo.Product;
import com.caozhihu.tmall.pojo.Property;
import com.caozhihu.tmall.pojo.PropertyValue;
import com.caozhihu.tmall.service.PropertyService;
import com.caozhihu.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("propertyValueService")
public class PropertyValueServiceImpl extends BaseServiceImpl implements PropertyValueService {

    @Autowired
    PropertyService propertyService;//因为propertyValue初始化需要使用propertyService.listByparent()方法，所以需要注入

    //初始化propertyValue，因为属性值没有增加，只有修改，所以需要使用product初始化来自动增加，以便修改
    //查询product对应的所有property，对于每个property，根据property和product获取对应的propertyValue，如果不存在则新建并插入到数据库
    @Override
    @Transactional(readOnly = false)
    public void init(Product product) {
        List<Property> properties = propertyService.listByParent(product.getCategory());
        for (Property property : properties) {
            PropertyValue propertyValue = get(property, product);
            if (propertyValue == null) {
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                save(propertyValue);
            }
        }
    }

    //获取同时满足两个条件的propertyValue
    private PropertyValue get(Property property, Product product) {
        List<PropertyValue> result = this.list("property", property, "product", product);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }
}
