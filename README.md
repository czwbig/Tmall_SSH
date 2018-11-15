# Tmall_SSH

技术栈 Struts2 + Hibernate + Spring + Jsp + Tomcat , 是 Java Web 入门非常好的练手项目

### 项目简介

`关联项目`  
**[github - 天猫 JavaEE 项目](https://github.com/czwbig/Tmall_JavaEE)**  
**[github - 天猫 SSH 项目](https://github.com/czwbig/Tmall_SSH)**  
**[github - 天猫 SSM 项目](https://github.com/czwbig/Tmall_SSM)**  

之前使用 JavaEE 整套技术来作为解决方案，实现模仿天猫网站的各种业务场景，现在开始使用框架技术，毕竟工作中还是要用框架。
本项目技术相对老旧，现在很少用 Struts2 了，但如果接手老项目的话还是要懂的，学习过程我们也可以认识到它们当时优秀的设计理念，
当时解决了哪些痛点，后面又是因为什么被新技术替代，这样才能加深对 Java Web 整个平台的理解，不亏。




> 项目用到的技术如下：  
**Java：Java SE基础  
前端：`HTML`,` CSS`, `JavaScript`,`AJAX`, `JQuery`, `Bootstrap`  
J2EE：`Tomcat`, `Servlet`, `JSP`, `Filter`  
框架： `Hibernate`，`Struts`，`Spring`，`SSH整合`  
数据库：`MySQL`**  
>

![项目结构](https://upload-images.jianshu.io/upload_images/14923529-929148a95cc33453.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


### 表结构

[建表sql](https://github.com/czwbig/Tmall_SSH/blob/master/sql/tmall_ssh.sql)  已经放在 Github 项目的 /sql 文件夹下

|表名 |中文含义 |介绍 |
| - |:-:| -|
| Category           |分类表         |存放分类信息，如女装，平板电视，沙发等 
| Property            |属性表         |存放属性信息，如颜色，重量，品牌，厂商，型号等 
| Product             |产品表         |存放产品信息，如LED40EC平板电视机，海尔EC6005热水器 
| PropertyValue   |属性值表     |存放属性值信息，如重量是900g,颜色是粉红色 
| ProductImage   |产品图片表   |存放产品图片信息，如产品页显示的5个图片 
| Review              |评论表           |存放评论信息，如买回来的蜡烛很好用，么么哒 
| User                   |用户表         |存放用户信息，如斩手狗，千手小粉红 
| Order                 |订单表         |存放订单信息，包括邮寄地址，电话号码等信息 
| OrderItem         |订单项表       | 存放订单项信息，包括购买产品种类，数量等

![表关系](https://upload-images.jianshu.io/upload_images/14923529-8645ee131490206f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

|一|多|
|-|-|
| Category-分类	|Product-产品
| Category-分类	|Property-属性
| Property-属性	|PropertyValue-属性值
| Product-产品	      |PropertyValue-属性值
| Product-产品	      | ProductImage-产品图片
| Product-产品	     | Review-评价
| User-用户	    | Order-订单
| Product-产品	    | OrderItem-订单项
| User-用户	    | OrderItem-订单项
| Order-订单	    | OrderItem-订单项
| User-用户	    | User-评价

以上直接看可能暂时无法完全理解，结合后面具体到项目的业务流程就明白了。

----

### 开发流程
首先使用经典的 SSH 模式进行由浅入深地开发出第一个分类管理模块 ，
然后分析这种方式的弊端，对其进行项目重构，重构这一块可以学习到不少 Java 里的中高级处理手法，
使得框架更加紧凑，后续开发更加便利和高效率。 

### 实体类设计
准备 Category 实体类，并用 Hibernate 注解标示其对应的表，字段等信息。
举个例子，对于 `分类 / category` 的 实体类 和 表结构 设计如下：
![//已省略对应的 getter/setter 方法](https://upload-images.jianshu.io/upload_images/14923529-6aa55971c25ab926.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


### DAOImpl  类设计
DAO 是 Data Access Object 的缩写，专门用于进行数据库访问的操作。
DAOImpl 继承了 HibernateTemplate，这是一个 Hibernate 框架提供的模板类，提供了各种各样的 CRUD方法，满足各种数据库操作的需要。

![注： 这里没有使用DAO接口，而是直接使用了DAOImpl 实现类的方式](https://upload-images.jianshu.io/upload_images/14923529-95b4e3ac1178b547.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

重写 HibernateTemplate 的 setSessionFactory() 方法， 以用于注入 SessionFactory  , 

![applicateionContext.xml 定义了 name="sf" 的 SessionFactory bean](https://upload-images.jianshu.io/upload_images/14923529-d15256932f22e52b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

SessionFactory 是在 spring 的配置文件里面定义的 bean ，可以看到其配置了连接数据库的数据源等信息，这样 dao 操作的时候，就不必获取对应的数据库连接进行操作，
Spring 将数据源 ds 对象注入 SessionFactory ，sf 又被注入到 HibernateTemplate ，dao  继承 HibernateTemplate 就可以直接操作数据库了，非常简便。

对比不使用 Spring+Hibernate 的情况，我们需要利用数据管理类 DBUtil 获取 Connectoion ，
并在 dao 里面获取对应的 Statement 分别实现 CURD 等方法，利用 JDBC 从数据库取出数据，再构造成 bean 对象返回。


----
### Service 类

设计 CategoryService 接口，用于提供业务方法 list() ，即查询所有的分类。

```
public interface CategoryService{
    public List list();
}
```

CategoryServiceImpl  实现了 CategoryService 接口，提供list()方法的具体实现,同时自动装配(注入) 了 DAOImpl 的实例 dao ,
在 list() 方法中，通过 dao 获取所有的分类对象。

```
@Service
public class CategoryServiceImpl  implements CategoryService {
    @Autowired 
    DAOImpl dao;
    @Override
    public List list() {
        DetachedCriteria dc = DetachedCriteria.forClass(Category.class); // 获取DetachedCriteria对象
        dc.addOrder(Order.desc("id"));
        return dao.findByCriteria(dc); //这是 HibernateTemplate 提供的方法
    }
}
```
---

### Action 类

CategoryAction 类作为 MVC 设计模式中的控制层起作用。  
1. 使用 basicstruts ，对应配置文件 struts.xml 中定义的 basicstruts 保持一致  
2. 在 Result 注解中，定义了返回的页面为 /admin/listCategory.jsp  
3. 自动装配（注入）categoryService 对象，用于从数据库获取所有分类对象的集合。  
4. 把对 admin_category_list 路径的访问映射到 list 方法上  
5.  list() 方法通过 categoryService 获取到所有的分类对象，放在 categories 属性中。  
6. 同时提供了 getCategories() 方法，用于向listCategory.jsp页面传递数据  

![CategoryAction 部分代码 已省略 categories 的 getter/setter ](https://upload-images.jianshu.io/upload_images/14923529-b323a2b24f96f25c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


### 配置文件

##### web.xml

这个web.xml做了3件事情
1.让所有请求都进入 Struts2 的过滤器 StrutsPrepareAndExecuteFilter  
2.对所有请求进行 UTF-8 编码  
3.指定Spring配置文件 applicationContext.xml  的位置

```
<web-app>
    <filter>
        <filter-name>struts2</filter-name>
        <filter-class>
            org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter
        </filter-class>
    </filter>
    <filter-mapping>
        <filter-name>struts2</filter-name>
        <dispatcher>FORWARD</dispatcher>
        <dispatcher>REQUEST</dispatcher>       
        <url-pattern>/*</url-pattern>
    </filter-mapping>
     
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
 
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
</web-app>
```
##### struts.xml

```
<struts>
  <constant name="struts.i18n.encoding" value="UTF-8"></constant>
  <constant name="struts.objectFactory" value="spring"/>
  <package name="basicstruts" extends="struts-default">
 </package>
</struts>
```

##### applicationContext.xml
applicationContext 除了配置上述的 SessionFactory ，还要配置事务管理器
```
<!-- 配置事务管理器（声明式的事务） --> 
    <bean id="transactionManager" class="org.springframework.orm.hibernate3.HibernateTransactionManager"> 
        <property name="sessionFactory" ref="sf"></property> 
    </bean> 
```

##### 访问 jsp 显示数据

Action 携带数据跳转到 jsp ，作为视图，担当的角色是显示数据，借助 JSTL 的 c:forEach 标签遍历从CategoryAction 的 list() 的传递过来的集合。

![listCategory.jsp 部分代码](https://upload-images.jianshu.io/upload_images/14923529-9bb321d1f30a8407.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![localhost/admin_category_list 访问效果](https://upload-images.jianshu.io/upload_images/14923529-767eeb3d143ed003.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

[完整版的 listCategory.jsp](https://github.com/czwbig/Tmall_SSH/blob/master/web/admin/listCategory.jsp) 还包含4个公共文件，分别是 头部，导航，行业，页脚。
分类管理还有增加，编辑，修改，删除，分页，另外后台其他管理页面，前台页面。具体的需要浏览代码，篇幅原因就不展开了。


##### 思路流程图
![思路流程图](https://upload-images.jianshu.io/upload_images/14923529-cc398db9fd76243f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 重构（这里非常精彩）

分类管理的 CURD 功能全部做好之后，代码层面的问题开始逐渐浮现出来了，问题主要表现在 Service 层，和 Action 层。

##### Service层的问题
Service 层代码如下：

![](https://upload-images.jianshu.io/upload_images/14923529-64d5d8b2fbef927d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

首先看接口：CategoryService。 其声明的方法基本上就是 CURD 和分页。可以预见的是，在后续做产品管理，用户管理，订单管理等等，
也会有这么一个非常近似的 CURD 的接口，换句话说，这里是有做抽象和代码重构的机会和价值的。

然后看实现类：CategoryServiceImpl。 CategoryServiceImpl本身其实就是个架子，真正起作用的是为其注入的DAO对象，
所以这个地方也是可以引入委派模式，使得代码调用更加顺畅。

##### Service 层的重构

Service层 的重构行为主要包括两种角度
1. 对CURD进行抽象  
2. 委派模式的重构  

由于可以预见的在后续做产品管理，用户管理，订单管理等等，也会有这么一个非常近似的CURD的接口，
那么我们就做一个BaseService，里面就提供这些CRUD和分页查询的方法。

![](https://upload-images.jianshu.io/upload_images/14923529-18721369a0be32bc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

接着设计 BaseServiceImpl 类，其 CURD 关键方法都是调用的被注入的 dao 完成的，这样就十分适合使用委派模式来重构这块代码。  

不使用委派模式访问数据库都需要通过dao.XXX()来进行。 而委派重构之后，数据库相关方法，不再需要通过dao，直接调用即可，代码看上去更简洁。

##### 委派类 ServiceDelegateDAO 

设计一个新的类，叫做 ServiceDelegateDAO ，在其中注入 dao ，然后让对 dao 的每一个方法进行委派。 
那么到底什么是委派呢？ 如 ServiceDelegateDAO 类所示：
 ```
public void delete(Object entity) throws DataAccessException {
	dao.delete(entity);
}	
```


当调用 ServiceDelegateDAO 对象的 delete(Object entity) 的时候，其实就是委派给的 dao 的 delete(Object entity) 方法。
但是从调用者的角度来看，调用者只知道 ServiceDelegateDAO 这个类的 delete(Object entity) 方法，而意识不到 dao 的存在。
而 dao 继承了 HibernateTemplate ，一共有一百多个方法，哈哈这么麻烦肯定有工具可以一键生成，
果然利用 idea 立马就生成了所有委派方法，即快速又不会出错，突然感觉好开心。

##### BaseServiceImpl 

BaseServiceImpl 的构造器非常骚气，BaseServiceImpl 的 clazz 对象需要引用实体类对象，这样 CURD 方法中的 `DetachedCriteria dc = DetachedCriteria.forClass(clazz);` 才能获取到对应的查询对象。  
所以这里利用反射，在构造方法中，借助异常处理和反射得到 Category.class 或者 Product.class 。 即要做到哪个类继承了 BaseServiceImpl ，clazz 就对应哪个类对应的实体类对象。  

首先要获取是哪个类继承了 BaseServiceImpl ，因为实例化子类，父类的构造方法一定会被调用，
所以在父类 BaseServiceImpl 里故意抛出一个异常，然后手动捕捉住它，
在其对应的 StackTrace 里的第二个(下标是1) 栈跟踪元素 StackTraceElement  ，即对应子类。 
这样我们就拿到了子类名称 CategoryServiceImpl 或者 ProductServiceImpl，具体的在代码注释里了，写的非常清楚。


![BaseServiceImpl 部分代码](https://upload-images.jianshu.io/upload_images/14923529-d997996b80f89731.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

这样 CategoryService 就不需要自己声明方法了，只需要继承接口 BaseService 即可

![](https://upload-images.jianshu.io/upload_images/14923529-baef2585a1b49291.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

CategoryServiceImpl 也不需要自己提供实现了，继承 BaseServiceImpl 并实现接口 CategoryService 即可

![](https://upload-images.jianshu.io/upload_images/14923529-3f1b61c1e98e01c7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

这么做的好处主要在于：后续新功能开发的过程中，当需要新增加新的Service类的话，比如 PropertyService，无需从头开发，
只需要继承 BaseServiceImpl 并实现 PropertyService，那么其所需要CRUD一套方法都有了。

1. 开发成本显著降低
2. 更加不容易出错(因为方法都被抽象在父类中了，并且被前面的业务验证过了，要出错早就被纠正过了)

![画了个类关系图](https://upload-images.jianshu.io/upload_images/14923529-6ac10557ae57acf8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


##### Action的问题
先看代码

![CategoryAction 部分代码](https://upload-images.jianshu.io/upload_images/14923529-0cc00cd000ff9e32.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

这样的CategoryAction代码完成功能是没有问题的，但是问题恰恰在于，这样一个本来是用于充当控制层(Controller)的类，需要集中应付太多的需求： 
1. 返回页面的定义
2. 单个对象的getter setter
3. 集合对象的getter setter
4. 分页对象的getter setter
5. 上传文件对象的getter setter
6. Service层对象的注入
7. 作为控制层进行的访问路径映射

把所有的这些代码，都放在一个类里面，这个类就会显得繁杂，不易阅读，不易维护。 所以这个地方也是很有代码重构价值的。

##### Action 层的重构

目前 CategoryAction 存在的问题是一个类需要做太多的事情，显的繁杂，影响阅读和维护。 那么重构思路就是把不同的事情，放在专门的类进行处理，各司其职。  
- 上传专用 `Action4Upload`   
  这个类就专门用于处理图片上传，其他的事情一概不管  
- 分页专用 `Action4Pagination`   
   专门用于处理分页，并且继承上传专用 Action4Upload  
- 对象和集合 `Action4Pojo`  
  用于提供实体对象以及实体对象集合的setter和getter.  
setter用于接收注入  
getter用于提供数据到JSP(VIEW)上  
- 注入服务专用 `Action4Service`    
  Action4Service提供服务的注入  

Action4Service 另外提供了一个方法 t2p() ,专门用于把对象指向对应的持久对象。

![Action4Service .t2p()](https://upload-images.jianshu.io/upload_images/14923529-cb9d841b40077c9c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

方法调用的最后结果就导致父类 Action4Pojo 中声明的 pojo 本身是指向瞬时对象的，现在指向了持久对象（从数据库中取出的对象）。

再定义返回页面的 `Action4Result` 继承 Action4Service ,专门进行返回页面的定义

![](https://upload-images.jianshu.io/upload_images/14923529-d27d4ef86f6fac55.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

这样  
CategoryAction 继承Action4Result, 于是就间接地继承了 Action4Service，Action4Pojo，Action4Pagination，Action4Upload，
于是就通过继承提供了各种相关的功能，CategoryAction 本身只需要专注于扮演控制器(Controller)本身就行了。这些工作对后面其他 Action 同样适用，大大简化了后续开发。

此外，后续还有针对 Service 的关系查询重构，和Service 多条件查询重构，具体的由于篇幅原因，请移步[github 项目的地址](https://github.com/czwbig/Tmall_SSH)


##### 页面展示
![前台首页](https://upload-images.jianshu.io/upload_images/14923529-6be73ccdb1dec779.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![产品页](https://upload-images.jianshu.io/upload_images/14923529-d7a5bfc9f920b5e8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

本篇博客所讲不足整个项目的 1/10 ，有兴趣的朋友请移步 [github 项目的地址](https://github.com/czwbig/Tmall_SSH) 。


### 参考
**[天猫SSH整站学习教程](http://how2j.cn/k/tmall-ssh/tmall-ssh-1314/1314.html?p=55563)** 里面除了本项目，还有 Java 基础，前端，Tomcat 及其他中间件等教程， 可以注册一个账户，能保存学习记录。
