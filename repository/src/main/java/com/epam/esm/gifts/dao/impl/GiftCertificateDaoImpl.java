package com.epam.esm.gifts.dao.impl;

import com.epam.esm.gifts.dao.GiftCertificateDao;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.GiftCertificateAttribute;
import com.epam.esm.gifts.model.Order;
import com.epam.esm.gifts.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    @PersistenceContext
    private final EntityManager entityManager;
    private final SqlQueryBuilder sqlQueryBuilder;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    public GiftCertificateDaoImpl(EntityManager entityManager, SqlQueryBuilder sqlQueryBuilder) {
        this.entityManager = entityManager;
        this.sqlQueryBuilder = sqlQueryBuilder;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.sqlQueryBuilder.setCriteriaBuilder(criteriaBuilder);
    }

    @Override
    public List<GiftCertificate> findAll(Integer offset, Integer limit) {
        CriteriaQuery<GiftCertificate>criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate>certificateRoot = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(certificateRoot);
        criteriaQuery.orderBy(criteriaBuilder.asc(certificateRoot.get("id")));
        return entityManager.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        entityManager.persist(certificate);
        return certificate;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class,id));
    }

    @Override
    public void update(GiftCertificate certificate) {
        entityManager.persist(certificate);
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        entityManager.remove(giftCertificate);
    }

    @Override
    public Long findEntityNumber() {
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        query.select(criteriaBuilder.count(root));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public Long findEntityNumber(GiftCertificateAttribute attribute) {
        TypedQuery<Long> typedQuery = entityManager
                .createQuery(sqlQueryBuilder.buildToCountCertificates(attribute));
        return typedQuery.getSingleResult();
    }

    @Override
    public List<GiftCertificate> findByAttributes(GiftCertificateAttribute attribute, Integer offset, Integer limit) {
        TypedQuery<GiftCertificate> query = entityManager
                .createQuery(sqlQueryBuilder.giftSearchAndSortQuery(attribute));
        return query.setFirstResult(offset).setMaxResults(limit).getResultList();
    }
    @Override
    public boolean isGiftNameFree(String name) {
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        query.select(criteriaBuilder.count(root.get("id")));
        query.where(criteriaBuilder.equal(root.get("name"),name));
        return entityManager.createQuery(query).getSingleResult() == 0;
    }

    @Override
    public boolean isGiftCertificateUsedInOrders(Long id) {
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Order> root = query.from(Order.class);
        Join<GiftCertificate,Order> giftCertificateJoin = root.join("certificateList");
        query.select(criteriaBuilder.count(root.get("id")));
        query.where(criteriaBuilder.equal(giftCertificateJoin.get("id"),id));
        return entityManager.createQuery(query).getSingleResult() == 0;
    }
}
