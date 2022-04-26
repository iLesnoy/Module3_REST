package com.epam.esm.gifts.dao.impl;

import com.epam.esm.gifts.model.GiftCertificate;
import com.epam.esm.gifts.model.GiftCertificateAttribute;
import com.epam.esm.gifts.model.Tag;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Objects;

@Component
public class SqlQueryBuilder {


    private CriteriaBuilder criteriaBuilder;

    public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }

    public CriteriaQuery<GiftCertificate> giftSearchAndSortQuery(GiftCertificateAttribute attribute){
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        query.select(root);
        Predicate predicate = buildQueryCondition(root,attribute);
        query.where(predicate);
        List<Order> orderList = buildOrderListByFields(root,attribute);
        query.orderBy(orderList);
        return query;

    }

    private Predicate buildQueryCondition(Root<GiftCertificate> root, GiftCertificateAttribute attribute){
        Predicate tagName = buildByTagName(root,attribute);
        Predicate searchPart = buildBySearchPart(root,attribute);
        return criteriaBuilder.and(tagName,searchPart);
    }

    private Predicate buildByTagName(Root<GiftCertificate> root, GiftCertificateAttribute attribute) {
        List<String> tagNameList = attribute.getTagNameList();
        return CollectionUtils.isEmpty(tagNameList)
                ? criteriaBuilder.conjunction()
                : tagNameList.stream().map(tagName -> {
            Join<GiftCertificate, Tag> tagJoin = root.join("tag_list");
            return criteriaBuilder.equal(tagJoin.get("name"), tagName);
        }).reduce(criteriaBuilder.conjunction(), criteriaBuilder::and);
    }

    public CriteriaQuery<Long> buildToCountCertificates(GiftCertificateAttribute attribute){
        CriteriaQuery<Long>criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<GiftCertificate>certificateRoot = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(criteriaBuilder.count(certificateRoot));
        Predicate resultPredicate = buildQueryCondition(certificateRoot, attribute);
        criteriaQuery.where(resultPredicate);
        return criteriaQuery;
    }

    private Predicate buildBySearchPart(Root<GiftCertificate> root, GiftCertificateAttribute attribute) {
        String searchPart = Objects.nonNull(attribute.getSearchPart()) ? "%" + attribute.getSearchPart() + "%" : "";
        Predicate result = criteriaBuilder.conjunction();
        if (!"".equals(searchPart)) {
            Predicate namePartPredicate = criteriaBuilder.like(root.get("name"), searchPart);
            Predicate descriptionPartPredicate = criteriaBuilder.like(root.get("description"), searchPart);
            result = criteriaBuilder.or(namePartPredicate, descriptionPartPredicate);
        }
        return result;
    }

    private List<Order> buildOrderListByFields(Root<GiftCertificate> root, GiftCertificateAttribute attribute) {
        String orderSort = Objects.nonNull(attribute.getOrderSort()) ? attribute.getOrderSort() : "ASC";
        List<String> fieldList = attribute.getSortingFieldList();
        return CollectionUtils.isEmpty(fieldList)
                ? List.of(buildOrderByField(root, "id", orderSort))
                : fieldList.stream().map(field -> buildOrderByField(root, field, orderSort)).toList();
    }

    private Order buildOrderByField(Root<GiftCertificate> root, String fieldName, String orderSort) {
        return orderSort.equals("DEFAULT_SORT") ? criteriaBuilder.asc(root.get(fieldName)) : criteriaBuilder.desc(root.get(fieldName));
    }

}
