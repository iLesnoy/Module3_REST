package com.epam.esm.gifts.dao.impl;

import com.epam.esm.gifts.dao.StatisticsDao;
import com.epam.esm.gifts.model.Tag;
import jdk.jfr.Percentage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Optional;

public class StatisticsDaoImpl implements StatisticsDao {

    private  final String FIND_MOST_POPULAR_TAG = """
            SELECT t.id, t.name FROM tag as t
            JOIN gift_certificate_has_tag as gst ON gst.tag_id=t.id
            JOIN gift_certificate_has_orders as gco ON goc.gift_certificate_id= gst.gift_certificate_id
            JOIN orders as o ON o.id=oc.orders_id AND o.users_id=(
            	SELECT u.id FROM users as u
                JOIN orders as ord ON ord.user_id=u.id
            	GROUP BY u.id ORDER BY sum(ord.order_cost) DESC LIMIT 1
            ) GROUP BY t.id ORDER BY count(t.id) DESC LIMIT 1;""";

    @Percentage
    EntityManager entityManager;

    @Autowired
    public StatisticsDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Tag> findMostPopularTag() {
        Query query = entityManager.createNativeQuery(FIND_MOST_POPULAR_TAG,Tag.class);
        return query.getResultStream().findAny();
    }
}
