package com.epam.esm.gifts.dao.impl;

import com.epam.esm.gifts.dao.OrderDao;
import com.epam.esm.gifts.model.Order;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    @Autowired
    public OrderDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Order> findAll(Integer offset, Integer limit) {
        CriteriaQuery<Order>criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order>orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get("id")));
        return entityManager.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public void create(Order order) {

    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(Order order) {
        entityManager.merge(order);
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }
}
