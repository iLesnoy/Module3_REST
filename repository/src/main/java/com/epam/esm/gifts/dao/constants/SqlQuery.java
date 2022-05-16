package com.epam.esm.gifts.dao.constants;

public class SqlQuery {

    public static final String FIND_CERTIFICATES_BY_SEARCH_PART =
            "SELECT c FROM GiftCertificate c WHERE c.name LIKE %:searchPart% OR c.description LIKE %:searchPart%";
    public static final String FIND_CERTIFICATES_BY_TAG_NAMES_AND_SEARCH_PART =
            "SELECT c FROM GiftCertificate c JOIN c.tagList tl WHERE tl.name IN :tagNameList" +
                    " AND (c.name LIKE %:searchPart% OR c.description LIKE %:searchPart%) GROUP BY c HAVING COUNT(tl)>=:tagNumber";
    public static final String FIND_MOST_POPULAR_TAG =
            "SELECT t.id, t.name FROM tags as t " +
                    "JOIN tags_certificates as tc ON tc.tag_id=t.id " +
                    "JOIN orders_certificates as oc ON oc.gift_certificate_id=tc.gift_certificate_id " +
                    "JOIN orders as o ON o.id=oc.order_id AND o.user_id=(" +
                    "SELECT u.id FROM users as u " +
                    "JOIN orders as ord ON ord.user_id=u.id " +
                    "GROUP BY u.id ORDER BY sum(ord.order_cost) DESC LIMIT 1" +
                    ") GROUP BY t.id ORDER BY count(t.id) DESC LIMIT 1";

    private SqlQuery() {
    }
}
