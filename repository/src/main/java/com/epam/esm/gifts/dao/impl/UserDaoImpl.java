package com.epam.esm.gifts.dao.impl;

import com.epam.esm.gifts.dao.UserDao;
import com.epam.esm.gifts.model.Order;
import com.epam.esm.gifts.model.User;
import jdk.jfr.Percentage;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @Percentage
    private final EntityManager entityManager;
    private final CriteriaBuilder cb;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.cb = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<User> findAll(Integer offset, Integer limit) {
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> userRoot = query.from(User.class);
        query.select(userRoot);
        query.orderBy(cb.asc(userRoot.get("id")));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class,id));
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public Long findEntityNumber() {
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<User> root = query.from(User.class);
        query.select(cb.count(root));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public boolean isNameFree(String name) {
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<User> root = query.from(User.class);
        query.select(cb.count(root.get("id")));
        query.where(cb.equal(root.get("name"),name));
        return entityManager.createQuery(query).getSingleResult() == 0;
    }

    @Override
    public User findByName(String name) {
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        query.where(cb.like(root.get("name"),name));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public Long userOrderNumber(User user) {
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Order> root = query.from(Order.class);
        query.select(cb.count(root));
        query.where(cb.equal(root.get("user"),user));
        return entityManager.createQuery(query).getSingleResult();
    }


    @Override
    public List<Order> finUserOrder(User user, Integer offset, Integer limit){
        CriteriaQuery<Order>query = cb.createQuery(Order.class);
        Root<Order>root = query.from(Order.class);
        query.select(root);
        query.where(cb.equal(root.get("user"),user));
        query.orderBy(cb.asc(root.get("id")));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
