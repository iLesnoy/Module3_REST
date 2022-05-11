package com.epam.esm.gifts.dao.impl;

import com.epam.esm.gifts.dao.TagDao;
import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;


@Component
public class TagDaoImpl implements TagDao {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    public TagDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Tag> findAll(Integer offset, Integer limit) {
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(root);
        query.orderBy(criteriaBuilder.asc(root.get("id")));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(root);
        query.where(criteriaBuilder.equal(root.get("name"), name));
        return entityManager.createQuery(query).getResultList().stream().findAny();
    }

    @Override
    public List<GiftCertificate> isTagUsed(Tag tag) {
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        Join<Tag, GiftCertificate>tagGiftCertificateJoin = root.join("tagList");
        query.select(root);
        query.where(criteriaBuilder.equal(tagGiftCertificateJoin.get("id"),tag.getId()));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void update(Tag tag) {
        entityManager.merge(tag);
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public Long findEntityNumber() {
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(criteriaBuilder.count(root));
        return entityManager.createQuery(query).getSingleResult();
    }

}
