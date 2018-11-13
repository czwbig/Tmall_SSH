package com.caozhihu.tmall.service.impl;

import com.caozhihu.tmall.dao.impl.DAOImpl;
import org.hibernate.Filter;
import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ServiceDelegateDAO {

    //自动装配，从ioc容器中去查找DAOIplm类型的bean（如果有多个此类型，则返回 name = "dao" 的bean），并返回给此属性
    //不知为何此处用@AutoWired会注入失败，dao=null，使用@Resource则成功
    //好像说BaseServiceImpl中调用本类方法的对象是new出来的，而不是spring注入获得的，所以不能@AutoWired，存疑
    @Resource(name = "dao")
    private DAOImpl dao;

    @Resource(name = "sf")
    public void setSessionFactory(SessionFactory sessionFactory) {
        dao.setSessionFactory(sessionFactory);
    }

    public SessionFactory getSessionFactory() {
        return dao.getSessionFactory();
    }

    public void setFilterNames(String... filterNames) {
        dao.setFilterNames(filterNames);
    }

    public String[] getFilterNames() {
        return dao.getFilterNames();
    }

    public void setExposeNativeSession(boolean exposeNativeSession) {
        dao.setExposeNativeSession(exposeNativeSession);
    }

    public boolean isExposeNativeSession() {
        return dao.isExposeNativeSession();
    }

    public void setCheckWriteOperations(boolean checkWriteOperations) {
        dao.setCheckWriteOperations(checkWriteOperations);
    }

    public boolean isCheckWriteOperations() {
        return dao.isCheckWriteOperations();
    }

    public void setCacheQueries(boolean cacheQueries) {
        dao.setCacheQueries(cacheQueries);
    }

    public boolean isCacheQueries() {
        return dao.isCacheQueries();
    }

    public void setQueryCacheRegion(String queryCacheRegion) {
        dao.setQueryCacheRegion(queryCacheRegion);
    }

    public String getQueryCacheRegion() {
        return dao.getQueryCacheRegion();
    }

    public void setFetchSize(int fetchSize) {
        dao.setFetchSize(fetchSize);
    }

    public int getFetchSize() {
        return dao.getFetchSize();
    }

    public void setMaxResults(int maxResults) {
        dao.setMaxResults(maxResults);
    }

    public int getMaxResults() {
        return dao.getMaxResults();
    }

    public void afterPropertiesSet() {
        dao.afterPropertiesSet();
    }

    public <T> T execute(HibernateCallback<T> action) throws DataAccessException {
        return dao.execute(action);
    }

    public <T> T executeWithNativeSession(HibernateCallback<T> action) {
        return dao.executeWithNativeSession(action);
    }

    public <T> T get(Class<T> entityClass, Serializable id) throws DataAccessException {
        return dao.get(entityClass, id);
    }

    public <T> T get(Class<T> entityClass, Serializable id, LockMode lockMode) throws DataAccessException {
        return dao.get(entityClass, id, lockMode);
    }

    public Object get(String entityName, Serializable id) throws DataAccessException {
        return dao.get(entityName, id);
    }

    public Object get(String entityName, Serializable id, LockMode lockMode) throws DataAccessException {
        return dao.get(entityName, id, lockMode);
    }

    public <T> T load(Class<T> entityClass, Serializable id) throws DataAccessException {
        return dao.load(entityClass, id);
    }

    public <T> T load(Class<T> entityClass, Serializable id, LockMode lockMode) throws DataAccessException {
        return dao.load(entityClass, id, lockMode);
    }

    public Object load(String entityName, Serializable id) throws DataAccessException {
        return dao.load(entityName, id);
    }

    public Object load(String entityName, Serializable id, LockMode lockMode) throws DataAccessException {
        return dao.load(entityName, id, lockMode);
    }

    public <T> List<T> loadAll(Class<T> entityClass) throws DataAccessException {
        return dao.loadAll(entityClass);
    }

    public void load(Object entity, Serializable id) throws DataAccessException {
        dao.load(entity, id);
    }

    public void refresh(Object entity) throws DataAccessException {
        dao.refresh(entity);
    }

    public void refresh(Object entity, LockMode lockMode) throws DataAccessException {
        dao.refresh(entity, lockMode);
    }

    public boolean contains(Object entity) throws DataAccessException {
        return dao.contains(entity);
    }

    public void evict(Object entity) throws DataAccessException {
        dao.evict(entity);
    }

    public void initialize(Object proxy) throws DataAccessException {
        dao.initialize(proxy);
    }

    public Filter enableFilter(String filterName) throws IllegalStateException {
        return dao.enableFilter(filterName);
    }

    public void lock(Object entity, LockMode lockMode) throws DataAccessException {
        dao.lock(entity, lockMode);
    }

    public void lock(String entityName, Object entity, LockMode lockMode) throws DataAccessException {
        dao.lock(entityName, entity, lockMode);
    }

    @Transactional(readOnly = false)
    public Serializable save(Object entity) throws DataAccessException {
        return dao.save(entity);
    }

    @Transactional(readOnly = false)
    public Serializable save(String entityName, Object entity) throws DataAccessException {
        return dao.save(entityName, entity);
    }

    @Transactional(readOnly = false)
    public void update(Object entity) throws DataAccessException {
        dao.update(entity);
    }
    @Transactional(readOnly = false)
    public void update(Object entity, LockMode lockMode) throws DataAccessException {
        dao.update(entity, lockMode);
    }

    @Transactional(readOnly = false)
    public void update(String entityName, Object entity) throws DataAccessException {
        dao.update(entityName, entity);
    }

    @Transactional(readOnly = false)
    public void update(String entityName, Object entity, LockMode lockMode) throws DataAccessException {
        dao.update(entityName, entity, lockMode);
    }

    @Transactional(readOnly = false)
    public void saveOrUpdate(Object entity) throws DataAccessException {
        dao.saveOrUpdate(entity);
    }

    public void saveOrUpdate(String entityName, Object entity) throws DataAccessException {
        dao.saveOrUpdate(entityName, entity);
    }

    public void replicate(Object entity, ReplicationMode replicationMode) throws DataAccessException {
        dao.replicate(entity, replicationMode);
    }

    public void replicate(String entityName, Object entity, ReplicationMode replicationMode) throws DataAccessException {
        dao.replicate(entityName, entity, replicationMode);
    }

    public void persist(Object entity) throws DataAccessException {
        dao.persist(entity);
    }

    public void persist(String entityName, Object entity) throws DataAccessException {
        dao.persist(entityName, entity);
    }

    public <T> T merge(T entity) throws DataAccessException {
        return dao.merge(entity);
    }

    public <T> T merge(String entityName, T entity) throws DataAccessException {
        return dao.merge(entityName, entity);
    }

    @Transactional(readOnly = false)
    public void delete(Object entity) throws DataAccessException {
        dao.delete(entity);
    }

    public void delete(Object entity, LockMode lockMode) throws DataAccessException {
        dao.delete(entity, lockMode);
    }

    public void delete(String entityName, Object entity) throws DataAccessException {
        dao.delete(entityName, entity);
    }

    public void delete(String entityName, Object entity, LockMode lockMode) throws DataAccessException {
        dao.delete(entityName, entity, lockMode);
    }

    public void deleteAll(Collection<?> entities) throws DataAccessException {
        dao.deleteAll(entities);
    }

    public void flush() throws DataAccessException {
        dao.flush();
    }

    public void clear() throws DataAccessException {
        dao.clear();
    }

    public List<?> find(String queryString, Object... values) throws DataAccessException {
        return dao.find(queryString, values);
    }

    public List<?> findByNamedParam(String queryString, String paramName, Object value) throws DataAccessException {
        return dao.findByNamedParam(queryString, paramName, value);
    }

    public List<?> findByNamedParam(String queryString, String[] paramNames, Object[] values) throws DataAccessException {
        return dao.findByNamedParam(queryString, paramNames, values);
    }

    public List<?> findByValueBean(String queryString, Object valueBean) throws DataAccessException {
        return dao.findByValueBean(queryString, valueBean);
    }

    public List<?> findByNamedQuery(String queryName, Object... values) throws DataAccessException {
        return dao.findByNamedQuery(queryName, values);
    }

    public List<?> findByNamedQueryAndNamedParam(String queryName, String paramName, Object value) throws DataAccessException {
        return dao.findByNamedQueryAndNamedParam(queryName, paramName, value);
    }

    public List<?> findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values) throws DataAccessException {
        return dao.findByNamedQueryAndNamedParam(queryName, paramNames, values);
    }

    public List<?> findByNamedQueryAndValueBean(String queryName, Object valueBean) throws DataAccessException {
        return dao.findByNamedQueryAndValueBean(queryName, valueBean);
    }

    public List<?> findByCriteria(DetachedCriteria criteria) throws DataAccessException {
        return dao.findByCriteria(criteria);
    }

    public List<?> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) throws DataAccessException {
        return dao.findByCriteria(criteria, firstResult, maxResults);
    }

    public <T> List<T> findByExample(T exampleEntity) throws DataAccessException {
        return dao.findByExample(exampleEntity);
    }

    public <T> List<T> findByExample(String entityName, T exampleEntity) throws DataAccessException {
        return dao.findByExample(entityName, exampleEntity);
    }

    public <T> List<T> findByExample(T exampleEntity, int firstResult, int maxResults) throws DataAccessException {
        return dao.findByExample(exampleEntity, firstResult, maxResults);
    }

    public <T> List<T> findByExample(String entityName, T exampleEntity, int firstResult, int maxResults) throws DataAccessException {
        return dao.findByExample(entityName, exampleEntity, firstResult, maxResults);
    }

    public Iterator<?> iterate(String queryString, Object... values) throws DataAccessException {
        return dao.iterate(queryString, values);
    }

    public void closeIterator(Iterator<?> it) throws DataAccessException {
        dao.closeIterator(it);
    }

    public int bulkUpdate(String queryString, Object... values) throws DataAccessException {
        return dao.bulkUpdate(queryString, values);
    }

}
