package com.epam.esm.gifts.dao.constants;

public class SqlQuery {

    public static final String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price," +
            " duration, create_date, last_update_date) VALUES (?,?,?,?,?,?)";
    public static final String FIND_ALL_GIFT_CERTIFICATES = "SELECT gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date, tag.id, tag.name FROM gift_certificate \n" +
            "JOIN gift_certificate_has_tag ON gift_certificate.id = gift_certificate_has_tag.gift_certificate_id JOIN tag ON tag.id = gift_certificate_has_tag.tag_id";
    public static final String ADD_TAG_TO_GIFT_CERTIFICATE = "INSERT INTO gift_certificate_has_tag" +
            " (gift_certificate_id, tag_id) VALUES (?, ?)";
    public static final String DELETE_GIFT_CERTIFICATE_BY_ID = "DELETE FROM gift_certificate WHERE id=?";
    public static final String FIND_GIFT_CERTIFICATE_BY_ID = FIND_ALL_GIFT_CERTIFICATES + " WHERE gift_certificate.id=?";
    public static final String DELETE_ALL_TAGS_FROM_CERTIFICATE = "DELETE FROM tags_certificate WHERE gift_certificate_id=?";
}
