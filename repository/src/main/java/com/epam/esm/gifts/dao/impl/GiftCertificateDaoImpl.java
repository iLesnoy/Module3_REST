package com.epam.esm.gifts.dao.impl;

import com.epam.esm.gifts.dao.GiftCertificateDao;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;


@Component
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    @Autowired
    public GiftCertificateDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
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
    public void create(GiftCertificate certificate) {
        entityManager.persist(certificate);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(GiftCertificate certificate) {
        entityManager.merge(certificate);
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public List<Tag> addTagsToCertificate(Long id, List<Tag> addedTagList) {
        return null;
    }

    @Override
    public List<GiftCertificate> findByParameters(String tagName, String searchPart, String description, List<String> sortingFieldList, String orderSort) {
        return null;
    }

    @Override
    public boolean deleteAllTagsFromCertificate(Long id) {
        return false;
    }
}
