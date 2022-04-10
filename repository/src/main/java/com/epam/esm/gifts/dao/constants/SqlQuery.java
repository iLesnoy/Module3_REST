package com.epam.esm.gifts.dao.constants;

public class SqlQuery {

    public static final String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price," +
            " duration, create_date, last_update_date) VALUES (?,?,?,?,?,?)";
    public static final String FIND_ALL_GIFT_CERTIFICATES = "SELECT gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date, tag.id, tag.name FROM gift_certificate " +
            "JOIN gift_certificate_has_tag ON gift_certificate.id = gift_certificate_has_tag.gift_certificate_id JOIN tag ON tag.id = gift_certificate_has_tag.tag_id";
    public static final String ADD_TAG_TO_GIFT_CERTIFICATE = "INSERT INTO gift_certificate_has_tag" +
            " (gift_certificate_id, tag_id) VALUES (?, ?)";
    public static final String COUNT_TAG_IN_USE = "SELECT count(*) FROM gift_certificate_has_tag WHERE tag_id=?";
    public static final String DELETE_GIFT_CERTIFICATE_BY_ID = "DELETE FROM gift_certificate WHERE id=?";
    public static final String FIND_GIFT_CERTIFICATE_BY_ID = FIND_ALL_GIFT_CERTIFICATES + " WHERE gift_certificate.id=?";
    public static final String DELETE_ALL_TAGS_FROM_CERTIFICATE = "DELETE FROM gift_certificate_has_tag WHERE gift_certificate_id=?";
    public static final String SEARCH_AND_SORT_QUERY = "SELECT gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date, tag.id, tag.name FROM gift_certificate " +
            "JOIN gift_certificate_has_tag ON gift_certificate.id = gift_certificate_has_tag.gift_certificate_id JOIN tag ON tag.id = gift_certificate_has_tag.tag_id " +
            "WHERE tag.name LIKE CONCAT('%%' '%s' '%%') AND gift_certificate.name LIKE CONCAT ('%%', '%s', '%%') " +
            "OR gift_certificate.description LIKE CONCAT ('%%', '%s', '%%') ORDER BY ";
    public static final String UPDATE_GIFT_BY_ID = "UPDATE gift_certificate SET name=?,description=?,price=?,duration=?,create_date=?,last_update_date=? WHERE id=?";
    public static final String INSERT_TAG = "INSERT INTO tag (name) VALUES (?)";
    public static final String FIND_ALL_TAGS = "SELECT id,name FROM tag";
    public static final String FIND_TAG_BY_ID = "SELECT id,name FROM tag WHERE id=?";
    public static final String UPDATE_TAG_BY_ID = "UPDATE tag SET name =? WHERE id=?";
    public static final String FIND_TAG_BY_NAME = "SELECT id,name FROM tag WHERE name=?";
    public static final String DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id=?";

}